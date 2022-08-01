package exceptions;

public class FilmeSemEstoqueException extends Exception {

	public FilmeSemEstoqueException(String message) {
		super(message);
	}

	public FilmeSemEstoqueException(String message, Throwable cause) {
		super(message, cause);
	}
}
