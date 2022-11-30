package it.prova.pokeronline.security.dto;

import java.util.List;

import lombok.Data;

@Data
public class UtenteInfoJWTResponseDTO {

	private String nome;
	private String cognome;
	private String type = "Bearer";
	private String username;
	private List<String> roles;
	private Integer esperienzaAccumulata;
	private Integer creditoAccumulato;

	public UtenteInfoJWTResponseDTO(String nome, String cognome, String username, List<String> roles, Integer esperienzaAccumulata, Integer creditoAccumulato) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.roles = roles;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}
}