package it.prova.pokeronline.web.api.exception;

public class UtenteNotAuthorizedException  extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UtenteNotAuthorizedException(String message) {
		super(message);
	}
}