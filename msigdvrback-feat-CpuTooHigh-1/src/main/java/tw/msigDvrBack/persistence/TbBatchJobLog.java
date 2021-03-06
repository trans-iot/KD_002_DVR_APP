package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbBatchJobLog implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.seq_no
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private Integer seqNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.job_pg_name
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String jobPgName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.job_dscr
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String jobDscr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.run_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private Date runDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.err_cde
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String errCde;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.err_msg
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String errMsg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.cr_user
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String crUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.cr_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private Date crDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.userstamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private String userstamp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_batch_job_log.datestamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private Date datestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_batch_job_log
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.seq_no
     *
     * @return the value of tb_batch_job_log.seq_no
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.seq_no
     *
     * @param seqNo the value for tb_batch_job_log.seq_no
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.job_pg_name
     *
     * @return the value of tb_batch_job_log.job_pg_name
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getJobPgName() {
        return jobPgName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.job_pg_name
     *
     * @param jobPgName the value for tb_batch_job_log.job_pg_name
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setJobPgName(String jobPgName) {
        this.jobPgName = jobPgName == null ? null : jobPgName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.job_dscr
     *
     * @return the value of tb_batch_job_log.job_dscr
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getJobDscr() {
        return jobDscr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.job_dscr
     *
     * @param jobDscr the value for tb_batch_job_log.job_dscr
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setJobDscr(String jobDscr) {
        this.jobDscr = jobDscr == null ? null : jobDscr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.run_date
     *
     * @return the value of tb_batch_job_log.run_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public Date getRunDate() {
        return runDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.run_date
     *
     * @param runDate the value for tb_batch_job_log.run_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.err_cde
     *
     * @return the value of tb_batch_job_log.err_cde
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getErrCde() {
        return errCde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.err_cde
     *
     * @param errCde the value for tb_batch_job_log.err_cde
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setErrCde(String errCde) {
        this.errCde = errCde == null ? null : errCde.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.err_msg
     *
     * @return the value of tb_batch_job_log.err_msg
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.err_msg
     *
     * @param errMsg the value for tb_batch_job_log.err_msg
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.cr_user
     *
     * @return the value of tb_batch_job_log.cr_user
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getCrUser() {
        return crUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.cr_user
     *
     * @param crUser the value for tb_batch_job_log.cr_user
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setCrUser(String crUser) {
        this.crUser = crUser == null ? null : crUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.cr_date
     *
     * @return the value of tb_batch_job_log.cr_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.cr_date
     *
     * @param crDate the value for tb_batch_job_log.cr_date
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.userstamp
     *
     * @return the value of tb_batch_job_log.userstamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public String getUserstamp() {
        return userstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.userstamp
     *
     * @param userstamp the value for tb_batch_job_log.userstamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setUserstamp(String userstamp) {
        this.userstamp = userstamp == null ? null : userstamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_batch_job_log.datestamp
     *
     * @return the value of tb_batch_job_log.datestamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public Date getDatestamp() {
        return datestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_batch_job_log.datestamp
     *
     * @param datestamp the value for tb_batch_job_log.datestamp
     *
     * @mbg.generated Fri Jun 05 13:53:54 CST 2020
     */
    public void setDatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }
}