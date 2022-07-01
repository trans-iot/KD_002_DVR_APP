package tw.msigDvrBack.persistence;

import java.io.Serializable;

public class TbDeviceKey implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.device_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String deviceType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.imei
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String imei;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tb_device.sn
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private String sn;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table tb_device
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.device_type
	 * @return  the value of tb_device.device_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.device_type
	 * @param deviceType  the value for tb_device.device_type
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType == null ? null : deviceType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.imei
	 * @return  the value of tb_device.imei
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.imei
	 * @param imei  the value for tb_device.imei
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setImei(String imei) {
		this.imei = imei == null ? null : imei.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tb_device.sn
	 * @return  the value of tb_device.sn
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tb_device.sn
	 * @param sn  the value for tb_device.sn
	 * @mbg.generated  Fri Jun 05 13:39:52 CST 2020
	 */
	public void setSn(String sn) {
		this.sn = sn == null ? null : sn.trim();
	}
}