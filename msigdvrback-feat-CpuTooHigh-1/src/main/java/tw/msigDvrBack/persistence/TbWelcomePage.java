package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbWelcomePage implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.page_type
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private String pageType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.image_url
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private String imageUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.image_time
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private Date imageTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.is_display
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private String isDisplay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.cr_user
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private String crUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.cr_date
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private Date crDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.userstamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private String userstamp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_welcome_page.datestamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private Date datestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_welcome_page
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.page_type
     *
     * @return the value of tb_welcome_page.page_type
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public String getPageType() {
        return pageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.page_type
     *
     * @param pageType the value for tb_welcome_page.page_type
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setPageType(String pageType) {
        this.pageType = pageType == null ? null : pageType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.image_url
     *
     * @return the value of tb_welcome_page.image_url
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.image_url
     *
     * @param imageUrl the value for tb_welcome_page.image_url
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.image_time
     *
     * @return the value of tb_welcome_page.image_time
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public Date getImageTime() {
        return imageTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.image_time
     *
     * @param imageTime the value for tb_welcome_page.image_time
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setImageTime(Date imageTime) {
        this.imageTime = imageTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.is_display
     *
     * @return the value of tb_welcome_page.is_display
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public String getIsDisplay() {
        return isDisplay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.is_display
     *
     * @param isDisplay the value for tb_welcome_page.is_display
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay == null ? null : isDisplay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.cr_user
     *
     * @return the value of tb_welcome_page.cr_user
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public String getCrUser() {
        return crUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.cr_user
     *
     * @param crUser the value for tb_welcome_page.cr_user
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setCrUser(String crUser) {
        this.crUser = crUser == null ? null : crUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.cr_date
     *
     * @return the value of tb_welcome_page.cr_date
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.cr_date
     *
     * @param crDate the value for tb_welcome_page.cr_date
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.userstamp
     *
     * @return the value of tb_welcome_page.userstamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public String getUserstamp() {
        return userstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.userstamp
     *
     * @param userstamp the value for tb_welcome_page.userstamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setUserstamp(String userstamp) {
        this.userstamp = userstamp == null ? null : userstamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_welcome_page.datestamp
     *
     * @return the value of tb_welcome_page.datestamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public Date getDatestamp() {
        return datestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_welcome_page.datestamp
     *
     * @param datestamp the value for tb_welcome_page.datestamp
     *
     * @mbg.generated Mon Oct 22 15:24:14 GMT+08:00 2018
     */
    public void setDatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }
}