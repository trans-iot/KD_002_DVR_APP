package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbCustOpenid extends TbCustOpenidKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.OPEN_ID
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.EMAIL
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.MOBILE_PHONE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private String mobilePhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.CR_USER
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private String crUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.CR_DATE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private Date crDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.USERSTAMP
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private String userstamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_cust_openid.datestamp
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private Date datestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_cust_openid
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.OPEN_ID
     *
     * @return the value of tb_cust_openid.OPEN_ID
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.OPEN_ID
     *
     * @param openId the value for tb_cust_openid.OPEN_ID
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.EMAIL
     *
     * @return the value of tb_cust_openid.EMAIL
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.EMAIL
     *
     * @param email the value for tb_cust_openid.EMAIL
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.MOBILE_PHONE
     *
     * @return the value of tb_cust_openid.MOBILE_PHONE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.MOBILE_PHONE
     *
     * @param mobilePhone the value for tb_cust_openid.MOBILE_PHONE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.CR_USER
     *
     * @return the value of tb_cust_openid.CR_USER
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public String getCrUser() {
        return crUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.CR_USER
     *
     * @param crUser the value for tb_cust_openid.CR_USER
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setCrUser(String crUser) {
        this.crUser = crUser == null ? null : crUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.CR_DATE
     *
     * @return the value of tb_cust_openid.CR_DATE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.CR_DATE
     *
     * @param crDate the value for tb_cust_openid.CR_DATE
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.USERSTAMP
     *
     * @return the value of tb_cust_openid.USERSTAMP
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public String getUserstamp() {
        return userstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.USERSTAMP
     *
     * @param userstamp the value for tb_cust_openid.USERSTAMP
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setUserstamp(String userstamp) {
        this.userstamp = userstamp == null ? null : userstamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_cust_openid.datestamp
     *
     * @return the value of tb_cust_openid.datestamp
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public Date getdatestamp() {
        return datestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_cust_openid.datestamp
     *
     * @param datestamp the value for tb_cust_openid.datestamp
     *
     * @mbggenerated Tue May 31 11:54:18 GMT+08:00 2016
     */
    public void setdatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }
}