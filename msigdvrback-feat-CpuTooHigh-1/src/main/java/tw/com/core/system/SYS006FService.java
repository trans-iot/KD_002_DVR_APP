package tw.com.core.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.core.menu.MenuData;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.manual.SysAccountMapper;
import tw.msigDvrBack.manual.TbSysModMapper;
import tw.msigDvrBack.manual.TbSysRoleManualMapper;
import tw.msigDvrBack.manual.TbSysRolePgMapMapper;
import tw.msigDvrBack.persistence.TbSysMod;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.msigDvrBack.persistence.TbSysRolePgMap;
import tw.spring.ComLogger;

@Service
public class SYS006FService extends BaseService{

	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysRoleManualMapper tbSysRoleManualMapper;
	
	@Autowired
	private SysAccountMapper sysAccountMapper;
	
	@Autowired
	private TbSysModMapper tbSysModMapper;
	
	@Autowired TbSysRolePgMapMapper tbSysRolePgMapMapper;
	
	/**
	 * 取得 SSO Role 清單, 並按照 orderByClause 排序
	 * @param form
	 * @return
	 */
	public List<TbSysRole> querySsoRoleList(String orderByClause){
		Map<String,Object> querymap = new HashMap<String,Object>();
		// order by
		if (StringUtils.isNotBlank(orderByClause)) {
			querymap.put("orderByClause", orderByClause);
		}else{
			//如order by clause為空值，則以role_id為預設排序
			querymap.put("orderByClause", "role_id asc");
		}
		List<TbSysRole> list = tbSysRoleManualMapper.selectByExample(querymap);
		
		return list;
	}
	

	/**
	 * 依據 RoleId 取得 sysId_pmId_modId_pgId 組成的 字串資料
	 * 
	 * @param roleId
	 * @return string
	 */
	public String queryPgListByRoleId(String roleId) {
		roleId = ESAPI.encoder().decodeForHTML(roleId);
		List<MenuData> list = sysAccountMapper.selectListByRoleId(roleId);
		String s = "%s"+CwConstants.SPLIT_CHAR+"%s"+CwConstants.SPLIT_CHAR+"%s"+CwConstants.SPLIT_CHAR+"%s";
		String[] arr = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
			MenuData menuData = list.get(i);
			String sysId = menuData.getApId();
			String pmId = menuData.getPmId();
			String modId = menuData.getModId();
			String pgId = menuData.getPgId();
			arr[i] = String.format(s, sysId, pmId, modId, pgId);
		}
		String rs = StringUtils.join(arr, CwConstants.ARRAY_CHAR);
		//fixed by Marks 2020/09/09
		return ESAPI.encoder().encodeForHTML(rs);
	}
	
	public List<MenuData> queryAllList() {
		return sysAccountMapper.selectAllMenuTree();
	}
	
	
	/**
	 * 依據id清單作處理, 
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	public boolean saveIds(String roleId, String ids) throws Exception {
		logger.debug("ids:[{}]", ids);
		//depends on SYS006F doSave(), var param = "roleId="+roleId+"$ids="+ids.join(";");
		String[] idArr = ids.split(CwConstants.ARRAY_CHAR);
		
		TbSysRolePgMap[] pgs = new TbSysRolePgMap[idArr.length];
		for (int i=0; i<idArr.length; i++) {
			String s = idArr[i];
			String[] fields = s.split(CwConstants.SPLIT_CHAR);
			if (fields.length<5) {
				continue;
			}
			String pgId = fields[4];
			pgs[i] = new TbSysRolePgMap();
			pgs[i].setRoleId(roleId);
			pgs[i].setPgId(pgId);
			pgs[i].setCrDate(new Date());
		}
		boolean flag = false;
		try {
			deleteByRoleId(roleId);
			insertArray(pgs);
			flag = true;
		}
		catch (Exception e) {
			logger.error("update fail:{}", e.getMessage());
			throw e;
		}
		return flag;
	}
	
	private void insertArray(TbSysRolePgMap[] pgs) throws Exception {
		for (int i=0; i<pgs.length; i++) {
			if (pgs[i] != null && StringUtils.isNotBlank(pgs[i].getRoleId())) {
				tbSysRolePgMapMapper.insertSelective(pgs[i]);
			}
		}
	}
	
	private void deleteByRoleId(String roleId) throws Exception {
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("roleId", roleId);
		tbSysRolePgMapMapper.deleteByExample(querymap);
	}
	
	public Map<String, TbSysMod> getModMap(){
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("orderByClause", "pg_proj_id");
		List<TbSysMod> modList = tbSysModMapper.selectByExample(querymap);
		Map<String, TbSysMod> modMap = new HashMap<String, TbSysMod>();
		for (TbSysMod mod : modList){
			modMap.put(mod.getPgProjId(), mod);
		}
		return modMap;
	}
}
