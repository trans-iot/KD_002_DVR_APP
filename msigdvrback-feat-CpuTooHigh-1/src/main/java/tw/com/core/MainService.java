/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 12, 2012
 **/
package tw.com.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.core.menu.MenuData;
import tw.com.core.menu.MenuTree;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.SysAccountMapper;
import tw.msigDvrBack.manual.TbSqlManualMapper;
import tw.msigDvrBack.manual.TbSysModMapper;
import tw.msigDvrBack.manual.TbSysUserMapper;
import tw.msigDvrBack.manual.TbSysUserPwdHisManualMapper;
import tw.msigDvrBack.manual.TbSysUserRoleMapMapper;
import tw.msigDvrBack.persistence.TbSysUser;
import tw.msigDvrBack.persistence.TbSysUserPwdHis;
import tw.msigDvrBack.persistence.TbSysUserPwdHisExample;
import tw.msigDvrBack.persistence.TbSysUserRoleMap;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.OWASPAPI;

@Service
public class MainService extends BaseService {

	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysUserMapper cwTbSsoUserProfMapper;
	
	@Autowired
	private TbSysUserRoleMapMapper tbSysUserRoleMapMapper;
	
	@Autowired
	private PgSsoWtCheckMapper pgSsoWtChecker;
	
	@Autowired
	private TbSysUserMapper tbSysUserMapper;
	
	@Autowired
	private SysAccountMapper sysAccountMapper;
	
	@Autowired
	private TbSysModMapper tbSysModMapper;
	
	@Autowired
	private TbSysUserPwdHisManualMapper tbSysUserPwdHisManualMapper;
		
	@Autowired
	private TbSqlManualMapper tbSqlManualMapper;
	
	@Autowired
	private MyContext myContext;
	
	/**
	 * 取得 User 主檔
	 * @param userId
	 * @return
	 */
	public TbSysUser getUserProf(String userId) {
		TbSysUser record = tbSysUserMapper.selectByPrimaryKey(userId);
		logger.debug("data null:{}", record==null?true:false);
		return record;
	}
	
