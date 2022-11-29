package it.prova.pokeronline;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloService;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", Ruolo.ROLE_CLASSIC_USER));
		}
		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special User", Ruolo.ROLE_SPECIAL_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special User", Ruolo.ROLE_SPECIAL_USER));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date(), 100, 500);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", new Date(), 50, 250);
			classicUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser.getId());
		}

		if (utenteServiceInstance.findByUsername("special") == null) {
			Utente specialUser = new Utente("special", "special", "Antonioo", "Verdii", new Date(), 10, 300);
			specialUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special User", Ruolo.ROLE_SPECIAL_USER));
			utenteServiceInstance.inserisciNuovo(specialUser);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(specialUser.getId());
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente classicUser2 = new Utente("user2", "user2", "Antoniooo", "Verdiii", new Date(), 200, 600);
			classicUser2.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser2);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser2.getId());
		}

		// Mettere dati tavolo
		Tavolo tavolo1 = new Tavolo(10, 500, "Tavolo 1", new Date());
		tavolo1.setCreatore(utenteServiceInstance.findByUsername("admin"));
		Set<Utente> giocatori = Set.of(utenteServiceInstance.findByUsername("admin"), utenteServiceInstance.findByUsername("user"), utenteServiceInstance.findByUsername("special"));
		tavolo1.setGiocatori(giocatori);
		
		if (tavoloService.findByDenominazione(tavolo1.getDenominazione()).isEmpty())
			tavoloService.inserisciNuovo(tavolo1);
		
		Tavolo tavolo2 = new Tavolo(50, 200, "Tavolo 2", new Date());
		tavolo2.setCreatore(utenteServiceInstance.findByUsername("special"));
		
		if (tavoloService.findByDenominazione(tavolo2.getDenominazione()).isEmpty())
			tavoloService.inserisciNuovo(tavolo2);
	}
}
