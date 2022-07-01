package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tw.msigDvrBack.persistence.TbSysUser;
import tw.msigDvrBack.persistence.TbSysUserExample;

public interface TbSysUserMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	long countByExample(Map<String, Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int deleteByExample(TbSysUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int deleteByPrimaryKey(String sysUserId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int insert(TbSysUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int insertSelective(TbSysUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	List<TbSysUser> selectByExample(Map<String, Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	TbSysUser selectByPrimaryKey(String sysUserId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int updateByExampleSelective(@Param("record") TbSysUser record, @Param("example") TbSysUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int updateByExample(@Param("record") TbSysUser record, @Param("example") TbSysUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int updateByPrimaryKeySelective(TbSysUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_sys_user
	 * @mbg.generated  Tue Jun 02 09:06:53 GMT+08:00 2020
	 */
	int updateByPrimaryKey(TbSysUser record);
}