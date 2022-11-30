package it.prova.pokeronline.web.api.exception;

public class NonInTavoloException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NonInTavoloException(String message) {
		super(message);
	}
}