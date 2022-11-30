package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.GiaInTavoloException;
import it.prova.pokeronline.web.api.exception.NonInTavoloException;

@RestController
@RequestMapping("/api/play")
public class PlayController {
	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;
	
	@PostMapping("compraCredito/{credito}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String compraCredito(@PathVariable(value = "credito", required = true) Integer credito) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		Integer creditoFinale = utenteLoggato.getCreditoAccumulato() + credito;
		
		utenteLoggato.setCreditoAccumulato(creditoFinale);
		utenteService.aggiorna(utenteLoggato);
		
		return "Credito caricato! il tuo nuovo credito e' di " + creditoFinale;
	}
	
	@GetMapping("/lastGame")
	public TavoloDTO dammiLastGame() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		// Se non sono in nessun tavolo
		Tavolo result = null;
		try {
			result = tavoloService.ultimoGame(utenteLoggato.getId());
		}
		catch(Exception e) {
			throw new NonInTavoloException("Non si e' attualmente in nessun tavolo");
		}
			
		
		return TavoloDTO.buildTavoloDTOFromModel(result, true);
	}
	
	@PostMapping("abbandonaPartita")
	public UtenteDTO abbandonaPartita() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		Long idTavolo = null;
		try {
			idTavolo = tavoloService.ultimoGame(utenteLoggato.getId()).getId();
		}
		catch(Exception e) {
			throw new NonInTavoloException("Non si e' attualmente in nessun tavolo");
		}
		
		return UtenteDTO.buildUtenteDTOFromModel(tavoloService.abbandonaPartita(idTavolo));
	}
	
	// tutti i tavoli con esperienza minima minore o uguale alla mia
	@GetMapping
	public List<TavoloDTO> ricerca() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		List<TavoloDTO> tavoliTrovati = TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listEsperienzaMin(utenteLoggato.getEsperienzaAccumulata()), true);
		
		return tavoliTrovati;
	}

	@PostMapping("entraInTavolo/{id}")
	public UtenteDTO entraGioca(@PathVariable(value = "id", required = true) long idTavolo) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		Tavolo result = tavoloService.ultimoGame(utenteLoggato.getId());
		
		if(result != null) {
			throw new GiaInTavoloException("Sei gia in partita in un altro tavolo!");
		}
		
		tavoloService.entraPartita(idTavolo);
		
		// inizio simulazione partita
		double segnoDouble = Math.random();
		
		String segno = "";
		if(segnoDouble > 0.5)
			segno = "positivo";
		else
			segno = "negativo";
		
		double num = Math.random();
        int somma = (int)(num*1000+1);
		
		int totDaAggiungereOSottrarre = (int) (segnoDouble*somma);
		
		Integer creditoFinale = 0;
		if(segno.equals("positivo")){
			creditoFinale = utenteLoggato.getCreditoAccumulato() + totDaAggiungereOSottrarre;
		}
		if(segno.equals("negativo")){
			creditoFinale = utenteLoggato.getCreditoAccumulato() - totDaAggiungereOSottrarre;
		}
		
		utenteLoggato.setCreditoAccumulato(creditoFinale);
		utenteService.aggiorna(utenteLoggato);
		
		// fine simulazione esco dalla partita
		return UtenteDTO.buildUtenteDTOFromModel(tavoloService.abbandonaPartita(idTavolo));
	}
	
}
