package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long> {
	@Query("select t from Tavolo t join fetch t.creatore join fetch t.giocatori where t.id = ?1")
	Tavolo findSingleTavoloEager(Long id);
	
	List<Tavolo> findByDenominazione(String denominazione);
	
	@Query("select t from Tavolo t join fetch t.creatore join fetch t.giocatori")
	List<Tavolo> findAllTavoloEager();
	
	@Query("select t from Tavolo t join t.creatore where t.creatore.id = ?1")
	List<Tavolo> findAllSpecial(Long id);
	
	@Query("select t from Tavolo t join t.creatore where t.id = ?1 and t.creatore.id = ?2")
	Tavolo findByIdSpecial(Long idTavolo, Long idUtente);
	
	Tavolo findByGiocatoriId(Long id);
	
	@Query("select t from Tavolo t WHERE t.esperienzaMin <= ?1")
	List<Tavolo> findAllByEsperienzaMin(Integer min);
}
