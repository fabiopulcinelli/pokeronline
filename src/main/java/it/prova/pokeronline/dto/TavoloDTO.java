package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	private Integer esperienzaMin;
	
	@NotNull(message = "{cifraMinima.notnull}")
	private Integer cifraMinima;
	
	@NotBlank(message = "{denominazione.notnull}")
	private String denominazione;
	
	@NotNull(message = "{dataCreazione.notnull}")
	private Date dataCreazione;

	@JsonIgnoreProperties(value = { "tavoli" })
	private UtenteDTO creatore;
	
	@JsonIgnoreProperties(value = { "tavoli" })
	private Set<UtenteDTO> giocatori;

	Set<Utente> utenti;
	
	public TavoloDTO() {
	}

	public TavoloDTO(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dataCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
	}

	public TavoloDTO(Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dataCreazione) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
	}

	public Tavolo buildTavoloModel() {
		Tavolo result = new Tavolo(this.id, this.esperienzaMin, this.cifraMinima, this.denominazione, this.dataCreazione);

		if(this.giocatori.size() > 1) {
			Set<Utente> set = result.getGiocatori();
			this.giocatori.forEach(utente -> set.add(utente.buildUtenteModel(false)));
		}
		
		if(this.creatore != null)
			result.setCreatore(this.creatore.buildUtenteModel(false));
			
		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeGiocatori) {
		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getEsperienzaMin(), tavoloModel.getCifraMinima(),
				tavoloModel.getDenominazione(), tavoloModel.getDataCreazione());

		if (tavoloModel.getCreatore() != null && tavoloModel.getCreatore().getId() != null
				&& tavoloModel.getCreatore().getId() > 0) {
			result.setCreatore(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getCreatore()));
		}

		if (tavoloModel.getGiocatori() != null && !tavoloModel.getGiocatori().isEmpty()) {
			result.setGiocatori(UtenteDTO.createUtenteDTOSetFromModelSet(tavoloModel.getGiocatori()));
		}
		
		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput, boolean includeGiocatori) {
		return modelListInput.stream().map(tavoloEntity -> {
			
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity, includeGiocatori);
		}).collect(Collectors.toList());
	}

	public static Set<TavoloDTO> createTavoloDTOSetFromModelSet(Set<Tavolo> modelListInput, boolean includeGiocatori) {
		return modelListInput.stream().map(tavoloEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity, includeGiocatori);
		}).collect(Collectors.toSet());
	}
}
