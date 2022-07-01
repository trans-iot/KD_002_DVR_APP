/**
 *  基本資料維護設定模組 - 群組維護
 *  設定 SSO_MOD_PROF <~> SSO_PG_PROF <~> SSO_SYSTEM
 *  
 *  @since: 1.0 
 *  @author: alanlin
 **/
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
import tw.msigDvrBack.manual.TbSysModMapper;
import tw.msigDvrBack.manual.TbSysPgMapper;
import tw.msigDvrBack.persistence.TbSysMod;
import tw.msigDvrBack.persistence.TbSysPg;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

@Service
public class SYS004FService extends BaseService {
	
	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysPgMapper tbSysPgMapper;
	
	@Autowired
	private TbSysModMapper tbSysModMapper;
	/**
	 * 取得查詢資料總數
	 */
	public int countTotal(SYS004FQueryForm form){
		Map<String, Object> map = fillupCri(form);
		int size = tbSysPgMapper.countByExample(map);
		return size;
	}
	
	/**
	 * 預備查詢條件，提供模糊查詢
	 */
	private Map<String,Object>  fillupCri(SYS004FQueryForm form) {
		String pgProjId = form.getPgProjId();
		String pgId = form.getPgId();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(pgProjId)){
			if(StringUtils.contains(pgProjId, "%")){
				queryMap.put("pgProjIdLike", pgProjId);
			}else{
				queryMap.put("pgProjId", pgProjId);
			}
		}
		if(StringUtils.isNotBlank(pgId)){
			if(StringUtils.contains(pgId, "%")){
				queryMap.put("pgIdLike", pgId);
			}else{
				queryMap.put("pgId", pgId);
			}
		}
		return queryMap;
	}
	
	public List<TbSysPg> queryList(SYS004FQueryForm form){
		Map<String, Object> queryMap = fillupCri(form);
		
		int perPage = PageUtils.getPageInt(form.getPerPageNum());
		int curPage = PageUtils.getPageInt(form.getPages());
		if (perPage > 0) {
			Page page = new Page(curPage, perPage);
			queryMap.put("perPage", perPage);
			queryMap.put("curPage", curPage);
			queryMap.put("begin", page.getBegin());
			queryMap.put("page", "page");
		}
		
		// order by
		String orderByClause = form.getOrderByClause();
		if (StringUtils.isNotBlank(orderByClause)) {
			queryMap.put("orderByClause", orderByClause);
		}else{
			//如order by clause為空值，則以pg_id為預設排序
			queryMap.put("orderByClause", "pg_id");
		}
		
		List<TbSysPg> list = tbSysPgMapper.selectByExample(queryMap);
		logger.debug("query size:{}", list.size());
		return list;
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
	
	/**
	 * 依Pkey  查詢該資料是否已存在
	 * @param TbSysPg
	 * @return int 筆數
	 */
	public int countByPkey(TbSysPg key) {
		String pgId = key.getPgId();
		Date effDate = key.getEffDate();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("pgId", pgId);
		queryMap.put("effDate", effDate);
		int size = tbSysPgMapper.countByExample(queryMap);
		return size;
	}
	
	/**
	 * 新增 CW_TB_SSO_PG_PROF
	 * @param form SYS004FModForm
	 * @return int 新增筆數
	 */
	public int insertData(SYS004FModForm form) {
		TbSysPg record = transform(form);
		int cnt = tbSysPgMapper.insertSelective(record);
		return cnt;
	}
	
	/**
	 * 依Pkey刪除模組主檔資料
	 * @param key CwTbSsoPgProfKey
	 * @return int 刪除筆數
	 */
	public int deleteByKey(TbSysPg key){
		int count = tbSysPgMapper.deleteByPrimaryKey(key.getPgId());
		if (count > 0) {
			logger.debug("CwTbSsoPgProf  Pkey => apId : "+" pgId : "+key.getPgId()+" , effDate : {} ",key.getEffDate());
		}
		return count;
	}
	
	private TbSysPg transform(SYS004FModForm form){
		TbSysPg record = new TbSysPg();
		record.setPgProjId(form.getPgProjId());
		record.setPgId(form.getPgId());
		record.setPgName(form.getPgName());
		record.setSeqNo(Integer.parseInt(form.getSeqNo()));
		record.setEffDate(CwDateUtils.formatDate(form.getEffDate()));
		record.setEndDate(CwDateUtils.formatDate(form.getEndDate()));
		
		return record;
	}
	
	/**
	 * 修改前查詢
	 */
	
	public TbSysPg queryByKey(TbSysPg key){
		TbSysPg tbSysPg = tbSysPgMapper.selectByPrimaryKey(key.getPgId());
		if (tbSysPg == null) {
			logger.info("not found tbSysPg"+" , pgId : "+key.getPgId());
		}
		return tbSysPg;
	}
	
	/**
	 * 計算程式主檔筆數
	 * @param form SYS004FModForm
	 * @return int 修改筆數
	 */
	public int updateData(SYS004FModForm form){
		TbSysPg record = transform(form);
		int count = tbSysPgMapper.updateByPrimaryKey(this.transSysPg(record));
		return count;
	}
	
	private TbSysPg transSysPg(TbSysPg record) {
		TbSysPg key = new TbSysPg();
		key.setPgId(record.getPgId());
		key.setEffDate(record.getEffDate());
		TbSysPg updRecord = tbSysPgMapper.selectByPrimaryKey(record.getPgId());
		if (record.getPgName() != null)
			updRecord.setPgName(record.getPgName());
		//經CwDateUtils.formatDate處理若無值會是null
		updRecord.setEndDate(record.getEndDate());
		if (record.getPgProjId() != null)
			updRecord.setPgProjId(record.getPgProjId());
		if (record.getSeqNo() != null)
			updRecord.setSeqNo(record.getSeqNo());
		return updRecord;
	}
}
