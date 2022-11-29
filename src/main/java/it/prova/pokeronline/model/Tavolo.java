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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "agenda")
@Data
public class Tavolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzaMin")
	private int esperienzaMin;
	@Column(name = "cifraMinima")
	private int cifraMinima;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private Date dataCreazione;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavolo")
	private Set<Utente> giocatori = new HashSet<Utente>(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente creatore;
	
	public Tavolo() {
	}

	public Tavolo(int esperienzaMin, int cifraMinima, String denominazione, Date dataCreazione) {
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
