package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbDeviceKey;
import org.apache.ibatis.annotations.Param;
import tw.msigDvrBack.persistence.TbDeviceExample;

public interface TbDeviceManualMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	long countByExample(Map<String, Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int deleteByExample(TbDeviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int deleteByPrimaryKey(TbDeviceKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int insert(TbDevice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int insertSelective(TbDevice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	List<TbDevice> selectByExample(Map<String, Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	TbDevice selectByPrimaryKey(TbDeviceKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int updateByExampleSelective(@Param("record") TbDevice record, @Param("example") TbDeviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int updateByExample(@Param("record") TbDevice record, @Param("example") TbDeviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int updateByPrimaryKeySelective(TbDevice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tb_device
	 * @mbg.generated  Tue Jun 02 11:30:13 GMT+08:00 2020
	 */
	int updateByPrimaryKey(TbDevice record);
}