	/**
	 * 依據 user_id 取得 role_id list	
	 * 一個 User 有可能對應多個 Role
	 * @param userId
	 * @return
	 */
	public List<TbSysUserRoleMap> getRoleByUser(String userId) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", userId);
		List<TbSysUserRoleMap> recordList = tbSysUserRoleMapMapper.selectByExample(querymap);
		return recordList;
	}
	
	/**
	 * 依據 RoleId 取得選單
	 * 會傳 HTML 格式的樹狀選單
	 * @param sessionData
	 * @return
	 */
	public String getMenuTreeBySessionData(SessionData sessionData) {
		List<MenuData> menuDataList = sysAccountMapper.selectMenuBySessionData(sessionData);
		String html = MenuTree.getHtmlTree(menuDataList, sessionData.getSysUserId(), sessionData.getDevUrl());
		return html;
	}

	/**
	 * 依據登入的 USER_ID 取得該有的資料
	 * SYS_USER_ID
	 * role_id  (一個 User 有可能對應多個 Role, 預設是選擇第一個)
	 * etc... 
	 * @param userId
	 * @return
	 */
	public SessionData initSessionData(String userId) {
		TbSysUser record = getUserProf(userId);
		List<TbSysUserRoleMap> roleRecordList = getRoleByUser(userId);
		
		SessionData sd = new SessionData();
		sd.setSysUserId(record.getSysUserId());
		sd.setUserName(record.getUserName());
		String status = record.getStatus();
		if(StringUtils.equals("R", status) || validatePwdOver90(record)){
			sd.setResetPassword(true);
		}else{
			sd.setResetPassword(false);
		}
		
		String dbTime = sysAccountMapper.selectDbTime();
		sd.setLoginTime(dbTime);
		
		if(roleRecordList.size() > 0){
			List<String> roleList = new ArrayList<String>();
			for(TbSysUserRoleMap role:roleRecordList) {
				roleList.add(role.getRoleId());
			}
			sd.setRoleList(roleList);
			//fixed by Marks 2020/10/08 Trust Boundary Violation
			sd.setRoleId(OWASPAPI.encodeForHTML(roleList.get(0)));
		}
		
		return sd;
	}

	/**
	 * 傳入新舊密碼, userId 進行密碼變更
	 * 
	 * 使用 oldpwd + userId 檢核是否正確, 是則更新成 newpwd 否則回傳失敗
	 * - user id 找不到丟出錯誤訊息
	 * - 密碼不符丟出錯誤訊息
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public boolean executeChangePassword(String oldpwd, String newpwd, String userId) throws Exception {
//		String oldpwdDec = null;
		String oldpwdEnc = null;
		String newpwdEnc = null;
		
		TbSysUser userProf = tbSysUserMapper.selectByPrimaryKey(userId);
		if (userProf == null) {
			throw new Exception("USER_ID not found");
		}
		logger.debug("userProf:{}", ToStringBuilder.reflectionToString(userProf));
		
		oldpwdEnc = getEncPassword(oldpwd);
		logger.debug("oldpwdEnc:{}", oldpwdEnc);
		if (!StringUtils.equals(userProf.getPwd().toUpperCase(), oldpwdEnc.toUpperCase())) {
			throw new Exception( myContext.getMessage("renewPwd.old.error") );
		}
		
		newpwdEnc = getEncPassword(newpwd);
		
		userProf.setPwd(newpwdEnc);
		userProf.setStatus("A");  // n for no-need, anything else will be need reset
		userProf.setLoginFailCnt((short) 0);
		tbSysUserMapper.updateByPrimaryKey(userProf);
		return true;
	}	
	
	/**
	+	 * 取得加密後的密碼
	+	 * @param password
	+	 * @return
	+	 * @throws Exception
	+	 */
	public String getEncPassword(String password) throws Exception {
		String str = pgSsoWtChecker.getEncryptString(password);
		
		return str;
	}
	
	
	/**
	 * @method getTbSysUserPwdHis
	 * @description 依照user帳號密碼取得歷史紀錄
	 * <pre>
	 * 
	 * @param userId, password
	 * @return List<TbSysUserPwdHis>
	 * @throws Exception
	 * 
	 * @author Marks
	 * @since 2020/06/01
	 */
	public List<TbSysUserPwdHis> getTbSysUserPwdHis(String userId, String password) throws Exception {
		String pwd = getEncPassword(password);
		TbSysUserPwdHisExample example = new TbSysUserPwdHisExample();
		example.createCriteria().andSysUserIdEqualTo(userId).andPwdEqualTo(pwd);
		List<TbSysUserPwdHis> list = tbSysUserPwdHisManualMapper.selectByExample(example);
		logger.info("==================PWD_HISTORY Count =========> {}" +list.size());
		return list;
	}
	
	/**
	 * @method executeRefreshTbSysUserPwdHistory
	 * @description 每位user帳密歷史紀錄至多5筆
	 * <pre>
	 * 
	 * @param sysUserId, pwd
	 * @throws Exception
	 * 
	 * @author Marks
	 * @since 2020/06/01
	 */
	public void executeRefreshTbSysUserPwdHistory(String sysUserId, String pwd) throws Exception {
		TbSysUserPwdHis insertData = new TbSysUserPwdHis();
		insertData.setSysUserId(sysUserId);
		insertData.setPwd(getEncPassword(pwd));
		insertData.setCrDate(new Date());
		insertData.setDatestamp(new Date());
		insertData.setCrUser(sysUserId);
		tbSysUserPwdHisManualMapper.insertSelective(insertData);
		TbSysUserPwdHisExample example = new TbSysUserPwdHisExample();
		example.createCriteria().andSysUserIdEqualTo(sysUserId);
		example.setOrderByClause("cr_date ASC");
		List<TbSysUserPwdHis> list = tbSysUserPwdHisManualMapper.selectByExample(example);
		if(list.size() > 5) {
			example = new TbSysUserPwdHisExample();
			example.createCriteria().andSeqNoEqualTo(list.get(0).getSeqNo());
			int delCount = tbSysUserPwdHisManualMapper.deleteByExample(example);
		}
	}
	
	/**
	 * @method validatePwdOver90
	 * @description 驗證密碼是否超過90天，有則強制修改密碼
	 * <pre>
	 * 
	 * @param tbSysUser
	 * @return　boolean
	 * 
	 * @author Marks
	 * @since 2020/06/01
	 */
	public boolean validatePwdOver90(TbSysUser tbSysUser) {
		Long nowDate = System.currentTimeMillis()/1000L;
		Map<String, Object> crDateMap = tbSqlManualMapper.selectMaxCrdateWithTbSysUserPwdHis(tbSysUser.getSysUserId());
		Date crDate = null;
		if(crDateMap == null || (crDateMap != null && crDateMap.get("chgLastDate") == null)) {
			crDate = tbSysUser.getCrDate();
		} else {
			crDate = (Date)crDateMap.get("chgLastDate");
		}
		if(Math.abs(nowDate - crDate.getTime()/1000L) > 90 * 24 * 60 * 60) {
			return true;
		}
		return false;
	}
	
}
