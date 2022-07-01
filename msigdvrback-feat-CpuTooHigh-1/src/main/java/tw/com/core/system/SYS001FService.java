/**
 *  基本資料維護設定模組 - 使用者維護
 *  SSO_USER_PROF
 *  
 *  @since: 1.0 
 *  @author: OlsonLiu
 **/
package tw.com.core.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.exception.CustomValidationException;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbSysRoleManualMapper;
import tw.msigDvrBack.manual.TbSysUserMapper;
import tw.msigDvrBack.manual.TbSysUserRoleMapMapper;
import tw.msigDvrBack.manual.VwSysUserMapper;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.msigDvrBack.persistence.TbSysUser;
import tw.msigDvrBack.persistence.TbSysUserRoleMap;
import tw.msigDvrBack.persistence.TbSysUserRoleMapKey;
import tw.msigDvrBack.persistence.VwSysUser;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

@Service
public class SYS001FService extends BaseService {

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;

	@Autowired
	private VwSysUserMapper vwSysUserMapper;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeMapper;

	@Autowired
	private TbSysRoleManualMapper tbSysRoleMapper;

	@Autowired
	private TbSysUserMapper tbSysUserMapper;

	@Autowired
	private TbSysUserRoleMapMapper tbSysUserRoleMapMapper;

	/**
	 * 取得查詢資料總數
	 */
	public int countTotal(SYS001FQueryForm form) {
		Map<String, Object> querymap = fillupCri(form);

		long size = vwSysUserMapper.countByExample(querymap);
		return (int) size;
	}

