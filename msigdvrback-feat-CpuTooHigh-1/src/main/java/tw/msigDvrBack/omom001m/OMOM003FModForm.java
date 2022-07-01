package tw.msigDvrBack.omom001m;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.persistence.TbCustomer;

/**
 * 
 * 
 * OMOM003FModForm.java
 * 
 * @since 2018/10/22
 * @author Bob
 */
public class OMOM003FModForm extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 設備種類
	 */
	private String deviceType;
	/**
	 * 設備種類dscr
	 */
	private String deviceTypeDscr;
	/**
	 * MAC imei
	 */
	private String imei;
	/**
	 * SN
	 */
	private String sn;
	/**
	 * 設備狀態
	 */
	private String deviceStatus;
	/**
	 * 設備狀態dscr
	 */
	private String deviceStatusDscr;
	/**
	 * 綁定會員
	 */
	private String userId;
	/**
	 * 綁定日期
	 */
	private Date bindDate;
	/**
	 * 修改人員
	 */
	private String userstamp;
	/**
	 * 修改日期
	 */
	private Date datestamp;
	private Date devEffDate;
	private Date devEndDate;
	private String targetNo;
	private String serviceType;
	private String simMobile;
	private Date simEffDate;
	private Date simEndDate;
	private Date uploadDate;
	
	private String action;

	//人員資料
	private TbCustomer tbCustomer;
	
	private String dobDscr;
	
	private String registerTimeDscr;
	
	private String agreeTimeDscr;
	
	private String appLoginTimeDscr;
	
	
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public String getUserstamp() {
		return userstamp;
	}

	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDeviceTypeDscr() {
		return deviceTypeDscr;
	}

	public void setDeviceTypeDscr(String deviceTypeDscr) {
		this.deviceTypeDscr = deviceTypeDscr;
	}

	public String getDeviceStatusDscr() {
		return deviceStatusDscr;
	}

	public void setDeviceStatusDscr(String deviceStatusDscr) {
		this.deviceStatusDscr = deviceStatusDscr;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getDevEffDate() {
		return devEffDate;
	}

	public void setDevEffDate(Date devEffDate) {
		this.devEffDate = devEffDate;
	}

	public Date getDevEndDate() {
		return devEndDate;
	}

	public void setDevEndDate(Date devEndDate) {
		this.devEndDate = devEndDate;
	}

	public String getTargetNo() {
		return targetNo;
	}

	public void setTargetNo(String targetNo) {
		this.targetNo = targetNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(String simMobile) {
		this.simMobile = simMobile;
	}

	public Date getSimEffDate() {
		return simEffDate;
	}

	public void setSimEffDate(Date simEffDate) {
		this.simEffDate = simEffDate;
	}

	public Date getSimEndDate() {
		return simEndDate;
	}

	public void setSimEndDate(Date simEndDate) {
		this.simEndDate = simEndDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public TbCustomer getTbCustomer() {
		return tbCustomer;
	}

	public void setTbCustomer(TbCustomer tbCustomer) {
		this.tbCustomer = tbCustomer;
	}

	public String getDobDscr() {
		return dobDscr;
	}

	public void setDobDscr(String dobDscr) {
		this.dobDscr = dobDscr;
	}

	public String getRegisterTimeDscr() {
		return registerTimeDscr;
	}

	public void setRegisterTimeDscr(String registerTimeDscr) {
		this.registerTimeDscr = registerTimeDscr;
	}

	public String getAgreeTimeDscr() {
		return agreeTimeDscr;
	}

	public void setAgreeTimeDscr(String agreeTimeDscr) {
		this.agreeTimeDscr = agreeTimeDscr;
	}

	public String getAppLoginTimeDscr() {
		return appLoginTimeDscr;
	}

	public void setAppLoginTimeDscr(String appLoginTimeDscr) {
		this.appLoginTimeDscr = appLoginTimeDscr;
	}

}
