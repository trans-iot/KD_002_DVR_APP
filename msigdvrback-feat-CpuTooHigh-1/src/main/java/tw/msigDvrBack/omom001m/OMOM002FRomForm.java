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
@SuppressWarnings("serial")
public class OMOM002FRomForm  extends BaseForm implements Serializable {
	
	/**
	 * 設備種類
	 */
	private String device_type;
	/**
	 * MAC ADDERESS
	 */
	private String mac_address;
	/**
	 * SN
	 */
	private String sn;
	/**
	 * 綁定會員
	 */
	private String user_id;
	/**
	 * 行車紀錄是否歸零重算
	 */
	private String is_reset;
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
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
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
	public String getIs_reset() {
		return is_reset;
	}
	public void setIs_reset(String is_reset) {
		this.is_reset = is_reset;
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
	
	
	
}
