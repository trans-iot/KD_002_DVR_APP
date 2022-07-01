/**
 *  @since 1.0 
 *  @author : alanlin
 *  @since: Jul 30, 2012
 **/
package tw.msigDvrBack.common;

import java.io.Serializable;
import java.util.Map;

public class AjaxForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 狀態碼
	 */
	private AjaxResultEnum result;
	
	/**
	 * Ajax 訊息
	 */
	private String message;
	
	/**
	 * json string
	 */
	private Map<String, Object> map;
	
	public AjaxResultEnum getResult() {
		return result;
	}

	public void setResult(AjaxResultEnum result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
