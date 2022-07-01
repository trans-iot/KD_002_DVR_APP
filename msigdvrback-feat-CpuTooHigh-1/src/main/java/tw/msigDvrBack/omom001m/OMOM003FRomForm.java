package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

/**
 * 
 * 
 * OMOM003FRomForm.java
 * 
 * @since 2018/10/22
 * @author Bob
 */
public class OMOM003FRomForm  extends BaseForm implements Serializable {
	
	
	private static final long serialVersionUID = -2183264063319389892L;
	/**
	 * 設備種類
	 */
	private String device_type;
	/**
	 * DVR IMEI
	 */
	private String imei;
	/**
	 * SN
	 */
	private String sn;
	/**
	 * 綁定會員
	 */
	private String user_id;
	/**
	 * 修改人員
	 */
	private String userstamp;
	/**
	 * 交易時間
	 */
	private String trx_time;
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
}
