package exceptions;

public class EmptyStringException extends Exception {
	
	public EmptyStringException() {}

	public EmptyStringException(String s) {
		super(s);
	}
}
