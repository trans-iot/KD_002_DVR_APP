package tw.msigDvrBack.manual;

import java.util.Date;

import tw.msigDvrBack.persistence.TbDeviceHis;


public interface TbDeviceHisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */

    
    int deleteBySysdateDiff(Date crDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    int deleteByPrimaryKey(Integer seqNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    int insert(TbDeviceHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    int insertSelective(TbDeviceHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    TbDeviceHis selectByPrimaryKey(Integer seqNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    int updateByPrimaryKeySelective(TbDeviceHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_device_his
     *
     * @mbg.generated Wed Oct 24 16:14:48 GMT+08:00 2018
     */
    int updateByPrimaryKey(TbDeviceHis record);
}