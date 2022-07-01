/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 17, 2012
 **/
package tw.msigDvrBack.manual;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tw.com.core.SessionData;
import tw.com.core.menu.MenuData;

public interface SysAccountMapper {
	
	/**
	 * 依據SessionData資料取得 Menu的清單
	 * 
	 * @param sessionData
	 * @return
	 */
	public List<MenuData> selectMenuBySessionData(SessionData sessionData);
	
	
	/**
	 * 不分使用者取得所有的選單清單
	 * @return
	 */
	public List<MenuData> selectAllMenuTree();
	
	/**
	 * 依據 RoleId 取得目前設定清單
	 * @return
	 */
	public List<MenuData> selectListByRoleId(@Param("roleId") String roleId);
	
	/**
	 * 依據 UserId 取得目前平台清單
	 * @return
	 */
	public List<Map<String, Object>> selectRolePlatformMap(String userId);
	
	/**
	 * 取得 DB 得系統時間
	 * 回傳格式  yyyy/MM/dd hh:mm:ss
	 * @return
	 */
	public String selectDbTime();
	
	/**
	 * 取得 DB 得系統時間
	 * 回傳格式  yyyy/MM/dd
	 * @return
	 */
	public String selectDbDate();
	
	/**
	 * 取得 DB 得系統月份
	 * 回傳格式  MM
	 * @return
	 */
	public String selectDbMonth();
}
