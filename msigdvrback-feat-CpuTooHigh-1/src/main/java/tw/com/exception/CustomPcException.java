/*
 *   version 1.0
 * 2009/07/18 13:00 debbie 修改CustomPcException方法傳入參數名稱
 */
package tw.com.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * percedure 自定錯誤訊息
 * 
 * @author derek_chang 
 * @since 2008/1/14 上午 10:23:38
 * lastModified 2009/07/14 11:54:26 ,aden
 */
@SuppressWarnings("serial")
public class CustomPcException extends DataAccessException{
	
	private String errCde;
	private Map<String, Object> responseMap;
	
	public void put(String key, Object value) {
		if (responseMap == null)
			responseMap = new HashMap<String, Object>();
		responseMap.put(key, value);
	}
	

	
	public Object get(String key) {
		if (responseMap == null)
			responseMap = new HashMap<String, Object>();
		return responseMap.get(key);
	}
	
	
	public String getErrCde() {
		return errCde;
	}

	public void setErrCde(String errCde) {
		this.errCde = errCde;
	}
	
	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}

	public CustomPcException(String errCde, String msg) {		
		super(msg);
		this.errCde=errCde;		
	}
	
	/**
	 * Constructor for DataIntegrityViolationException.
	 * @param msg the detail message
	 */
	public CustomPcException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for DataIntegrityViolationException.
	 * @param msg the detail message
	 * @param cause the root cause from the data access API in use
	 */
	public CustomPcException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
