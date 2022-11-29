package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "utente")
@Data
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "dataRegistrazione")
	private Date dataRegistrazione;

	// se non uso questa annotation viene gestito come un intero
	@Enumerated(EnumType.STRING)
	private StatoUtente stato;
	
	@Column(name = "esperienzaAccumulata")
	private int esperienzaAccumulata;
	
	@Column(name = "creditoAccumulato")
	private int creditoAccumulato;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utente")
	private Set<Tavolo> tavoliCreati = new HashSet<Tavolo>(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tavolo_id", nullable = false)
	private Tavolo tavoloGioco;

	public Utente() {
	}
	
	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Utente(String username, String password, String nome, String cognome, Date dataRegistrazione) {
		this(username, password);
		this.nome = nome;
		this.cognome = cognome;
		this.dataRegistrazione = dataRegistrazione;
	}

	public Utente(Long id, String username, String password, String nome, String cognome,
			Date dateCreated, StatoUtente stato) {
		this(username, password, nome, cognome, dateCreated);
		this.id = id;
		this.stato = stato;
	}
}
