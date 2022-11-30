package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tavolo")
@Data
public class Tavolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;
	@Column(name = "cifraMinima")
	private Integer cifraMinima;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private Date dataCreazione;
	
	@OneToMany
	private Set<Utente> giocatori = new HashSet<Utente>(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatore_id", referencedColumnName = "id", nullable = false)
	private Utente creatore;
	
	public Tavolo() {
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dataCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
	}
	
	public Tavolo(Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dataCreazione) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
	}

	public Tavolo(Long id) {
		super();
		this.id = id;
	}
}
