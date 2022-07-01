/**
 *  
 *  Any new add value in this class, need add to decorator.jsp
 *  and doing the transform mapping.
 *   
 *  @since 1.0 
 *  @author : alanlin
 *  @since: Aug 8, 2012
 **/
package tw.msigDvrBack.common;

public enum AjaxResultEnum {
	/**
	 * For system checking, use < 0
	 * For page checking, use > 0
	 */
	
	QUERY_FAIL(1),
	INSERT_FAIL(2),
	UPDATE_FAIL(3),
	COPY_FAIL(4),
	DELETE_FAIL(5),
	OK(0),
	FAIL(-1),
	LOGIN_TIMEOUT(-10);
	
	private Integer result;
	AjaxResultEnum(Integer result) {
		this.result = result;
	}
	
	public Integer getResult() {
		return result; 
	}
	
}
