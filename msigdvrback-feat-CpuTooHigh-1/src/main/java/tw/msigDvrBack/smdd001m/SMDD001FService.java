/**
 *  基本資料維護設定模組 - 系統維護
 *  參考資料代碼維護查詢
 *  
 *  
 *  @since: 1.0 
 *  @author: gary
 **/
package tw.msigDvrBack.smdd001m;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbLookupTypeManualMapper;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbLookupType;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;
import tw.util.FormChecker;
import tw.util.PageUtils;

@Service
public class SMDD001FService extends BaseService
{
	@Autowired
	private TbLookupTypeManualMapper tbLookupTypeManualMapper;
	
	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeMapper;

	
	@ComLogger
	private Logger logger;
	/**
	 * 
	 * <pre>
	 * Method Name : getLookupData
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param params
	 * @return List<Map<String,Object>> 
	 */
	public List<Map<String, Object>> getLookupData(SMDD001FQueryForm params) {
		int curPage = PageUtils.getPageInt(params.getPages()),
			perPage = PageUtils.getPageInt(params.getPerPageNum());		
		Page page = new Page(curPage, perPage);
		params.setPage(page);
		return this.tbLookupTypeManualMapper.getLookupTypeData(params);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : countTotal
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param params
	 * @return int 
	 */
	public int countTotal(SMDD001FQueryForm params) {
		return this.tbLookupTypeManualMapper.countTotal(params);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : getModuleCdeData
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @return List<TbLookupType> 
	 */
	public List<TbLookupCde> getModuleCdeData() {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", "MDCDE");
		return this.tbLookupCdeMapper.selectByExample(queryMap);
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : insertData
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param modForm
	 * @return int 
	 */
	public int insertLookupTypeData(SMDD001FModForm modForm) {
		TbLookupType cwTbLookupType = this.transfer(modForm);
		return this.tbLookupTypeManualMapper.insert(cwTbLookupType);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : countByPK
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param lookupType
	 * @return int 
	 */
	public int countByPK(String lookupType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lookupType", lookupType);
		return this.tbLookupTypeManualMapper.countByExample(map);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : getByPK
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param lookupType
	 * @return TbLookupType 
	 */
	public TbLookupType getByPK(String lookupType) {
		return this.tbLookupTypeManualMapper.selectByPrimaryKey(lookupType);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : countDetailDataByLookupType
	 * Description : 
	 * </pre>
	 * @since 2013/1/8
	 * @author gary 
	 *
	 * @param lookupType
	 * @return int 
	 */
	public Long countDetailDataByLookupType(String lookupType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lookupType", lookupType);
		logger.debug("into  count :{}", tbLookupCdeMapper.countByExample(map));
		return this.tbLookupCdeMapper.countByExample(map);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : getDetailDataByLookupType
	 * Description : 
	 * </pre>
	 * @since 2013/1/8
	 * @author gary 
	 *
	 * @param lookupType
	 * @return List<TbLookupCde> 
	 */
	public List<TbLookupCde> getDetailDataByLookupType(String lookupType, BaseForm detailForm, boolean isPaged) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", lookupType);
		if (StringUtils.isNotBlank(detailForm.getOrderByClause())) {
			queryMap.put("orderByClause", detailForm.getOrderByClause());
		}
		if (isPaged) {
			Page page = new Page(PageUtils.getPageInt(detailForm.getPages()), PageUtils.getPageInt(detailForm.getPerPageNum()));
			queryMap.put("perPage", detailForm.getPerPageNum());
			queryMap.put("curPage", detailForm.getPages());
			queryMap.put("begin", page.getBegin());
			queryMap.put("page", "page");
		}
		return this.tbLookupCdeMapper.selectByExample(queryMap);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : updateData
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param modForm
	 * @return int 
	 */
	public int updateData(SMDD001FModForm modForm) {
		TbLookupType cwTbLookupType = this.transfer(modForm);
		//20130326 Roy Upd
		return this.tbLookupTypeManualMapper.updateByPrimaryKey(this.transLookupType(cwTbLookupType));
	}
	
	//輔助update cw_tb_lookup_type	20130326 Roy Add
	private TbLookupType transLookupType(TbLookupType record) {
		TbLookupType updRecord = tbLookupTypeManualMapper.selectByPrimaryKey(record.getLookupType());
		String dscr = record.getDscr();
		if (dscr != null && !"".equals(dscr))
			updRecord.setDscr(dscr);
		if (record.getValueDscr() != null)
			updRecord.setValueDscr(record.getValueDscr());
		if (record.getType1Dscr() != null)
			updRecord.setType1Dscr(record.getType1Dscr());
		if (record.getType2Dscr() != null)
			updRecord.setType2Dscr(record.getType2Dscr());
		if (record.getType3Dscr() != null)
			updRecord.setType3Dscr(record.getType3Dscr());
		if (record.getSysIndic() != null)
			updRecord.setSysIndic(record.getSysIndic());
		return updRecord;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : deleteBySeq
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param lookupType
	 * @return int 
	 */
	public int deleteBySeq(String lookupType) {
		return this.tbLookupTypeManualMapper.deleteByPrimaryKey(lookupType);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : deleteDetailBySeq
	 * Description : 
	 * </pre>
	 * @since 2013/1/9
	 * @author gary 
	 *
	 * @param lookupType
	 * @return int 
	 */
	public int deleteDetailBySeq(String lookupType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lookupType", lookupType);
		return this.tbLookupCdeMapper.deleteByExample(map);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : deleteDetailDataByLookupCde
	 * Description : 
	 * </pre>
	 * @since 2013/1/9
	 * @author gary 
	 *
	 * @param lookupCde
	 * @return int 
	 */
	public int deleteDetailDataByTypeAndCde(String lookupType, String lookupCde) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lookupType", lookupType);
		map.put("lookupCde", lookupCde);
		return this.tbLookupCdeMapper.deleteByExample(map);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : countLookupCdeByTypeAndCde
	 * Description : 
	 * </pre>
	 * @since 2013/1/10
	 * @author gary 
	 *
	 * @param form
	 * @return int 
	 */
	public long countLookupCdeByTypeAndCde(SMDD001FModLookupCdeForm form) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lookupCde",form.getLookupCde() );
		map.put("lookupType", form.getLookupType());
		return this.tbLookupCdeMapper.countByExample(map);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : insertLookupCdeData
	 * Description : 
	 * </pre>
	 * @since 2013/1/9
	 * @author gary 
	 *
	 * @param form
	 * @return int 
	 */
	public int insertLookupCdeData(SMDD001FModLookupCdeForm form) {
		TbLookupCde TbLookupCde = transfer(form);
		return this.tbLookupCdeMapper.insert(TbLookupCde);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : updateLookupCdeData
	 * Description : 
	 * </pre>
	 * @since 2013/1/10
	 * @author gary 
	 *
	 * @param form
	 * @return int 
	 */
	public int updateLookupCdeData(SMDD001FModLookupCdeForm form) {
		TbLookupCde tbLookupCde = transfer(form);
		return this.tbLookupCdeMapper.updateByPrimaryKey(tbLookupCde);
	}
	/**
	 * 
	 * <pre>
	 * Method Name : updateBatch
	 * Description : 
	 * </pre>
	 * @since 2013/1/10
	 * @author gary 
	 *
	 * @param myContext
	 * @param dataList void 
	 */
	public void updateBatch(MyContext myContext, String loginUser, List<SMDD001FModLookupCdeForm> dataList) throws Exception {
		for (SMDD001FModLookupCdeForm form : dataList) {
			// form[index].colName 跳號, spring的做法是, 會new 新的java bean全部塞空值, 所以需要跳過
			if (!FormChecker.isFormContainValue(form)) {
				continue;
			}
			String now = CwDateUtils.getTodaytime();
			if (StringUtils.isNotBlank(form.getIsNew())) {
				long cnt = this.countLookupCdeByTypeAndCde(form);
				if (cnt != 0) {
					throw new Exception(
							myContext.getMessage("smdd001f.detail.lookupCde") + 
							"[" + form.getLookupType() + "." + form.getLookupCde() + "]" + 
							myContext.getMessage("violate.constraint.please.fill.in.again.message"));
				} else {
					form.setCrUser(loginUser);
					form.setCrDate(CwDateUtils.formatDateTime(now));
					form.setUserstamp(loginUser);
					form.setDatestamp(now);
					this.insertLookupCdeData(form);
				}
			} else if (StringUtils.isNotBlank(form.getDetailModifyCheck())) {
				form.setUserstamp(loginUser);
				form.setDatestamp(now);
				this.updateLookupCdeData(form);
			}
		}
	}
	/**
	 * 
	 * <pre>
	 * Method Name : transfer
	 * Description : 
	 * </pre>
	 * @since 2013/1/9
	 * @author gary 
	 *
	 * @param modForm
	 * @return TbLookupCde 
	 */
	private TbLookupCde transfer(SMDD001FModLookupCdeForm form) {
		TbLookupCde tbLookupCde = new TbLookupCde();
		tbLookupCde.setDscr(form.getDscr());
		tbLookupCde.setValue(StringUtils.isNotBlank(form.getValue()) ? new Long(form.getValue()) : null);
		tbLookupCde.setType1(form.getType1());
		tbLookupCde.setType2(form.getType2());
		tbLookupCde.setType3(form.getType3());
		tbLookupCde.setCrUser(form.getCrUser());
		tbLookupCde.setCrDate(form.getCrDate());
		tbLookupCde.setUserstamp(form.getUserstamp());
		tbLookupCde.setDatestamp(CwDateUtils.formatDateTime(form.getDatestamp()));
		tbLookupCde.setLookupType(form.getLookupType());
		tbLookupCde.setLookupCde(form.getLookupCde());
		return tbLookupCde;
	}
	/**
	 * 
	 * <pre>
	 * Method Name : transfer
	 * Description : 
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param modForm
	 * @return TbLookupType 
	 */
	private TbLookupType transfer(SMDD001FModForm modForm) {
		TbLookupType cwTbLookupType = new TbLookupType();
		cwTbLookupType.setLookupType(modForm.getLookupType());
		cwTbLookupType.setDscr(modForm.getDscr());
		cwTbLookupType.setValueDscr(modForm.getValueDscr());
		cwTbLookupType.setType1Dscr(modForm.getType1Dscr());
		cwTbLookupType.setType2Dscr(modForm.getType2Dscr());
		cwTbLookupType.setType3Dscr(modForm.getType3Dscr());
		cwTbLookupType.setCrUser(modForm.getCrUser());
		cwTbLookupType.setCrDate(CwDateUtils.formatDateTime(modForm.getCrDate()));
		cwTbLookupType.setUserstamp(modForm.getUserstamp());
		cwTbLookupType.setDatestamp(CwDateUtils.formatDateTime(modForm.getDatestamp()));
		cwTbLookupType.setSysIndic(modForm.getSysIndic());
		return cwTbLookupType;
	}
	
}
