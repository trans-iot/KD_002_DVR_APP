package tw.msigDvrBack.persistence;

import java.io.Serializable;

public class TbSysUserRoleMapKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_user_role_map.sys_user_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    private String sysUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_user_role_map.role_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_sys_user_role_map
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_user_role_map.sys_user_id
     *
     * @return the value of tb_sys_user_role_map.sys_user_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    public String getSysUserId() {
        return sysUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_user_role_map.sys_user_id
     *
     * @param sysUserId the value for tb_sys_user_role_map.sys_user_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_user_role_map.role_id
     *
     * @return the value of tb_sys_user_role_map.role_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_user_role_map.role_id
     *
     * @param roleId the value for tb_sys_user_role_map.role_id
     *
     * @mbg.generated Wed Sep 26 11:04:46 CST 2018
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}