/*
 *   version 1.0
 */
package tw.com.exception;

import org.springframework.dao.DataIntegrityViolationException;
/**
 * 時間重疊例外 
 * @author derek_chang 
 * @since 2008/2/27 上午 10:46:24
 *
 */
public class CustomDateOverlapException extends DataIntegrityViolationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomDateOverlapException(String msg) { 
		 super( msg );
	}
}
