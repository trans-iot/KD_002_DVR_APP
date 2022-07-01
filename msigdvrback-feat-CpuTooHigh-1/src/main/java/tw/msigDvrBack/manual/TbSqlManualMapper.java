package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

public interface TbSqlManualMapper {

	public Map<String,Object> selectMaxCrdateWithTbSysUserPwdHis(String sysUserId);
	
	/**
	 * <pre>
	 * @description 查詢代碼描述
	 * @method fnPublicQueryLookupDscr
	 * <pre>
	 * @param map
	 * @return 代碼描述
	 * 
	 * @author Marks
	 * @since 2020/06/03
	 */
	public String fnPublicQueryLookupDscr(Map<String,Object> map);
	
	/**
	 * <pre>
	 * @description 取得亂數密碼
	 * @method getRandomPwd
	 * <pre>
	 * @return 亂數密碼
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public String getRandomPwd();
	
	/**
	 * <pre>
	 * @description 取得會員編號
	 * @method getUserId
	 * <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/05
	 */
	public void getUserId(Map<String, Object> map);
	
	List<Map<String,Object>> getCustomerToSendEmail();
}
