/**
 *  設備資料查詢
 *  
 * @author Bob
 * @since 2018/10/22
 **/
package tw.msigDvrBack.omom001m;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.CommonService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.TbCustCarManualMapper;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbDeviceManualMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.persistence.TbCustCar;
import tw.msigDvrBack.persistence.TbCustomer;
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.AESUtilDmm;
import tw.util.CallWebApi;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

@Service
public class OMOM003FService extends BaseService {

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	private TbDeviceManualMapper tbDeviceManualMapper;
	
	@Autowired
	private TbCustCarManualMapper tbCustCarManualMapper;
	
	@Autowired
	private TbCustomerManualMapper tbCustomerManualMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MyContext myContext;

	@ComLogger
	private Logger logger;

	/**
	 * 
	 * <pre>
	 * Method Name : queryList
	 * Description : 查詢
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param form
	 * @return List<TbDevice>
	 * @throws Exception
	 */
	public List<TbDevice> queryList(OMOM003FQueryForm form) throws Exception {
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
		} 
		
		List<TbDevice> list = tbDeviceManualMapper.selectByExample(map);

		return list;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : checkPage
	 * Description : 確認頁面
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param pages,
	 *            perPage, totalCount
	 * @return String
	 * @throws Exception
	 */
	public String checkPage(String pages, String perPage, int totalCount) throws Exception {
		int ip = 1;
		int ipp = 10;
		if (StringUtils.isNotBlank(pages)) {
			ip = Integer.parseInt(pages);
		}
		if (StringUtils.isNotBlank(perPage)) {
			ipp = Integer.parseInt(perPage);
		}

		int rp = ip - 1;
		if (rp * ipp + 1 > totalCount) {
			int cp = ip == 1 ? 1 : rp;
			return String.valueOf(cp);
		} else {
			return pages;
		}
	}

