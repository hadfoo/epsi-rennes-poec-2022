package fr.epsi.rennes.poec.antoine.exception;

public class TechnicalException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public TechnicalException(Exception e) {
		super(e);
	}
	
}
