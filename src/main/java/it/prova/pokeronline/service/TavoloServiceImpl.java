package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.web.api.exception.NonInTavoloException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

@Service
public class TavoloServiceImpl implements TavoloService {
	@Autowired
	private TavoloRepository repository;
	
	@Autowired
	private UtenteRepository utenteRepository;

	public List<Tavolo> listAllElements(boolean eager) {
		if (eager)
			return (List<Tavolo>) repository.findAllTavoloEager();

		return (List<Tavolo>) repository.findAll();
	}

	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Tavolo caricaSingoloElementoEager(Long id) {
		return repository.findSingleTavoloEager(id);
	}

	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);
	}

	@Override
	public List<Tavolo> findByDenominazione(String denominazione) {
		return repository.findByDenominazione(denominazione);
	}
	
	public List<Tavolo> findAllSpecial(String name) {
		return repository.findAllSpecial(utenteRepository.findByUsername(name).get().getId());
	}
	
	public Tavolo findByIdSpecial(Long idTavolo, Long idUtente) {
		return repository.findByIdSpecial(idTavolo, idUtente);
	}
	
	@Override
	@Transactional
	public Tavolo ultimoGame(Long id) {
		Tavolo result = repository.findByGiocatoriId(id);

		// Se non sono in nessun tavolo
		if (result == null)
			throw new NonInTavoloException("Non si e' attualmente in nessun tavolo");

		return result;
	}
	
	@Transactional
	public Utente abbandonaPartita(Long idTavolo) {
		// Verifico che il tavolo esista.
		Tavolo tavoloInstance = repository.findById(idTavolo).orElse(null);
		if (tavoloInstance == null)
			throw new TavoloNotFoundException("Tavolo con id: " + idTavolo + " not Found");

		// Prendo l' utente In Sessione.
		Utente utenteLoggato = utenteRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		
		tavoloInstance.getGiocatori().remove(utenteLoggato);

		utenteLoggato.setEsperienzaAccumulata(utenteLoggato.getEsperienzaAccumulata() + 1);
		repository.save(tavoloInstance);
		utenteRepository.save(utenteLoggato);

		return utenteLoggato;
	}
	
	@Transactional
	public List<Tavolo> listEsperienzaMin(Integer min) {
		return (List<Tavolo>) repository.findAllByEsperienzaMin(min);
	}
}