	/**
	 * 預備查詢條件，提供模糊查詢
	 */
	private Map<String,Object> fillupCri(SYS001FQueryForm form) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		String sysUserId = form.getSysuserId();
		String sysUserName = form.getSysUserName();
		String sysDept = form.getSysDept();
		if (StringUtils.isNotBlank(sysUserId)) {
			if (StringUtils.contains(sysUserId, "%")) {
				querymap.put("sysUserIdLike", sysUserId);
			} else {
				querymap.put("sysUserId", sysUserId);
			}
		}
		if(StringUtils.isNotBlank(sysUserName)) {
			querymap.put("sysUserNameLike", sysUserName);
		}
		if(StringUtils.isNotBlank(sysDept)) {
			querymap.put("sysDept", sysDept);
		}
		return querymap;
	}

	/**
	 * <pre>
	 * Method Name : queryList
	 * Description : 取得查詢清單
	 * </pre>
	 * 
	 * @since 2013/02/22 修改
	 * @author Finn
	 * @param SYS001FQueryForm
	 *            form
	 * @return List<CwTbSsoUserProf>
	 */
	public List<VwSysUser> queryList(SYS001FQueryForm form) {
		Map<String, Object> map = fillupCri(form);
		// 分頁
		int perPage = PageUtils.getPageInt(form.getPerPageNum());
		int curPage = PageUtils.getPageInt(form.getPages());

		if (perPage > 0) {
			Page page = new Page(curPage, perPage);
			map.put("perPage", perPage);
			map.put("curPage", curPage);
			map.put("begin", page.getBegin());
			map.put("page", "page");
		}

		// order by
		String orderByClause = form.getOrderByClause();
		logger.debug("orderByClause----" + orderByClause);
		if (StringUtils.isNotBlank(orderByClause)) {
			map.put("orderByClause", orderByClause);
		} else {
			map.put("orderByClause", "sys_user_id");
		}

		List<VwSysUser> list = vwSysUserMapper.selectByExample(map);

		return list;
	}

	// 取得帳號狀態
	public List<TbLookupCde> getDscrByType(String lkupType) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", lkupType);
		List<TbLookupCde> list = tbLookupCdeMapper.selectByExample(queryMap);
		return list;
	}

	/**
	 * 依使用者帳號 查詢該資料是否已存在
	 * 
	 * @param userId
	 *            使用者帳號
	 * @return int 筆數
	 */
	public int countByPkey(String sysUserId) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", sysUserId);
		long size = tbSysUserMapper.countByExample(querymap);
		logger.debug("userId:{}, count:{}", sysUserId, size);
		return (int) size;
	}

	/**
	 * <pre>
	 * Method Name : insertData Description : 新增(新增主檔及明細資料入口)
	 * 
	 * <pre>
	 * 
	 * @author Olson
	 * @since 2012/11/22
	 * @param form
	 *            SYS001FMainForm
	 * @return int
	 * @throws CustomValidationException
	 */
	public int insertData(SYS001FMainForm mainForm) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(myContext.getMessage("insert.fail.message")).append("：\n");

		// 取得主檔資料(TB_SYS_USER)
		SYS001FModForm modForm = mainForm.getSys001fModForm();
		// 新增時，ResetPawdIndic為 "Y"
		modForm.setResetPawdIndic("R");
		TbSysUser record = transform(modForm);
		String userId = modForm.getUserId();
		int ukCnt = this.countByPkey(userId);
		if (ukCnt > 0) {
			sb.append(myContext.getMessage("master.insert.fail")).append("\n")
					.append(myContext.getMessage("sys001f.userId")).append("+")
					.append(myContext.getMessage("eff.date.label"))
					.append(myContext.getMessage("violate.constraint.please.fill.in.again.message"));
			throw new Exception(sb.toString());
		}
		int count = 0;
		try {
			// 新增時，狀態為 重設密碼
			record.setStatus("R");
			count = tbSysUserMapper.insertSelective(record);
			int cnt = count;
			logger.debug("insert Tb_Sys_User count:{}", cnt);
		} catch (Exception e) {
			logger.error("insertData error:{}", e.getMessage());
		}

		// 新增明細資料CW_TB_SSO_USER_ROLE_MAP
		// 若頁面該tab為空則sys001fModUserRoleMapDetailForm可能為null
		List<SYS001FModUserRoleMapForm> userRoleMapList = mainForm.getSys001fModUserRoleMapDetailForm() == null
				? new ArrayList<SYS001FModUserRoleMapForm>()
				: mainForm.getSys001fModUserRoleMapDetailForm().getUserRoleMapList();
		int userRoleMapCount = 0;

		for (int i = 0; i < userRoleMapList.size(); i++) {
			TbSysUserRoleMap tbSysUserRoleMap = transform(userRoleMapList.get(i));
			tbSysUserRoleMap.setSysUserId(userId);
			;

			// 新增前select是否已有重複資料
			int insertCount = this.countByUnique(tbSysUserRoleMap);
			if (insertCount > 0) {
				sb.append(myContext.getMessage("detail.insert.fail")).append("\n").append("：")
						.append(myContext.getMessage("sys001f.userId")).append("=")
						.append(tbSysUserRoleMap.getSysUserId()).append(";")
						.append(myContext.getMessage("sys001f.role.id")).append("=")
						.append(tbSysUserRoleMap.getRoleId())
						.append(myContext.getMessage("violate.constraint.please.fill.in.again.message"));
				this.throwException(sb.toString());
			}
			// 新增
			userRoleMapCount += this.insertUserRoleMapData(tbSysUserRoleMap);
		}
		return count;
	}

	/**
	 * 依Pkey刪除使用者資料
	 * 
	 * @param String
	 *            userId
	 * @return int 刪除筆數
	 */
	public int deleteByKey(String userId) {
		int count = tbSysUserMapper.deleteByPrimaryKey(userId);
		if (count > 0) {
			logger.debug("detele CwTbSsoUserProf Pkey => userId : {}, count:{}", userId, count);
		}
		return count;
	}

	/**
	 * 2013/02/23 Finn 修改 修改transMaster 依PK查詢使用者資料
	 * 
	 * @param userId
	 * @return
	 */
	public SYS001FMainForm queryByKey(String userId) {
		SYS001FMainForm mainForm = new SYS001FMainForm();

		// 查詢主檔
		TbSysUser tbSysUser = this.queryMasterByKey(userId);
		mainForm.setSys001fModForm(this.transMaster(tbSysUser));

		//// 查詢Detail資料(TB_SYS_USER_ROLE_MAP)
		List<TbSysUserRoleMap> userRoleMapList = queryUserRoleMapByMaster(userId);
		List<SYS001FModUserRoleMapForm> userRoleMapPageList = new ArrayList<SYS001FModUserRoleMapForm>();
		SYS001FModUserRoleMapDetailForm sys001fModUserRoleMapDetailForm = new SYS001FModUserRoleMapDetailForm();
		for (int i = 0; i < userRoleMapList.size(); i++) {
			userRoleMapPageList.add(this.transDtlUserRoleMap(userRoleMapList.get(i)));
		}
		sys001fModUserRoleMapDetailForm.setUserRoleMapList(userRoleMapPageList);
		mainForm.setSys001fModUserRoleMapDetailForm(sys001fModUserRoleMapDetailForm);

		return mainForm;
	}

	public TbSysUser queryMasterByKey(String userId) {
		TbSysUser tbSysUser = tbSysUserMapper.selectByPrimaryKey(userId);

		logger.debug("cwTbSsoUserProf :{}", ToStringBuilder.reflectionToString(tbSysUser));
		if (tbSysUser == null) {
			logger.info("not found tbSysUser({})", userId);
		}
		return tbSysUser;
	}

	private List<TbSysUserRoleMap> queryUserRoleMapByMaster(String userId) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", userId);
		return tbSysUserRoleMapMapper.selectByExample(querymap);
	}

	private int insertUserRoleMapData(TbSysUserRoleMap record) {

		int count = tbSysUserRoleMapMapper.insertSelective(record);
		
		int cnt = count;

		return count;
	}

	private TbSysUserRoleMap transform(SYS001FModUserRoleMapForm form) {
		TbSysUserRoleMap record = new TbSysUserRoleMap();
		record.setRoleId(form.getRoleId());
		record.setSysUserId(form.getsysUserId());
		record.setCrDate(CwDateUtils.formatDate(form.getCrDate()));
		record.setCrUser(form.getCrUser());
		record.setUserstamp(form.getUserstamp());
		record.setDatestamp(form.getDatestamp());
		return record;
	}

	private TbSysUser transform(SYS001FModForm form) {
		TbSysUser record = new TbSysUser();
		record.setSysUserId(form.getUserId());
		record.setUserName(form.getUserName());
		record.setEmail(form.getEmail());
		record.setPwd(pgSsoWtCheckMapper.getEncryptString(form.getUserId()));
		record.setStatus(form.getResetPawdIndic());
		record.setLoginFailCnt(form.getLoginFailCnt());
		record.setStatus(form.getStatus());
		record.setDeptNo(form.getDeptNo());
		record.setCrDate(CwDateUtils.formatDate(form.getCrDate()));
		return record;
	}

	/**
	 * 修改資料
	 * 
	 * @param form
	 *            SYS001FModForm
	 * @return int 修改筆數
	 */
	public int updateData(SYS001FMainForm mainForm) throws Exception {
		String errorMessage = myContext.getMessage("update.fail.message") + ":";
		String[] excludeFields = new String[] { "crUser", "crDate", "datestamp", "userstamp", "passWord", "modPawdDate",
				"loginFailCnt", "status" };
		int updateCount = 0;

		SYS001FModForm modForm = mainForm.getSys001fModForm();
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", modForm.getUserId());
		// 查詢主檔是否存在
		List<TbSysUser> sysUserList = tbSysUserMapper.selectByExample(querymap);
		if (sysUserList.size() != 1) {
			logger.debug("master data not found. tbSysUser : {} " + sysUserList);
			errorMessage += myContext.getMessage("master.notfound");
			this.throwException(errorMessage);
		}

		// 比對頁面資料與原始資料是否相同 否則更新
		TbSysUser record = this.transform(modForm);
		if (0 != CompareToBuilder.reflectionCompare(sysUserList.get(0), record, excludeFields)) {
//			logger.debug("update master data. record : {} " + record);

			// 修改主檔
			updateCount = tbSysUserMapper.updateByPrimaryKey(this.transSysUser(record));
			// 主檔修改失敗
			if (updateCount != 1) {
				logger.debug("update master data failed!!!. updateStatus : {} " + updateCount);
				errorMessage += myContext.getMessage("master.update.fail");
				this.throwException(errorMessage);
			}
		}

		String userId = mainForm.getSys001fModForm().getUserId();
		try {
			// 執行CW_TB_SSO_USER_ROLE_MAP更新操作
			updateCount = this.updateUserRoleMapData(mainForm.getSys001fModUserRoleMapDetailForm(), userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.debug("method updateData complate.");
		return updateCount;
	}

	private TbSysUser transSysUser(TbSysUser record) {
		TbSysUser updRecord = tbSysUserMapper.selectByPrimaryKey(record.getSysUserId());
		if (record.getDeptNo() != null)
			updRecord.setDeptNo(record.getDeptNo());
		if (record.getUserName() != null)
			updRecord.setUserName(record.getUserName());
		if (record.getEmail() != null)
			updRecord.setEmail(record.getEmail());
		if (record.getLoginFailCnt() != null)
			updRecord.setLoginFailCnt(record.getLoginFailCnt());
		if (record.getStatus() != null)
			updRecord.setStatus(record.getStatus());
		return updRecord;
	}

	/**
	 * <pre>
	 * Method Name : updateUserRoleMapData Description :
	 * 修改明細資料USER_ROLE_MAP(CW_TB_SSO_USER_ROLE_MAP)
	 * 
	 * <pre>
	 * 
	 * @author Olson
	 * @since 2012/11/23
	 * @param pageDataList
	 *            List&lt;SYS001FModUserMapForm&gt;
	 * @param userId
	 *            String(CW_TB_SSO_USER_ROLE_MAP.USER_ID)
	 * @return int 1:修改成功
	 * @throws CustomValidationException
	 */
	private int updateUserRoleMapData(SYS001FModUserRoleMapDetailForm detailForm, String userId)
			throws CustomValidationException {
		StringBuffer errMsg = new StringBuffer();
		errMsg.append(myContext.getMessage("detail.update.fail")).append("：\n");

		// 比對排除資料
		String[] excludeFields = new String[] { "userstamp", "datestamp", "crUser", "crDate", "serialVersionUID" };

		// 取得原始資料
		List<TbSysUserRoleMap> dbDataList = this.queryUserRoleMapByMaster(userId);
		// 頁面回傳資料(若頁面Detail row全部刪除則會為null，必須cover掉此狀況>>給予空list)
		List<SYS001FModUserRoleMapForm> pageDataList = detailForm == null ? new ArrayList<SYS001FModUserRoleMapForm>()
				: detailForm.getUserRoleMapList();
		List<TbSysUserRoleMap> updateList = new ArrayList<TbSysUserRoleMap>();
		List<TbSysUserRoleMap> deleteList = new ArrayList<TbSysUserRoleMap>();
		List<TbSysUserRoleMap> insertList = new ArrayList<TbSysUserRoleMap>();

		/*
		 * 分析Detail資料 分析該筆資料動作(判斷條件為userId、roleId): (ps.頁面會傳入所有值皆為null的form)
		 * DB內無此筆，UI傳入有>>>新增 DB內有此筆，UI傳入有且資料相同>>>不動作 DB內有此筆，UI傳入有且資料不同>>>修改
		 * DB內有此筆，UI傳入無>>>刪除
		 */
		for (SYS001FModUserRoleMapForm form : pageDataList) {
			Boolean isAddFlag = true;
			// 以Page傳入資料為base
			// 如果DB沒有此筆apId 和sysUserId則為新增
			String roleId = form.getRoleId();
			// 為避免跳號問題 如roleId 為NULL 則跳過不做處理
			if (roleId == null) {
				continue;
			}
			// 進入db資料的迴圈比對是否有roleId
			for (TbSysUserRoleMap dbData : dbDataList) {
				String tempRoleId = dbData.getRoleId();
				if (tempRoleId.equals(roleId)) {
					isAddFlag = false;
				}
			}
			if (isAddFlag == true) {
				insertList.add(this.transform(form));
			} else {
				String pageDataFlag = "N";
				TbSysUserRoleMap pageData = this.transform(form);
				for (int i = 0; i < dbDataList.size(); i++) {
					TbSysUserRoleMap record = dbDataList.get(i);
					// 比對是否為相同資料
					if (record.getRoleId().equals(form.getRoleId())) {
						// 比對內容，內容不同:進行更新(update)/內容相同:不做任何處理(doNothing)
						if (0 != CompareToBuilder.reflectionCompare(record, pageData, excludeFields)) {
							updateList.add(pageData);
						}
						pageDataFlag = "Y";
						// 移除已對應資料for過濾需刪除資料
						dbDataList.remove(i);
					}
				}
				// 頁面上資料不存在於資料庫中表示資料已被異動，須重新查詢再進行修改
				if ("N".equals(pageDataFlag)) {
					errMsg.append(myContext.getMessage("detail.notfound")).append("\n").append("：")
							.append(myContext.getMessage("sys001f.userId")).append("=").append(userId).append("：")
							.append(myContext.getMessage("sys001f.role.id")).append("=").append(roleId)
							.append(myContext.getMessage("data.not.exist"));
					this.throwException(errMsg.toString());
				}
			}

		} // DB中資料與PAGE對應不到則表示需刪除
		for (TbSysUserRoleMap record : dbDataList) {
			deleteList.add(record);
		}
		// 刪除Detail資料
		try {
			this.deleteUserRoleMapDtl(deleteList);
		} catch (Exception e) {
			// 加上功能訊息
			errMsg.append("\n").append(e.getMessage());
			this.throwException(errMsg.toString());
		}

		// 新增detail 資料
		int insertCount = 0;
		for (TbSysUserRoleMap record : insertList) {
			record.setSysUserId(userId);

			// 新增前select是否已有重複資料
			int count = this.countByUnique(record);
			if (count != 0) {
				errMsg.append(myContext.getMessage("detail.insert.fail")).append("\n").append("：")
						.append(myContext.getMessage("sys001f.userId")).append("=").append(record.getSysUserId())
						.append(";").append(myContext.getMessage("sys001f.role.id")).append("=")
						.append(record.getRoleId())
						.append(myContext.getMessage("violate.constraint.please.fill.in.again.message"));
				this.throwException(errMsg.toString());
			}
			// 新增
			insertCount += this.insertUserRoleMapData(record);
		}
		logger.debug("insert CW_TB_SSO_USER_ROLE_MAP total count : {}", insertCount);
		logger.debug("method updateSsoUserMap complete.");

		return 1;
	}

	public int delUserRoleMapDetail(TbSysUserRoleMap record) {
		TbSysUserRoleMap key = new TbSysUserRoleMap();
		key.setRoleId(record.getRoleId());
		key.setSysUserId(record.getSysUserId());
		int count = tbSysUserRoleMapMapper.deleteByPrimaryKey(key);
		logger.debug("delete CW_TB_SSO_USER_ROLE_MAP count:{}", count);
		return count;

	}

	/**
	 * <pre>
	 * Method Name : countByUnique Description : 查詢筆數 by UKey (tbSysUserRoleMap)
	 * 
	 * <pre>
	 * 
	 * @author Frank
	 * @since 2015/8/20
	 * @param record
	 *            tbSysUserRoleMap
	 * @return int
	 */
	public int countByUnique(TbSysUserRoleMap record) {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", record.getSysUserId());
		querymap.put("roleId", record.getRoleId());
		return (int) tbSysUserRoleMapMapper.countByExample(querymap);
	}

	/**
	 * Method Name : getDdlList Description : 取得DDL選單，顯示為 Cde - Dscr
	 * 
	 * @since 2013/02/22
	 * @author Finn
	 * @param lookupType
	 * @return Map<String,String>
	 */
	private Map<String, String> getDdlList(String lookupType) {
		Map<String, String> retMap = new LinkedHashMap<String, String>();
		for (TbLookupCde cwTbLookupCde : findCwTbLookupCde(lookupType)) {
			retMap.put(cwTbLookupCde.getLookupCde(), cwTbLookupCde.getLookupCde() + "-" + cwTbLookupCde.getDscr());
		}
		logger.debug("{}: {}", lookupType, retMap);
		return retMap;
	}

	/**
	 * Method Name : findCwTbLookupCde Description : 傳入lookupType取得list表
	 * 
	 * @since 2013/02/22
	 * @author Finn
	 * @param lookupType
	 * @return List<CwTbLookupCde>
	 */
	private List<TbLookupCde> findCwTbLookupCde(String lookupType) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", lookupType);
		return tbLookupCdeMapper.selectByExample(queryMap);
	}

	/**
	 * Method Name : findDeptDscrList Description : 取得部門名稱
	 * 
	 * @since 2013/02/22
	 * @author Finn
	 * @return Map<String,String>
	 */
	public Map<String, String> findDeptDscrList() {
		String lookupType = Constants.DEPTARTMENT;
		return this.getDdlList(lookupType);
	}

	/**
	 * getRoleList 取得角色下拉選單
	 * 
	 * @return
	 */
	public List<TbSysRole> getRoleList() {
		Map<String,Object> querymap = new HashMap<String,Object>();
		List<TbSysRole> list = tbSysRoleMapper.selectByExample(querymap);
		return list;
	}

	/**
	 * transMaster for queryByKey
	 * 
	 * @param obj
	 * @return
	 */
	public SYS001FModForm transMaster(TbSysUser obj) {
		SYS001FModForm form = new SYS001FModForm();
		form.setUserId(obj.getSysUserId());
		form.setUserName(obj.getUserName());
		form.setEmail(obj.getEmail());
		form.setStatus(obj.getStatus());
		// deptId
		form.setDeptNo(obj.getDeptNo());
		// loginFailCnt
		form.setLoginFailCnt(obj.getLoginFailCnt());
		form.setCrUser(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDate(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		return form;
	}

	public SYS001FModUserRoleMapForm transDtlUserRoleMap(TbSysUserRoleMap obj) {
		SYS001FModUserRoleMapForm form = new SYS001FModUserRoleMapForm();
		form.setsysUserId(obj.getSysUserId());
		form.setRoleId(obj.getRoleId());
		form.setCrUser(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDate(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());

		return form;
	}

	/**
	 * This method will thorw CustomValidationException and have message with
	 * incoming msg string.
	 * 
	 * @param msg
	 *            String
	 */
	private void throwException(String msg) throws CustomValidationException {
		CustomValidationException ce = new CustomValidationException(msg);
		throw ce;
	}

	/**
	 * <pre>
	 * Method Name : deleteUserRoleMapDtl Description : 刪除明細檔
	 * (CW_TB_SSO_USER_ROLE_MAP)
	 * 
	 * <pre>
	 * 
	 * @author Olson
	 * @since 2012/11/23
	 * @param cwTbSsoUserRoleMapList
	 *            List&lt;CwTbSsoUserRoleMap&gt;
	 * @return int
	 * @throws CustomValidationException
	 */
	public int deleteUserRoleMapDtl(List<TbSysUserRoleMap> tbSysUserRoleMapList) throws CustomValidationException {
		StringBuffer sb = new StringBuffer();
		sb.append(myContext.getMessage("detail.delete.fail")).append("\n");

		logger.debug("delete tbSysUserRoleMap {}", tbSysUserRoleMapList);

		int deleteCount = 0;
		for (TbSysUserRoleMap record : tbSysUserRoleMapList) {
			String userId = record.getSysUserId();
			String roleId = record.getRoleId();
			TbSysUserRoleMapKey key = new TbSysUserRoleMapKey();
			key.setSysUserId(userId);
			key.setRoleId(roleId);
			int count = tbSysUserRoleMapMapper.deleteByPrimaryKey(key);
			logger.debug("delete TB_SYS_USER_ROLE_MAP count:{}", count);
			if (count != 1) {
				sb.append(myContext.getMessage("sys001f.userId")).append("=").append(userId)
						.append(myContext.getMessage("sys001f.role.id")).append("=").append(roleId)
						.append(myContext.getMessage("data.not.exist"));
				this.throwException(sb.toString());
			}
			deleteCount += count;
		}
		logger.debug("delete TB_SYS_USER_ROLE_MAP total count : {}", deleteCount);
		return 1;
	}

	public int updateUserProf(String userId) {
		userId = userId.trim();
		TbSysUser tbSysUser = tbSysUserMapper.selectByPrimaryKey(userId);
		tbSysUser.setStatus("R");
		tbSysUser.setPwd(pgSsoWtCheckMapper.getEncryptString(userId));
		tbSysUser.setLoginFailCnt((short) 0);

		// 修改主檔
		int updateCount = tbSysUserMapper.updateByPrimaryKey(tbSysUser);
		return updateCount;
	}

	/**
	 * chkChildWhenDel 刪除前檢查該資料底下是否有detail
	 * 
	 * @param userId
	 * @return
	 */
	public boolean chkChildWhenDel(String userId) {

		boolean delFlag = true;

		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("sysUserId", userId);
		List<TbSysUserRoleMap> userRoleMaplist = tbSysUserRoleMapMapper.selectByExample(querymap);
		if (userRoleMaplist.size() > 0)
			delFlag = false;

		return delFlag;
	}
	
	public int executeSuspendAccount(String userId) {
		userId = userId.trim();
		TbSysUser tbSysUser = tbSysUserMapper.selectByPrimaryKey(userId);
		tbSysUser.setStatus("S");
		
		// 修改主檔
		int updateCount = tbSysUserMapper.updateByPrimaryKey(tbSysUser);
		return updateCount;
	}

}
