package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

/**
 * 
 * 
 * OMOM002FUpdCarForm.java
 * 
 * @since 2018/11/26
 * @author Bob
 */
public class OMOM002FUpdCarForm extends BaseForm implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 704529273281961057L;
	/**
	 * 會員編號
	 */
	private String userId;
	/**
	 * 保險生效日
	 */
	private String effUbiDate;
	/**
	 * 保險到期日
	 */
	private String endUbiDate;
	/**
	 * 保險服務人員
	 */
	private String servicer;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEffUbiDate() {
		return effUbiDate;
	}
	public void setEffUbiDate(String effUbiDate) {
		this.effUbiDate = effUbiDate;
	}
	public String getEndUbiDate() {
		return endUbiDate;
	}
	public void setEndUbiDate(String endUbiDate) {
		this.endUbiDate = endUbiDate;
	}
	public String getServicer() {
		return servicer;
	}
	public void setServicer(String servicer) {
		this.servicer = servicer;
	}
			
	

}
