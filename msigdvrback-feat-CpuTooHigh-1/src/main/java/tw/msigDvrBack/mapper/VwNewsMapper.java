package tw.msigDvrBack.mapper;

import java.util.List;
import java.util.Map;

import tw.msigDvrBack.persistence.VwNews;


public interface VwNewsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */
    int insert(VwNews record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */
    int insertSelective(VwNews record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */
   

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vw_news
     *
     * @mbg.generated Wed Oct 24 11:10:26 GMT+08:00 2018
     */
    
    
	/**
	 * <pre>
	 * Method Name : selectByExampleManual
	 * Description : 因應弱點掃描修改的方法
	 * 
	 * </pre>
	 * @since 2018/11/20
	 * @author Jerry
	 * 
	 * @param map
	 * @return list
	 */
	 List<VwNews> selectHourValue(Map<String, Object> map);
    
}