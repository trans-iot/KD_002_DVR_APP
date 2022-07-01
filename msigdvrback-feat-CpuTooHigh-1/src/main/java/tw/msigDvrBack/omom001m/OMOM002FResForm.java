package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

/**
 * 
 * 
 * OMOM002FRomForm.java
 * 
 * @since 2018/10/22
 * @author Bob
 */
public class OMOM002FResForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7582888098660323190L;
	
	/**
	 * 綁定會員
	 */
	private String user_id;
	/**
	 * 行車紀錄是否歸零重算
	 */
	private String is_reset;
	/**
	 * 新車號
	 */
	private String newCarNo;
	/**
	 * 修改人員
	 */
	private String userstamp;
	/**
	 * 交易時間
	 */
	private String trx_time;

	public String getNewCarNo() {
		return newCarNo;
	}

	public void setNewCarNo(String newCarNo) {
		this.newCarNo = newCarNo;
	}

	public String getUserstamp() {
		return userstamp;
	}

	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp;
	}

	public String getTrx_time() {
		return trx_time;
	}

	public void setTrx_time(String trx_time) {
		this.trx_time = trx_time;
	}

	public String getIs_reset() {
		return is_reset;
	}

	public void setIs_reset(String is_reset) {
		this.is_reset = is_reset;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	

}
