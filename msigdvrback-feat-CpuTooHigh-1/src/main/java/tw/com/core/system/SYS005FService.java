package tw.com.core.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.TbSysRoleMapper;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.msigDvrBack.persistence.TbSysRoleKey;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

@Service
public class SYS005FService extends BaseService{

	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysRoleMapper tbSysRoleMapper;
	
	/**
	 * 取得查詢資料總數
	 */
	public int countTotal(SYS005FQueryForm form){
		Map<String, Object> map = fillupCri(form);
		
		int size = tbSysRoleMapper.countByExample(map);
		return size;
	}
	
	public List queryList(SYS005FQueryForm form){
		Map<String, Object> map = fillupCri(form);

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
		if (StringUtils.isNotBlank(orderByClause)) {
			map.put("orderByClause",orderByClause);
		}else{
			//如order by clause為空值，則以role_id為預設排序
			map.put("orderByClause","role_id");
		}
		
		List<TbSysRole> list = tbSysRoleMapper.selectByExample(map);
		
		return list;
	}
	
	/**
	 * 預備查詢條件，提供模糊查詢
	 */
	private Map<String,Object> fillupCri(SYS005FQueryForm form) {
		String roleId = form.getRoleId();
		Map<String,Object> querymap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(roleId)){
			if(StringUtils.contains(roleId, "%")){
				querymap.put("roleIdLike", roleId);
			}else{
				querymap.put("roleId", roleId);
			}
		}
		return querymap;
	}
	
	/**
	 * 依Pkey  查詢該資料是否已存在
	 * @param CwTbSsoRoley
	 * @return int 筆數
	 */
	public int countByPkey(TbSysRoleKey key) {
		String roleId = key.getRoleId();
		Date effDate = key.getEffDate();
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("roleId", roleId);
		querymap.put("effDate", effDate);
		int size = tbSysRoleMapper.countByExample(querymap);
		
		return size;
	}
	
	/**
	 * 新增 CW_TB_SSO_ROLE
	 * @param form SYS005FModForm
	 * @return int 新增筆數
	 */
	public int insertData(SYS005FModForm form) {
		TbSysRole record = transform(form);
		int cnt = tbSysRoleMapper.insertSelective(record);
		return cnt;
	}
	
	/**
	 * 修改前查詢
	 */
	
	public TbSysRole queryByKey(TbSysRoleKey key){
		TbSysRole TbSysRole = tbSysRoleMapper.selectByPrimaryKey(key);
		if(TbSysRole == null){
			logger.info("not found CwTbSsoRole: roleId : "+key.getRoleId()+
					" , effDate : {} ",key.getEffDate());
		}
		return TbSysRole;
	}
	
	/**
	 * 計算角色主檔筆數
	 * @param form SYS005FModForm
	 * @return int 修改筆數
	 */
	public int updateData(SYS005FModForm form){
		
		String roleId = form.getRoleId();
		String effDate = form.getEffDate();
		
		TbSysRoleKey key = new TbSysRoleKey();
		key.setRoleId(roleId);
		key.setEffDate(CwDateUtils.formatDate(effDate));
		
		
		
		TbSysRole record = tbSysRoleMapper.selectByPrimaryKey(key);
		record.setEndDate(CwDateUtils.formatDate(form.getEndDate()));
		int count = tbSysRoleMapper.updateByPrimaryKey(record);
		return count;
	}	
	
	/**
	 * 依Pkey刪除角色主檔資料
	 * @param key CwTbSsoRoleKey
	 * @return int 刪除筆數
	 */
	public int deleteByKey(TbSysRoleKey key){
		int count = tbSysRoleMapper.deleteByPrimaryKey(key);
		if (count > 0) {
			logger.debug("CwTbSsoRole  Pkey => roleId : "+key.getRoleId()+
					", effDate : {} ",key.getEffDate());
		}
		return count;
	}
	
	private TbSysRole transform(SYS005FModForm form){
		TbSysRole record = new TbSysRole();
		record.setRoleId(form.getRoleId());
		record.setEffDate(CwDateUtils.formatDate(form.getEffDate()));
		record.setEndDate(CwDateUtils.formatDate(form.getEndDate()));
		return record;
	}
}