	/**
	 * 
	 * <pre>
	 * Method Name : countTotal
	 * Description : 查詢總數
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param params
	 * @return Long
	 * @throws Exception
	 */
	public Long countTotal(OMOM003FQueryForm form) throws Exception {
		Map<String, Object> map = fillupCri(form);

		Long size = tbDeviceManualMapper.countByExample(map);
		return size;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : fillupCri
	 * Description : 預備查詢條件，提供模糊查詢
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param cri,
	 *            form
	 * @return void
	 * @throws Exception
	 */
	private Map<String,Object> fillupCri(OMOM003FQueryForm form) throws Exception {
		String deviceType = form.getDeviceType();
		String imei = form.getImei();
		String sn = form.getSn();
		String deviceStatus = form.getDeviceStatus();
		String targetNo = form.getTargetNo();
		String simMobile = form.getSimMobile();
		String userId = form.getUserId();
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(deviceType)) {
			map.put("deviceType", deviceType);
		}
		if(StringUtils.isNotBlank(imei)){
			if(StringUtils.contains(imei, "%")){
				map.put("imeiLike", imei);
			}else{
				map.put("imei", imei);
			}
		}
		if(StringUtils.isNotBlank(sn)){
			if(StringUtils.contains(sn, "%")){
				map.put("snLike", sn);
			}else{
				map.put("sn", sn);
			}
		}
		if (StringUtils.isNotBlank(deviceStatus)) {
			map.put("deviceStatus", deviceStatus);
		}
		if(StringUtils.isNotBlank(targetNo)) {
			if(StringUtils.contains(targetNo, "%")){
				map.put("targetNoLike", targetNo);
			}else{
				map.put("targetNo", targetNo);
			}
		}
		if(StringUtils.isNotBlank(simMobile)) {
			if(StringUtils.contains(simMobile, "%")){
				map.put("simMobileLike", simMobile);
			}else{
				map.put("simMobile", simMobile);
			}
		}
		if(StringUtils.isNotBlank(userId)) {
			if(StringUtils.contains(userId, "%")){
				map.put("userIdLike", userId);
			}else{
				map.put("userId", userId);
			}
		}
		
		return map;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : getDscrByType
	 * Description : 取得帳號狀態下拉選單
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param lkupType
	 * @return List<TbLookupCde>
	 * @throws Exception
	 */
	public List<TbLookupCde> getDscrByType(String lkupType) throws Exception {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", lkupType);
		List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(queryMap);
		return list;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : queryByKey
	 * Description : 按照pk查詢
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param detailDeviceType,
	 *            detailMacAddress, detailSn
	 * @return OMOM003FModForm
	 * @throws Exception
	 */
	public OMOM003FModForm queryByKey(String detailDeviceType, String imei, String detailSn)
			throws Exception {
		OMOM003FModForm modForm = new OMOM003FModForm();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("deviceType", detailDeviceType);
		map.put("imei", imei);
		map.put("sn", detailSn);
		// 查詢主檔
		List<TbDevice> tbDeviceList = this.tbDeviceManualMapper.selectByExample(map);
		logger.debug("TbCustomer :{}", ToStringBuilder.reflectionToString(
				"detailDeviceType" + detailDeviceType + "detailImei" + imei + "detailSn" + detailSn));

		if (tbDeviceList == null) {
			logger.info("not found TbWelcomePage({})", "detailDeviceType" + detailDeviceType + "detailImei"
					+ imei + "detailSn" + detailSn);
		} else {
			if(tbDeviceList.size() > 0) {
				modForm = (this.transMod(tbDeviceList.get(0)));
			}
		}
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", Constants.DETYP);
		List<TbLookupCde> listDetyp = tbLookupCdeManualMapper.selectByExample(queryMap);
		Map<String,Object> queryMap2 = new HashMap<String,Object>();
		queryMap2.put("lookupType", Constants.DESTA);
		List<TbLookupCde> listDesta = tbLookupCdeManualMapper.selectByExample(queryMap2);
		for (TbLookupCde tbLookupCde : listDetyp) {
			if (tbLookupCde.getLookupCde().equals(modForm.getDeviceType())) {
				modForm.setDeviceTypeDscr(tbLookupCde.getDscr());
			}
		}
		for (TbLookupCde tbLookupCde : listDesta) {
			if (tbLookupCde.getLookupCde().equals(modForm.getDeviceStatus())) {
				modForm.setDeviceStatusDscr(tbLookupCde.getDscr());
			}
		}
		
		TbCustomer tbCustomer = tbCustomerManualMapper.selectByPrimaryKey(modForm.getUserId());
		if(Objects.nonNull(tbCustomer)) {
			transTbCustomer(tbCustomer, modForm);
		}
		return modForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : transMod
	 * Description : TbDevice轉換OMOM003FModForm
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param obj
	 * @return OMOM003FModForm
	 * @throws Exception
	 */
	private OMOM003FModForm transMod(TbDevice obj) throws Exception {

		OMOM003FModForm form = new OMOM003FModForm();
		form.setDeviceType(obj.getDeviceType());
		form.setImei(obj.getImei());
		form.setSn(obj.getSn());
		form.setUserId(obj.getUserId());
		form.setDeviceStatus(obj.getDeviceStatus());
		form.setBindDate(obj.getBindDate());
		form.setDeviceType(obj.getDeviceType());
		form.setDevEffDate(obj.getDevEffDate());
		form.setDevEndDate(obj.getDevEndDate());
		form.setSimEffDate(obj.getSimEffDate());
		form.setSimEndDate(obj.getSimEndDate());
		form.setUploadDate(obj.getUploadDate());
		form.setTargetNo(obj.getTargetNo());
		form.setSimMobile(obj.getSimMobile());
		form.setServiceType(commonService.getLookupCdeDscr(Constants.LOOKUP_SERVICETY, obj.getServiceType()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		return form;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : removeDevice
	 * Description : 執行解除綁定
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom003fRomForm
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> executeRemoveDevice(OMOM003FRomForm omom003fRomForm) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", Constants.AESKEY);
		queryMap.put("lookupCde", Constants.TIOTDVR);
		String desKey = new String();
		String iv = new String();
		//fixed by Marks 2020/09/09
		TbLookupCde cde = null;
		List<TbLookupCde> lookupList = tbLookupCdeManualMapper.selectByExample(queryMap);
		if(lookupList != null && lookupList.size() > 0) {
			cde = lookupList.get(0);
			desKey = cde.getType1();
			iv = cde.getType2();
		}
		JSONObject jsonArray = new JSONObject(omom003fRomForm);
		
		logger.info("removeDevice: omom003fRomForm", ToStringBuilder.reflectionToString(omom003fRomForm));
		String secret_data = AESUtilDmm.encrypt(jsonArray.toString(), desKey, iv);
		JSONObject content = new JSONObject();
		content.put("secret_data", secret_data);
		Map<String,Object> queryMap2 = new HashMap<String,Object>();
		queryMap2.put("lookupType", Constants.WEBURL);
		queryMap2.put("lookupCde", Constants.TIOTDVR);
		List<TbLookupCde> lookupListDomain = tbLookupCdeManualMapper.selectByExample(queryMap2);
		String url = lookupListDomain.get(0).getType1() + "/TiotDvrWeb/json/removeDevice.html";
		logger.info("removeDevice: CallWebApi url", url);

		String result = CallWebApi.callHttps(content.toString(), url, "application/json", "POST", 0, "UTF-8");
		logger.info("removeDevice: CallWebApi result", result);
		if (result.equals("error")) {
			map.put("errCde", "01");
			map.put("errMsg", myContext.getMessage("timeout.error"));
			return map;
		}
		map.put("errCde", (String) new JSONObject(result).get("errCde"));
		map.put("errMsg", (String) new JSONObject(result).get("errMsg"));
		return map;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : updateTbDevice
	 * Description : 更新解除綁定後tb_device資料
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom003fRomForm
	 * @return Integer
	 * @throws Exception
	 */
	public Integer updateTbDevice(OMOM003FRomForm omom003FRomForm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deviceType", omom003FRomForm.getDevice_type());
		map.put("imei", omom003FRomForm.getImei());
		map.put("sn", omom003FRomForm.getSn());
		// 查詢主檔
		List<TbDevice> vwCustUbiApplyList = this.tbDeviceManualMapper.selectByExample(map);
		TbDevice tbDevice = vwCustUbiApplyList.get(0);
		tbDevice.setDeviceStatus("REMOVE");
		tbDevice.setUserId(null);
		tbDevice.setBindDate(null);
		return tbDeviceManualMapper.updateByPrimaryKey(tbDevice);
	}

	public Integer updateTbCustCar(OMOM003FRomForm omom003fRomForm) {
		TbCustCar tbCustCar = tbCustCarManualMapper.selectByPrimaryKey(omom003fRomForm.getUser_id());
		tbCustCar.setEffMileageDate(new Date());
		tbCustCar.setMileageStatus("RESET");
		tbCustCar.setResetTime(new Date());
		tbCustCar.setUserId(omom003fRomForm.getUser_id());
		return tbCustCarManualMapper.updateByPrimaryKey(tbCustCar);
	}

	
	private void transTbCustomer(TbCustomer tbCustomer, OMOM003FModForm modForm) {
		modForm.setTbCustomer(tbCustomer);
		modForm.setDobDscr(CwDateUtils.transferDatetime(tbCustomer.getDob()));
		modForm.setRegisterTimeDscr(CwDateUtils.transferDatetime(tbCustomer.getRegisterTime()));
		modForm.setAgreeTimeDscr(CwDateUtils.transferDatetime(tbCustomer.getAgreeTime()));
		modForm.setAppLoginTimeDscr(CwDateUtils.transferDatetime(tbCustomer.getAppLoginTime()));
	}
}
