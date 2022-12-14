package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloService {
	List<Tavolo> listAllElements(boolean eager);

	Tavolo caricaSingoloElemento(Long id);

	Tavolo caricaSingoloElementoEager(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Tavolo tavoloInstance);

	List<Tavolo> findByDenominazione(String denominazione);
	
	List<Tavolo> findAllSpecial(String name);
	
	Tavolo findByIdSpecial(Long idTavolo, Long idUtente);
	
	Tavolo ultimoGame(Long id);
	
	Utente abbandonaPartita(Long idTavolo);
	
	void entraPartita(Long idTavolo);
	
	List<Tavolo> listEsperienzaMin(Integer min);
}
