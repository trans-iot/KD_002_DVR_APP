package tw.msigDvrBack.manual;

import java.util.HashMap;
import java.util.List;

import tw.msigDvrBack.persistence.TbCustCar;

public interface TbCustCarManualMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    int insert(TbCustCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    int insertSelective(TbCustCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    List<TbCustCar> selectByExample(HashMap<String,Object> queryMap);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    TbCustCar selectByPrimaryKey(String userId);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    int updateByPrimaryKeySelective(TbCustCar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_cust_car
     *
     * @mbg.generated Fri Nov 02 12:05:45 GMT+08:00 2018
     */
    int updateByPrimaryKey(TbCustCar record);
}