package tw.msigDvrBack.persistence;

import java.io.Serializable;

public class TbSysRolePgMapKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.role_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sys_role_pg_map.pg_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private String pgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_sys_role_pg_map
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.role_id
     *
     * @return the value of tb_sys_role_pg_map.role_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.role_id
     *
     * @param roleId the value for tb_sys_role_pg_map.role_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sys_role_pg_map.pg_id
     *
     * @return the value of tb_sys_role_pg_map.pg_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public String getPgId() {
        return pgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sys_role_pg_map.pg_id
     *
     * @param pgId the value for tb_sys_role_pg_map.pg_id
     *
     * @mbg.generated Fri Oct 05 15:13:56 CST 2018
     */
    public void setPgId(String pgId) {
        this.pgId = pgId == null ? null : pgId.trim();
    }
}