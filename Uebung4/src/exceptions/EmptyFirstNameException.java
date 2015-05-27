package exceptions;

public class EmptyFirstNameException extends Exception {
	
	public EmptyFirstNameException() {

	}

	public EmptyFirstNameException(String s) {
		super(s);
	}
}
