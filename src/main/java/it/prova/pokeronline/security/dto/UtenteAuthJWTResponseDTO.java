package it.prova.pokeronline.security.dto;

import java.util.List;

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

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getEsperienzaAccumulata() {
		return esperienzaAccumulata;
	}

	public void setEsperienzaAccumulata(Integer esperienzaAccumulata) {
		this.esperienzaAccumulata = esperienzaAccumulata;
	}

	public Integer getCreditoAccumulato() {
		return creditoAccumulato;
	}

	public void setCreditoAccumulato(Integer creditoAccumulato) {
		this.creditoAccumulato = creditoAccumulato;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
