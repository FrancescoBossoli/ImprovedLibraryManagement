package library.management.exceptions;

public class UnacceptableDateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnacceptableDateException(String message) {
		super(message);
	}	
}