package tw.com.exception;

public class CustomValidationException extends Exception{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomValidationException(String msg) { 
		 super( msg );
	 }
}
