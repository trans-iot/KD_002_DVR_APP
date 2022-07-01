package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM003FQueryForm.java
 * 
 * @since 2018/10/22
 * @author Bob
 */
public class OMOM003FQueryForm extends BaseForm implements Serializable {

	
	private static final long serialVersionUID = 5554059460862501354L;
	/**
	 * 設備種類
	 */
	private String deviceType;
	/**
	 * DVR IMEI
	 */
	private String imei;
	/**
	 * SN
	 */
	private String sn;
	/**
	 * 綁定標的號碼
	 */
	private String targetNo;
	/**
	 * SIM卡門號
	 */
	private String simMobile;
	
	/**
	 * 設備狀態
	 */
	private String deviceStatus;
	/**
	 * 綁定會員
	 */
	private String userId;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getTargetNo() {
		return targetNo;
	}

	public void setTargetNo(String targetNo) {
		this.targetNo = targetNo;
	}

	public String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(String simMobile) {
		this.simMobile = simMobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}