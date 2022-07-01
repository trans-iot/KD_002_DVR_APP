package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

import tw.msigDvrBack.persistence.TbLookupType;
import tw.msigDvrBack.smdd001m.SMDD001FQueryForm;

/**
 * 參考資料代碼維護查詢
 * SMDD001F_SMDD001F
 * 
 * @author Gary
 * @since 2013/01/03
 */
public interface TbLookupTypeManualMapper 
{
	/**
	 * 
	 * <pre>
	 * Method Name : getLookupTypeData
	 * Description : 
	 * </pre>
	 * @since 2013/1/4
	 * @author gary 
	 *
	 * @return List<Map<String,Object>> TODO
	 * lookupType		公用參數種類
	 * dscr				說明
	 * valueDscr		數值備註
	 * type1Dscr		類別1備註
	 * type2Dscr		類別2備註
	 * type3Dscr		類別3備註
	 * crUser			建立人員
	 * crDate			建立日期
	 * userStamp		異動人員
	 * dateStamp		異動日期
	 * moduleCde		歸屬模組代碼 (MDCDE)
	 * sysIndic			Y:系統參數; N: 使用者參數
	 * moduleCdeDscr	lookupCde 中文 dscr
	 */
	List<Map<String, Object>> getLookupTypeData(SMDD001FQueryForm params);
	/**
	 *  
	 * <pre>
	 * Method Name : countTotal
	 * Description : 依據條件取得 cw_tb_lookup_type 總數量
	 * </pre>
	 * @since 2013/1/4
	 * @author gary 
	 *
	 * @param params
	 * @return int TODO
	 */
	int countTotal(SMDD001FQueryForm params);
	
	/**
	 * <pre>
	 * Method Name : getDscr
	 * Description : 依據交易模式Cde取得說明
	 * </pre>
	 * @since 2013/1/22
	 * @author Finn
	 * 
	 * @param transMode
	 * @return String dscr
	 */
	String getTransDscr(String transMode);
	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int countByExample(Map<String,Object> map);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int deleteByPrimaryKey(String lookupType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int insert(TbLookupType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int insertSelective(TbLookupType record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    TbLookupType selectByPrimaryKey(String lookupType);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int updateByPrimaryKeySelective(TbLookupType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_lookup_type
     *
     * @mbggenerated Tue Aug 25 09:19:27 CST 2015
     */
    int updateByPrimaryKey(TbLookupType record);
}