package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

import tw.msigDvrBack.persistence.VwSysUser;

public interface VwSysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_sys_user
     *
     * @mbg.generated Tue Sep 25 14:37:02 CST 2018
     */
    long countByExample(Map<String, Object> querymap);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_sys_user
     *
     * @mbg.generated Tue Sep 25 14:37:02 CST 2018
     */
    int insert(VwSysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_sys_user
     *
     * @mbg.generated Tue Sep 25 14:37:02 CST 2018
     */
    int insertSelective(VwSysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_sys_user
     *
     * @mbg.generated Tue Sep 25 14:37:02 CST 2018
     */
    List<VwSysUser> selectByExample(Map<String, Object> map);

}