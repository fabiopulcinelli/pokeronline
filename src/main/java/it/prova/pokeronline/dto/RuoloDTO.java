package it.prova.pokeronline.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.pokeronline.model.Ruolo;
import lombok.Data;

@Data
public class RuoloDTO {

	private Long id;
	private String descrizione;
	private String codice;

	public RuoloDTO(Long id, String descrizione, String codice) {
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public static RuoloDTO buildRuoloDTOFromModel(Ruolo ruoloModel) {
		return new RuoloDTO(ruoloModel.getId(), ruoloModel.getDescrizione(), ruoloModel.getCodice());
	}

	public static List<RuoloDTO> createRuoloDTOListFromModelSet(Set<Ruolo> modelListInput) {
		return modelListInput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());
	}
	
	public static List<RuoloDTO> createRuoloDTOListFromModelList(List<Ruolo> modelListInput) {
		return modelListInput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());
	}
	
}
