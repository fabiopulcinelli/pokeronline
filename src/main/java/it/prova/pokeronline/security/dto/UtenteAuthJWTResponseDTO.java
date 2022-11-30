package it.prova.pokeronline.security.dto;

import java.util.List;

import lombok.Data;

@Data
public class UtenteAuthJWTResponseDTO {

	private String token;
	private String type = "Bearer";
	private String username;
	private List<String> roles;
	private Integer esperienzaAccumulata;
	private Integer creditoAccumulato;

	public UtenteAuthJWTResponseDTO(String accessToken, String username, List<String> roles, Integer esperienzaAccumulata, Integer creditoAccumulato) {
		this.token = accessToken;
		this.username = username;
		this.roles = roles;
		this.esperienzaAccumulata = esperienzaAccumulata;
		this.creditoAccumulato = creditoAccumulato;
	}
}
