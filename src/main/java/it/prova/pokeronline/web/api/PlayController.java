package it.prova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;

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
		
		return TavoloDTO.buildTavoloDTOFromModel(tavoloService.ultimoGame(utenteLoggato.getId()), true);
	}
}
