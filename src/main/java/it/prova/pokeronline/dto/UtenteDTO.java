package it.prova.pokeronline.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;

public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	private Date dataRegistazione;

	private StatoUtente stato;
	
	private Integer esperienzaAccumulata;
	
	private Integer creditoAccumulato;

	private Long[] ruoliIds;
	
	private Long[] tavoliIds;
	
	private Long tavoloId;

	public UtenteDTO() {
	}

	public UtenteDTO(Long id, String username, String nome, String cognome, StatoUtente stato, Integer esperienzaAccumulata, Integer creditoAccumulato) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataRegistrazione() {
		return dataRegistazione;
	}

	public void setDataRegistrazione(Date dataRegistazione) {
		this.dataRegistazione = dataRegistazione;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}
	
	public Date getDataRegistazione() {
		return dataRegistazione;
	}

	public void setDataRegistazione(Date dataRegistazione) {
		this.dataRegistazione = dataRegistazione;
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

	public Long[] getTavoliIds() {
		return tavoliIds;
	}

	public void setTavoliIds(Long[] tavoliIds) {
		this.tavoliIds = tavoliIds;
	}

	public Long getTavoloId() {
		return tavoloId;
	}

	public void setTavoloId(Long tavoloId) {
		this.tavoloId = tavoloId;
	}

	public Utente buildUtenteModel(boolean includeIdRoles, boolean includeIdTavoliCreati, boolean includeIdTavoloGioco) {
		Utente result = new Utente(this.id, this.username, this.password, this.nome, this.cognome,
				this.dataRegistazione, this.stato, this.esperienzaAccumulata, this.creditoAccumulato);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));
		if (includeIdTavoliCreati && tavoliIds != null)
			result.setTavoliCreati(Arrays.asList(tavoliIds).stream().map(id -> new Tavolo(id)).collect(Collectors.toSet()));
		if (includeIdTavoloGioco && tavoloId != null)
			result.setTavoloGioco(new Tavolo(id));

		return result;
	}

	// niente password...
	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
				utenteModel.getCognome(), utenteModel.getStato(), utenteModel.getEsperienzaAccumulata(), utenteModel.getCreditoAccumulato());

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});
		
		if (!utenteModel.getTavoliCreati().isEmpty())
			result.tavoliIds = utenteModel.getTavoliCreati().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});
		
		if (utenteModel.getTavoloGioco()!=null)
			result.tavoloId = utenteModel.getTavoloGioco().getId();

		return result;
	}

	public static List<UtenteDTO> createUtenteDTOListFromModelList(List<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}
	
	public static Set<UtenteDTO> createUtenteDTOSetFromModelSet(Set<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toSet());
	}
}
