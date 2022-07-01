package tw.msigDvrBack.smdd001m;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.persistence.TbDevice;

public class SMDD003FModForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String[] COPYIGNORE = new String[]{"dob", "password","devEffDate","devEndDate","simEffDate","simEndDate",
			"appLoginTime","registerTime","unuseDvrCnt","warmChgPwDt","bindDate","uploadDate", "crDate"};

	private String action;

	// *USER ID
	private String userId;

	// 中文姓名
	private String userName;

	// 英文姓名
	private String engName;

	// 暱稱
	private String nickname;

	// 電子信箱
	private String email;

	// 生日
	private String dob;

	// 聯絡電話
	private String contactPhone;

	// 地址
	private String addr;

	// 手機門號
	private String mobilePhone;

	// *客戶狀態
	private String custStatus;

	// 大頭照URL
	private String picUrl;

	// 性別
	private String sex;
	
	//密碼
	@SerializedName("password")
	@JsonProperty("password")
	private String psd;

	private String crUser;

	private String crDate;

	private String userstamp;

	private Date datestamp;

	private String cuid;

	private Date agreeTime;
	
	private String agreeTimeDscr;

	private String accCtcName;

	private String accCtcMobile;

	@SerializedName("pwdStatus")
	@JsonProperty("pwdStatus")
	private String psdStatus;

	private String registerTime;
	
	private String carNo;
	
	private String unuseDvrCnt;
	
	private String remarks;
	
	private String warmChgPwDt;
	
	private String appLoginTime;
	
	
	//設備資料
	//設備類型
	private String deviceType;
	//DVR S/N
	private String sn;
	//DVR imei
	private String imei;
	//設備狀態
	private String deviceStatus;
	private String deviceStatusDscr;
	//綁定日期
	private String bindDate;
	//設備資料上傳時間
	private String uploadDate;
	//設備有效期間起始日
	private String devEffDate;
	//設備有效期間結束日
	private String devEndDate;
	//SIM卡門號
	private String simMobile;
	//服務類別
	private String serviceType;
	private String serviceTypeDscr;
	//SIM卡有效起始日
	private String simEffDate;
	//SIM卡有效結束日
	private String simEndDate;
	//綁定標的號碼
	private String targetNo;
	
	private TbDevice tbDevice;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public Date getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(Date agreeTime) {
		this.agreeTime = agreeTime;
	}

	public String getAccCtcName() {
		return accCtcName;
	}

	public void setAccCtcName(String accCtcName) {
		this.accCtcName = accCtcName;
	}

	public String getAccCtcMobile() {
		return accCtcMobile;
	}

	public void setAccCtcMobile(String accCtcMobile) {
		this.accCtcMobile = accCtcMobile;
	}

	public String getPsdStatus() {
		return psdStatus;
	}

	public void setPsdStatus(String psdStatus) {
		this.psdStatus = psdStatus;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getUnuseDvrCnt() {
		return unuseDvrCnt;
	}

	public void setUnuseDvrCnt(String unuseDvrCnt) {
		this.unuseDvrCnt = unuseDvrCnt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getWarmChgPwDt() {
		return warmChgPwDt;
	}

	public void setWarmChgPwDt(String warmChgPwDt) {
		this.warmChgPwDt = warmChgPwDt;
	}

	public String getAppLoginTime() {
		return appLoginTime;
	}

	public void setAppLoginTime(String appLoginTime) {
		this.appLoginTime = appLoginTime;
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

	public String getDeviceStatusDscr() {
		return deviceStatusDscr;
	}

	public void setDeviceStatusDscr(String deviceStatusDscr) {
		this.deviceStatusDscr = deviceStatusDscr;
	}

	public String getBindDate() {
		return bindDate;
	}

	public void setBindDate(String bindDate) {
		this.bindDate = bindDate;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDevEffDate() {
		return devEffDate;
	}

	public void setDevEffDate(String devEffDate) {
		this.devEffDate = devEffDate;
	}

	public String getDevEndDate() {
		return devEndDate;
	}

	public void setDevEndDate(String devEndDate) {
		this.devEndDate = devEndDate;
	}

	public String getSimMobile() {
		return simMobile;
	}

	public void setSimMobile(String simMobile) {
		this.simMobile = simMobile;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeDscr() {
		return serviceTypeDscr;
	}

	public void setServiceTypeDscr(String serviceTypeDscr) {
		this.serviceTypeDscr = serviceTypeDscr;
	}

	public String getSimEffDate() {
		return simEffDate;
	}

	public void setSimEffDate(String simEffDate) {
		this.simEffDate = simEffDate;
	}

	public String getSimEndDate() {
		return simEndDate;
	}

	public void setSimEndDate(String simEndDate) {
		this.simEndDate = simEndDate;
	}

	public String getAgreeTimeDscr() {
		return agreeTimeDscr;
	}

	public void setAgreeTimeDscr(String agreeTimeDscr) {
		this.agreeTimeDscr = agreeTimeDscr;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}

	public TbDevice getTbDevice() {
		return tbDevice;
	}

	public void setTbDevice(TbDevice tbDevice) {
		this.tbDevice = tbDevice;
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

	public String getTargetNo() {
		return targetNo;
	}

	public void setTargetNo(String targetNo) {
		this.targetNo = targetNo;
	}
}
