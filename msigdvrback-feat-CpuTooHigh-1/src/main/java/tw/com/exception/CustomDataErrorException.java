/*
 * version 1.0
 */
package tw.com.exception;

import org.springframework.dao.DataIntegrityViolationException;
/**
 * call API 執行資料異動例外狀況
 *
 * @author Kevin Chen
 * @since 2008/3/12 下午 6:04:41
 * @version 1.0
 */
public class CustomDataErrorException extends DataIntegrityViolationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomDataErrorException(String msg) {
		super(msg);
	}

	public CustomDataErrorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
