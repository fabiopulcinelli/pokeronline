package it.prova.pokeronline.web.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {
	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {
		// solo se sono uno special player
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(roleItem -> roleItem.getAuthority().equals(Ruolo.ROLE_SPECIAL_USER)))
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findAllSpecial(SecurityContextHolder.getContext().getAuthentication().getName()), true);

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllElements(false), true);
	}
/*
	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		return TavoloDTO.buildTavoloDTOFromModelNoPassword(tavolo, true);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		if (tavoloInput.getUtenteCreazione() != null)
			throw new UtenteCreazioneNotNullException(
					"Non è ammesso fornire l' UtenteCreazione, impossibile Creare un tavolo per un Utente Specifico");

		tavoloInput.setUtenteCreazione(UtenteDTO.buildUtenteDTOFromModelNoPassword(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());

		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito, false);
	}

	@PutMapping("/{id}")
	public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloElementoEager(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		if (tavoloInput.getUtenteCreazione() == null || tavoloInput.getUtenteCreazione().getId() == null
				|| tavoloInput.getUtenteCreazione().getId() < 1)
			throw new UtenteCreazioneNonValidoException(
					"UtenteCreazione non valid, inserire correttamente il campo UtenteCreazione");

		// Per verificare il punto 3, confrontiamo l' id del utenteCreazione del tavolo
		// caricato dal DB e l' id del utenteCreazione del tavoloInput
		if (tavolo.getUtenteCreazione().getId() != tavoloInput.getUtenteCreazione().getId())
			throw new UtenteCreazioneNonCorrispondenteAlPrecedente(
					"Impossibile modificare L' UtenteCreazione inserendo un Utente Diverso, L' UtenteCreazione deve rimanere LO STESSO");

		tavoloInput.setId(id);
		Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildTavoloModel(), tavolo);
		return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		tavoloService.rimuovi(id);
	}

	@PostMapping("/search")
	public List<TavoloDTO> search(@RequestBody TavoloDTO example) {
		Utente inSessione = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (inSessione.getRuoli().stream().anyMatch(ruolo -> ruolo.getCodice().equals(Ruolo.ROLE_ADMIN)))
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(example.buildTavoloModel()),
					false);

		if (example.getUtenteCreazione() != null)
			throw new UtenteCreazionePresenteException(
					"All interno del tavolo Example è presente un UtenteCreazione, Impossibile procedere, rimuovi l' utenteCreazione dall' example");

		return TavoloDTO.createTavoloDTOListFromModelList(
				tavoloService.findByExampleSpecialPlayer(example.buildTavoloModel(), inSessione.getId()), false);
	}
	
	*/
}
