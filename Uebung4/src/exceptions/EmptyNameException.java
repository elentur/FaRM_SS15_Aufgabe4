package exceptions;

public class EmptyNameException extends Exception {
	
	public EmptyNameException() {

	}

	public EmptyNameException(String s) {
		super(s);
	}
}
