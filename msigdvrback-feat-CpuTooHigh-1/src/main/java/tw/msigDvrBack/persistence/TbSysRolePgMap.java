package tw.msigDvrBack.persistence;

import java.io.Serializable;
import java.util.Date;

public class TbSysRolePgMap extends TbSysRolePgMapKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.cr_user
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private String crUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.cr_date
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private Date crDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.userstamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private String userstamp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.datestamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private Date datestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_sys_role_pg_map
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.cr_user
     *
     * @return the value of tb_sys_role_pg_map.cr_user
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public String getCrUser() {
        return crUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.cr_user
     *
     * @param crUser the value for tb_sys_role_pg_map.cr_user
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setCrUser(String crUser) {
        this.crUser = crUser == null ? null : crUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.cr_date
     *
     * @return the value of tb_sys_role_pg_map.cr_date
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.cr_date
     *
     * @param crDate the value for tb_sys_role_pg_map.cr_date
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.userstamp
     *
     * @return the value of tb_sys_role_pg_map.userstamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public String getUserstamp() {
        return userstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.userstamp
     *
     * @param userstamp the value for tb_sys_role_pg_map.userstamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setUserstamp(String userstamp) {
        this.userstamp = userstamp == null ? null : userstamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.datestamp
     *
     * @return the value of tb_sys_role_pg_map.datestamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public Date getDatestamp() {
        return datestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.datestamp
     *
     * @param datestamp the value for tb_sys_role_pg_map.datestamp
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setDatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }
}