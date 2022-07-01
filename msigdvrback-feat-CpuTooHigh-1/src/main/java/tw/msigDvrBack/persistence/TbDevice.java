package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbDevice extends TbDeviceKey implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.device_status
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String deviceStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.user_id
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.bind_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date bindDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.dev_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date devEffDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.dev_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date devEndDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.target_no
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String targetNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.service_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String serviceType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.sim_mobile
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String simMobile;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.sim_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date simEffDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.sim_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date simEndDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.upload_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date uploadDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.cr_user
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String crUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.cr_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date crDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.userstamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String userstamp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.datestamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private Date datestamp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_device
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.device_status
	 * @return  the value of tb_device.device_status
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.device_status
	 * @param deviceStatus  the value for tb_device.device_status
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus == null ? null : deviceStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.user_id
	 * @return  the value of tb_device.user_id
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.user_id
	 * @param userId  the value for tb_device.user_id
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.bind_date
	 * @return  the value of tb_device.bind_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getBindDate() {
		return bindDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.bind_date
	 * @param bindDate  the value for tb_device.bind_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.dev_eff_date
	 * @return  the value of tb_device.dev_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getDevEffDate() {
		return devEffDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.dev_eff_date
	 * @param devEffDate  the value for tb_device.dev_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setDevEffDate(Date devEffDate) {
		this.devEffDate = devEffDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.dev_end_date
	 * @return  the value of tb_device.dev_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getDevEndDate() {
		return devEndDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.dev_end_date
	 * @param devEndDate  the value for tb_device.dev_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setDevEndDate(Date devEndDate) {
		this.devEndDate = devEndDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.target_no
	 * @return  the value of tb_device.target_no
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getTargetNo() {
		return targetNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.target_no
	 * @param targetNo  the value for tb_device.target_no
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setTargetNo(String targetNo) {
		this.targetNo = targetNo == null ? null : targetNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.service_type
	 * @return  the value of tb_device.service_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.service_type
	 * @param serviceType  the value for tb_device.service_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType == null ? null : serviceType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.sim_mobile
	 * @return  the value of tb_device.sim_mobile
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getSimMobile() {
		return simMobile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.sim_mobile
	 * @param simMobile  the value for tb_device.sim_mobile
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setSimMobile(String simMobile) {
		this.simMobile = simMobile == null ? null : simMobile.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.sim_eff_date
	 * @return  the value of tb_device.sim_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getSimEffDate() {
		return simEffDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.sim_eff_date
	 * @param simEffDate  the value for tb_device.sim_eff_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setSimEffDate(Date simEffDate) {
		this.simEffDate = simEffDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.sim_end_date
	 * @return  the value of tb_device.sim_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getSimEndDate() {
		return simEndDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.sim_end_date
	 * @param simEndDate  the value for tb_device.sim_end_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setSimEndDate(Date simEndDate) {
		this.simEndDate = simEndDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.upload_date
	 * @return  the value of tb_device.upload_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.upload_date
	 * @param uploadDate  the value for tb_device.upload_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.cr_user
	 * @return  the value of tb_device.cr_user
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getCrUser() {
		return crUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.cr_user
	 * @param crUser  the value for tb_device.cr_user
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setCrUser(String crUser) {
		this.crUser = crUser == null ? null : crUser.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.cr_date
	 * @return  the value of tb_device.cr_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getCrDate() {
		return crDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.cr_date
	 * @param crDate  the value for tb_device.cr_date
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.userstamp
	 * @return  the value of tb_device.userstamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getUserstamp() {
		return userstamp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.userstamp
	 * @param userstamp  the value for tb_device.userstamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp == null ? null : userstamp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.datestamp
	 * @return  the value of tb_device.datestamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public Date getDatestamp() {
		return datestamp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.datestamp
	 * @param datestamp  the value for tb_device.datestamp
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}
}