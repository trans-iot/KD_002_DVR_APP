/**
 *  基本資料維護設定模組 - 群組維護
 *  設定 SS_USER_ROE <~> SSO_USER_PROF
 *  
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.core.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.TbSysModManualMapper;
import tw.msigDvrBack.manual.TbSysModMapper;
import tw.msigDvrBack.manual.TbSysPgMapper;
import tw.msigDvrBack.persistence.TbSysMod;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.util.PageUtils;

@Service
public class SYS002FService extends BaseService {
	
	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysModMapper tbSysModMapper;

	@Autowired
	private TbSysModManualMapper tbSysModManualMapper;
		
	
	@Autowired
	private TbSysPgMapper tbSysPgMapper;
	
	/**
	 * 取得查詢資料總數
	 */
	public int countTotal(SYS002FQueryForm form){
		Map<String, Object> map = fillupCri(form);
		
		int size = tbSysModMapper.countByExample(map);
		return size;
	}
	
	public List<TbSysMod> queryList(SYS002FQueryForm form){
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
			map.put("orderByClause", orderByClause);
		}else{
			map.put("orderByClause", "p_pg_proj_id,seq_no");
		}
		
		List<TbSysMod> list = tbSysModMapper.selectByExample(map);
		
		return list;
	}
	
	
	/**
	 * 預備查詢條件，提供模糊查詢
	 */
	private Map<String,Object> fillupCri(SYS002FQueryForm form) {
		String projPgId = form.getProjPgId();
		String pProjPgId = form.getpProjPgId();
		Map<String,Object> querymap = new HashMap<String,Object>();
		
		if(StringUtils.isNotBlank(projPgId)){
			if(StringUtils.contains(projPgId, "%")){
				querymap.put("pgProjIdLike", projPgId);
			}else{
				querymap.put("pgProjId", projPgId);
			}
		}
		if(StringUtils.isNotBlank(pProjPgId)){
			if(StringUtils.contains(pProjPgId, "%")){
				querymap.put("pPgProjIdLike", pProjPgId);
			}else{
				querymap.put("pPgProjId", pProjPgId);
			}
		}
		return querymap;
		
	}
	
	private TbSysMod transform(SYS002FModForm form){
		TbSysMod record = new TbSysMod();
		record.setPgProjId(form.getPgProjId());
		record.setPgProjDscr(form.getPgProjDscr());
		if (StringUtils.isNotBlank(form.getpPgProjId())) {
			record.setpPgProjId(form.getpPgProjId());
		}
		record.setSeqNo(Integer.parseInt(form.getSeqNo()));
		return record;
	}
	
	
	/**
	 * getModIdList
	 * 取得模組代碼
	 * @param apId 主機代碼
	 * @return List<CwTbSsoModProf>
	 */
	public List<TbSysMod> getModIdList(){
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("orderByClause", "p_pg_proj_id,pg_proj_id,seq_no");
		List<TbSysMod> list = tbSysModMapper.selectByExample(queryMap);
		return list;
	}
	
	/** chkModParent
	 *  檢查該模組是否以為第一層
	 * @param parentModId 上層模組代碼
	 * @return 回傳查詢筆數 大於0則剔錯
	 */
	public int chkModParent(String pProjPgId){
		TbSysMod tbSysMod = new TbSysMod();
		tbSysMod.setpPgProjId(pProjPgId);
		List<TbSysMod> list = tbSysModManualMapper.selectToChkMod(tbSysMod);
		if(list != null && list.size() != 0) {
			TbSysMod bean = list.get(0);
			if(StringUtils.isNotBlank(bean.getpPgProjId())){
				return list.size();
			}
		}
		return 0;
	}
	
	/**
	 * 新增 CW_TB_SSO_MOD_PROF
	 * @param form SYS002FModForm
	 * @return int 新增筆數
	 */
	public int insertData(SYS002FModForm form) {
		TbSysMod record = transform(form);
		int cnt = tbSysModMapper.insertSelective(record);
		return cnt;
	}
	
	/**
	 * 依Pkey  查詢該資料是否已存在
	 * @param CwTbSsoModProfKey
	 * @return int 筆數
	 */
	public int countByPkey(TbSysMod key) {
		String pgProjId = key.getPgProjId();
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("pgProjId", pgProjId);
		int size = tbSysModMapper.countByExample(querymap);
		return size;
	}
	
	/**
	 * 修改前查詢
	 */
	
	public TbSysMod queryByKey(String pgProjId){
		TbSysMod tbSysMod = tbSysModMapper.selectByPrimaryKey(pgProjId);
		if (tbSysMod == null) {
			logger.info("not found tbSysMod: pgProjId : "+pgProjId);
		}
		return tbSysMod;
	}
	
	/**
	 * 計算模組主檔筆數
	 * @param form SYS002FModForm
	 * @return int 修改筆數
	 */
	public int updateData(SYS002FModForm form){
		
		TbSysMod record = tbSysModMapper.selectByPrimaryKey(form.getPgProjId());
		
		record.setSeqNo(Integer.parseInt(form.getSeqNo()));
		record.setpPgProjId(form.getpPgProjId());
		record.setPgProjDscr(form.getPgProjDscr());
		int count = tbSysModMapper.updateByPrimaryKey(record);
		return count;
	}
	
	/** chkHasChild
	 *  檢查是否含有子功能
	 * @param modId 模組代碼
	 * @return 回傳查詢筆數 大於0則剔錯
	 */
	public int chkHasChild(String pgProjId){
		TbSysMod bean = new TbSysMod();
		bean.setPgProjId(pgProjId);
		List<TbSysMod> list = tbSysModManualMapper.selectToChkChildMod(bean);
		if(list!=null) return list.size();
		
		return 0;
	}
	
	/**
	 * 檢查是否有 程式關聯到此模組
	 * @param modId
	 * @return 回傳查到的筆數，大於0 不應該刪除
	 */
	public int chkHasPg(String pgProjId) {
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("pgProjId", pgProjId);
		int count = tbSysPgMapper.countByExample(queryMap);
		logger.debug("tbSysPg : "+  count);
		return count;
	}
	
	/**
	 * 依Pkey刪除模組主檔資料
	 * @param key CwTbSsoModProfKey
	 * @return int 刪除筆數
	 */
	public int deleteByKey(String pgProjId){
		int count = tbSysModMapper.deleteByPrimaryKey(pgProjId);
		if (count > 0) {
			logger.debug("CwTbSsoModProf  Pkey => modId  : "+pgProjId) ;
		}
		return count;
	}
	
	/**
	 * 主機代碼及模組代碼 唯一值驗證
	 * @param String apId 
	 * @param String modId
	 * @return int 查詢比數
	 */
	public int countByapModId (String pgProjId){
		Map<String,Object> querymap = new HashMap<String,Object>();
		querymap.put("pgProjId", pgProjId);
		List<TbSysMod> list = tbSysModMapper.selectByExample(querymap);
		return list.size();
	}
	
}
