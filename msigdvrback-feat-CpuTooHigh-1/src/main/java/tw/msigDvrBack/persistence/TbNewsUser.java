package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbNewsUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private Integer seqNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.news_seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private Integer newsSeqNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.user_id
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.push_time
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private Date pushTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.push_status
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private String pushStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.cr_user
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private String crUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.cr_date
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private Date crDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.userstamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private String userstamp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_news_user.datestamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private Date datestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_news_user
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.seq_no
     *
     * @return the value of tb_news_user.seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.seq_no
     *
     * @param seqNo the value for tb_news_user.seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.news_seq_no
     *
     * @return the value of tb_news_user.news_seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public Integer getNewsSeqNo() {
        return newsSeqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.news_seq_no
     *
     * @param newsSeqNo the value for tb_news_user.news_seq_no
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setNewsSeqNo(Integer newsSeqNo) {
        this.newsSeqNo = newsSeqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.user_id
     *
     * @return the value of tb_news_user.user_id
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.user_id
     *
     * @param userId the value for tb_news_user.user_id
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.push_time
     *
     * @return the value of tb_news_user.push_time
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public Date getPushTime() {
        return pushTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.push_time
     *
     * @param pushTime the value for tb_news_user.push_time
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.push_status
     *
     * @return the value of tb_news_user.push_status
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public String getPushStatus() {
        return pushStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.push_status
     *
     * @param pushStatus the value for tb_news_user.push_status
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus == null ? null : pushStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.cr_user
     *
     * @return the value of tb_news_user.cr_user
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public String getCrUser() {
        return crUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.cr_user
     *
     * @param crUser the value for tb_news_user.cr_user
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setCrUser(String crUser) {
        this.crUser = crUser == null ? null : crUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.cr_date
     *
     * @return the value of tb_news_user.cr_date
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.cr_date
     *
     * @param crDate the value for tb_news_user.cr_date
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.userstamp
     *
     * @return the value of tb_news_user.userstamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public String getUserstamp() {
        return userstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.userstamp
     *
     * @param userstamp the value for tb_news_user.userstamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setUserstamp(String userstamp) {
        this.userstamp = userstamp == null ? null : userstamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_news_user.datestamp
     *
     * @return the value of tb_news_user.datestamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public Date getDatestamp() {
        return datestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_news_user.datestamp
     *
     * @param datestamp the value for tb_news_user.datestamp
     *
     * @mbg.generated Wed Oct 24 12:10:20 GMT+08:00 2018
     */
    public void setDatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }
}