/**
 *  參考會員資料維護
 *  
 * @author Bob
 * @since 2018/10/22
 **/
package tw.msigDvrBack.smdd001m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import tw.com.exception.CustomValidationException;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.CommonService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.TbCustOpenidMapper;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbDeviceManualMapper;
import tw.msigDvrBack.manual.TbLookupTypeManualMapper;
import tw.msigDvrBack.manual.TbSqlManualMapper;
import tw.msigDvrBack.persistence.TbCustOpenid;
import tw.msigDvrBack.persistence.TbCustomer;
import tw.msigDvrBack.persistence.TbCustomerExample;
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbDeviceExample;
import tw.mybatis.Page;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CallWebApi;
import tw.util.CwDateUtils;
import tw.util.EmailUtil;
import tw.util.PageUtils;

@Service
public class SMDD003FService extends BaseService {
	
	@Autowired
	private TbLookupTypeManualMapper tbLookupTypeManualMapper;
	
	@Autowired
	private TbCustomerManualMapper  tbCustomerManualMapper;
	
	@Autowired
	private TbCustOpenidMapper tbcustopenidmapper;
	
	@Autowired
	private TbSqlManualMapper tbSqlManualMapper;
	
	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;
	
	@Autowired
	private TbDeviceManualMapper tbDeviceManualMapper;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@ComLogger
	private Logger logger;
	
	@Autowired
	private MyContext myContext;
	
	/**
	 * 
	 * <pre>
	 * Method Name : queryList
	 * Description : 
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param params
	 * @return List<Map<String,Object>> 
	 * @throws ParseException 
	 */
	public List<TbCustomer> queryList(SMDD003FQueryForm form) throws ParseException {
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
		} else {
//			example.setOrderByClause("woNbr");
		}

		List<TbCustomer> list = tbCustomerManualMapper.selectByExample(map);
		
		return list;
	}
	
	
	public String checkPage(String pages, String perPage, int totalCount) {
		int ip = 1;
		int ipp = 10;
		if (StringUtils.isNotBlank(pages)) {
			ip = Integer.parseInt(pages);
		}
		if (StringUtils.isNotBlank(perPage)) {
			ipp = Integer.parseInt(perPage);
		}
		
		int rp = ip -1;
		if ( rp * ipp + 1 > totalCount) {
			int cp = ip == 1 ? 1 : rp;
			return String.valueOf(cp);
		}
		else {
			return pages;
		}
	}
	/**
	 * 
	 * <pre>
	 * Method Name : countTotal
	 * Description : 
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param params
	 * @return int 
	 * @throws ParseException 
	 */
	public Long countTotal(SMDD003FQueryForm form) throws ParseException {
		Map<String, Object> map = fillupCri(form);

		Long size = tbCustomerManualMapper.countByExample(map);
		return size;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : countByPK
	 * Description : 
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
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
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param  userid
	 * @return TbCustomer 
	 */
	public TbCustomer getByPK(String userid) {
		return this.tbCustomerManualMapper.selectByPrimaryKey(userid);
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : getByPK_openid
	 * Description : 
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param  userid
	 * @return  List<TbCustOpenid> 
	 */
	public  List<TbCustOpenid> getByPK_openid(String userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		return this.tbcustopenidmapper.selectByExample(map);
	}
	
	
	public int deleteByKey(String userId) throws Exception {
		TbDevice tbDevice = getTbDeviceByUserId(userId);
		SMDD003FModForm modForm = new SMDD003FModForm();
		if(Objects.nonNull(tbDevice)) {
			modForm.setSn(tbDevice.getSn());
			modForm.setUserId(tbDevice.getUserId());
			modForm.setDeviceType(tbDevice.getDeviceType());
			modForm.setUserstamp(tbDevice.getUserstamp());
		}
		execuetUnBindDevice(modForm);
		return tbCustomerManualMapper.deleteByPrimaryKey(userId);
	}
	
	
	/**
	 * 
	 * <pre>
	 * Method Name : fillupCri
	 * Description : 預備查詢條件，提供模糊查詢
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param form
	 * @throws ParseException 
	 */
	private Map<String,Object> fillupCri(SMDD003FQueryForm form) throws ParseException {
		String userId = form.getUser_id();
		String userName = form.getUser_name();
		String email = form.getEmail();
		String cuid = form.getCuid();
		String custStatus = form.getCust_status();
		String registerTimeBeginString = form.getRegisterTimeBegin();
		String registerTimeEndString = form.getRegisterTimeEnd();
		SimpleDateFormat sdf = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
		Map<String ,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(registerTimeBeginString) && StringUtils.isNotBlank(registerTimeEndString)) {
			Date registerTimeBegin = sdf.parse(registerTimeBeginString + Constants.FIRST_SECOND);
			Date registerTimeEnd = sdf.parse(registerTimeEndString + Constants.LAST_SECOND);
			map.put("registerTimeBegin", registerTimeBegin);
			map.put("registerTimeEnd", registerTimeEnd);
			map.put("registerTimeBetween", "registerTimeBetween");
		} else if (StringUtils.isNotBlank(registerTimeBeginString)) {
			Date registerTimeBegin = sdf.parse(registerTimeBeginString + Constants.FIRST_SECOND);
			map.put("registerTimeBegin", registerTimeBegin);
			map.put("registerTimeGreaterThanOrEqualTo", "registerTimeGreaterThanOrEqualTo");
		} else if (StringUtils.isNotBlank(registerTimeEndString)) {
			Date registerTimeEnd = sdf.parse(registerTimeEndString + Constants.LAST_SECOND);
			map.put("registerTimeEnd", registerTimeEnd);
			map.put("registerTimeLessThanOrEqualTo", "registerTimeLessThanOrEqualTo");
		}
		if (StringUtils.isNotBlank(userId)) {
			if (StringUtils.contains(userId, "%")) {
				map.put("userIdLike", userId);
			} else {
				map.put("userId", userId);
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			if (StringUtils.contains(userName, "%")) {
				map.put("userNameLike", userName);
			} else {
				map.put("userName", userName);
			}
		}
		if (StringUtils.isNotBlank(email)) {
			if (StringUtils.contains(email, "%")) {
				map.put("emailLike", email);
			} else {
				map.put("email", email);
			}
		}
		if (StringUtils.isNotBlank(cuid)) {
			if (StringUtils.contains(cuid, "%")) {
				map.put("cuidlLike", cuid);
			} else {
				map.put("cuid", cuid);
			}
		}
		if (StringUtils.isNotBlank(custStatus)) {
			map.put("custStatus", custStatus);
		}
		return map;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : queryByKey
	 * Description : 依主鍵查詢
	 * </pre>
	 * @author Bob
	 * @since 2020/06/08
	 *
	 * @param updatetuserId
	 * @return SMDD003FModForm 
	 */
	public SMDD003FModForm queryByKey(String  updatetuserId) {
		SMDD003FModForm modForm = new SMDD003FModForm();

		// 查詢主檔
		TbCustomer TbCustomer = this.getByPK(updatetuserId);
		logger.debug("TbCustomer :{}", ToStringBuilder.reflectionToString(updatetuserId));
		
		if (TbCustomer == null) {
			logger.info("not found TbCustomer({})", updatetuserId);
		} else {
			modForm = (this.transMod(TbCustomer));
		}
		
		TbDevice tbDevice = getTbDeviceByUserId(updatetuserId);
		if(tbDevice != null) {
			modForm.setTbDevice(tbDevice);
			transformModForm(modForm, tbDevice);
		}

		return modForm;
		
	}
	/**
	 * 
	 * <pre>
	 * Method Name : queryByKey_openid
	 * Description : 依openid查詢
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param updatetuserId
	 * @return List<TbCustOpenid>
	 */
	public List<TbCustOpenid> queryByKey_openid(String updatetuserId) {
		 
		// 查詢   TB_CUST_OPENID
		List<TbCustOpenid> TbCustOpenid_list = this.getByPK_openid(updatetuserId);
		logger.debug("TbCustomer :{}", ToStringBuilder.reflectionToString(updatetuserId));
		
		if (TbCustOpenid_list == null) {
			logger.info("not found TbCustomer({})", updatetuserId);
		}

		return TbCustOpenid_list;
		
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : getTbDeviceByUserId
	 * Description : 依userId查詢TbDevice
	 * </pre>
	 * @author Marks
	 * @since 2020/06/08
	 *
	 * @param userId
	 * @return TbDevice
	 */
	public TbDevice getTbDeviceByUserId(String userId) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("userId", userId);
		List<TbDevice> resultList = tbDeviceManualMapper.selectByExample(searchMap);
		if(resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : getTbDeviceByUserId
	 * Description : 依sn查詢TbDevice
	 * </pre>
	 * @author Marks
	 * @since 2020/06/22
	 *
	 * @param sn
	 * @return TbDevice
	 */
	public TbDevice getTbDeviceBySn(String sn, String deviceStatus) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("sn", sn);
		searchMap.put("deviceType", Constants.DEVICE_TYPE_DVR);
		searchMap.put("deviceStatusNotEquals", deviceStatus);
		List<TbDevice> resultList = tbDeviceManualMapper.selectByExample(searchMap);
		if(resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : transMod
	 * Description : 表單轉換
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param TbCustomer
	 * @return SMDD003FModForm
	 */
	private SMDD003FModForm transMod(TbCustomer obj) {
		
		SMDD003FModForm form = new SMDD003FModForm();
		form.setUserId(obj.getUserId());
		form.setUserName(obj.getUserName());
		form.setEngName(obj.getEngName());
		form.setNickname(obj.getNickname());
		form.setEmail(obj.getEmail());
		form.setMobilePhone(obj.getMobilePhone());
		form.setSex(obj.getSex());
		form.setDob(CwDateUtils.transferDate(obj.getDob()));
		form.setContactPhone(obj.getContactPhone());
		form.setCustStatus(obj.getCustStatus());
		form.setAddr(obj.getAddr());
		form.setPicUrl(obj.getPicUrl());
		form.setCuid(obj.getCuid());
		form.setAgreeTimeDscr(CwDateUtils.transferDatetime(obj.getAgreeTime()));
		form.setAccCtcName(obj.getAccCtcName());
		form.setAccCtcMobile(obj.getAccCtcMobile());
		form.setPsdStatus(obj.getPwdStatus());
		
		form.setAppLoginTime(CwDateUtils.transferDatetime(obj.getAppLoginTime()));
		form.setCarNo(obj.getCarNo());
		form.setRemarks(obj.getRemarks());
		form.setUnuseDvrCnt(Objects.nonNull(obj.getUnuseDvrCnt()) ? String.valueOf(obj.getUnuseDvrCnt()) : "");
		form.setRegisterTime(CwDateUtils.transferDatetime(obj.getRegisterTime()));
		form.setCrUser(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDatetime(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());

		return form;
	}
	
	/**
	 * <pre>
	 * Method Name : transformModForm
	 * Description : TbDevice轉換為SMDD003FModForm
	 * </pre>
	 * @param modForm
	 * @author Marks
	 * @since 2020/06/08
	 */
	private void transformModForm(SMDD003FModForm modForm, TbDevice obj) {
		modForm.setSn(obj.getSn());
		modForm.setDeviceStatus(commonService.getLookupCdeDscr(Constants.DESTA, obj.getDeviceStatus()));
		modForm.setBindDate(CwDateUtils.transferDate(obj.getBindDate()));
		modForm.setUploadDate(CwDateUtils.transferDatetime(obj.getUploadDate()));
		modForm.setDevEffDate(CwDateUtils.transferDate(obj.getDevEffDate()));
		modForm.setDevEndDate(CwDateUtils.transferDate(obj.getDevEndDate()));
		modForm.setSimEffDate(CwDateUtils.transferDate(obj.getSimEffDate()));
		modForm.setSimEndDate(CwDateUtils.transferDate(obj.getSimEndDate()));
		modForm.setSimMobile(obj.getSimMobile());
		modForm.setServiceType(obj.getServiceType());
	}
 
	/**
	 * 
	 * <pre>
	 * Method Name : transformTbCustomer
	 * Description : SMDD003FModForm轉換為TbCustomer
	 * </pre>
	 * @param modForm
	 * @author Marks
	 * @since 2020/06/05
	 *
	 * @param TbCustomer
	 */
	private TbCustomer transformTbCustomer(SMDD003FModForm form) {
		TbCustomer tbcustomer = new TbCustomer();
		tbcustomer.setUserId(form.getUserId());
		tbcustomer.setUserName(form.getUserName());
		tbcustomer.setEngName(form.getEngName());
		tbcustomer.setNickname(form.getNickname());
		tbcustomer.setEmail(form.getEmail().toLowerCase());
		tbcustomer.setMobilePhone(form.getMobilePhone());
		tbcustomer.setSex(form.getSex());
		tbcustomer.setDob(CwDateUtils.formatDate(form.getDob()));
		tbcustomer.setContactPhone(form.getContactPhone());
		tbcustomer.setCustStatus(form.getCustStatus());
		tbcustomer.setAddr(form.getAddr());
		tbcustomer.setPicUrl(form.getPicUrl());
		tbcustomer.setCuid(form.getCuid());
		tbcustomer.setAgreeTime(form.getAgreeTime());
		tbcustomer.setAccCtcName(form.getAccCtcName());
		tbcustomer.setAccCtcMobile(form.getAccCtcMobile());
		tbcustomer.setPwdStatus(form.getPsdStatus());
		tbcustomer.setPicUrl(form.getPicUrl());
		tbcustomer.setSn(form.getSn());
		
		tbcustomer.setAppLoginTime(CwDateUtils.formatDateTime(form.getAppLoginTime()));
		tbcustomer.setCarNo(form.getCarNo().toUpperCase());
		tbcustomer.setRemarks(form.getRemarks());
		if(StringUtils.isNotBlank(form.getUnuseDvrCnt())) {
			tbcustomer.setUnuseDvrCnt(Short.valueOf(form.getUnuseDvrCnt()));
		} 
		tbcustomer.setRegisterTime(CwDateUtils.formatDateTime(form.getRegisterTime()));
		tbcustomer.setAgreeTime(CwDateUtils.formatDateTime(form.getAgreeTimeDscr()));
		
		tbcustomer.setPassword(form.getPsd());
		tbcustomer.setCrUser(form.getCrUser());
		tbcustomer.setCrDate(CwDateUtils.formatDate(form.getCrDate()));
		tbcustomer.setUserstamp(form.getUserstamp());
		tbcustomer.setDatestamp(form.getDatestamp());
		return tbcustomer;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : transformTbCustomerForInsert
	 * Description : SMDD003FModForm轉換為TbCustomer
	 * </pre>
	 * @param modForm
	 * @author Marks
	 * @since 2020/06/05
	 *
	 * @param TbCustomer
	 */
	private TbCustomer transformTbCustomerForInsert(SMDD003FModForm modForm) {
		TbCustomer tbCustomer = new TbCustomer();
		BeanUtils.copyProperties(modForm, tbCustomer, SMDD003FModForm.COPYIGNORE);
		if(StringUtils.isNotBlank(modForm.getUnuseDvrCnt())) {
			tbCustomer.setUnuseDvrCnt(Short.valueOf(modForm.getUnuseDvrCnt()));
		}
		tbCustomer.setEmail(modForm.getEmail().toLowerCase());
		if(StringUtils.isNotBlank(modForm.getCarNo())) {
			tbCustomer.setCarNo(modForm.getCarNo().toUpperCase());
		}
		tbCustomer.setDob(CwDateUtils.formatDate(modForm.getDob()));
		tbCustomer.setPassword(pgSsoWtCheckMapper.getEncryptString(modForm.getPsd()));
		tbCustomer.setPwdStatus(Constants.PWD_STATUS_FIRST);
		tbCustomer.setRegisterTime(new Date());
		return tbCustomer;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : transformTbDevice
	 * Description : SMDD003FModForm轉換為TbDevice
	 * </pre>
	 * @param modForm
	 * @author Marks
	 * @since 2020/06/05
	 *
	 * @param TbDevice
	 */
	private TbDevice transformTbDevice(SMDD003FModForm modForm) {
		TbDevice tbDevice = getTbDeviceBySn(modForm.getSn(),"");
		tbDevice.setUserId(modForm.getUserId());
		tbDevice.setBindDate(new Date());
		tbDevice.setDeviceStatus(Constants.DEVICE_STATUS_BINDING);
		tbDevice.setTargetNo("");
		tbDevice.setDevEffDate(CwDateUtils.formatDate(modForm.getDevEffDate()));
		tbDevice.setDevEndDate(CwDateUtils.formatDate(modForm.getDevEndDate()));
		tbDevice.setServiceType(modForm.getServiceType());
		tbDevice.setSimMobile(modForm.getSimMobile());
		tbDevice.setSimEffDate(CwDateUtils.formatDate(modForm.getSimEffDate()));
		tbDevice.setSimEndDate(CwDateUtils.formatDate(modForm.getSimEndDate()));
		return tbDevice;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : processCallBindUserDevice
	 * Description : 呼叫設備綁定
	 * </pre>
	 * @param modForm
	 * @author Marks
	 * @since 2020/06/05
	 *
	 * @param String
	 */
	private String processCallBindUserDevice(SMDD003FModForm modForm) throws Exception {
		String errMsg = "";
		JSONObject content = new JSONObject();
		content.put("user_id", modForm.getUserId());
		content.put("sn", modForm.getSn());
		content.put("trx_time", CwDateUtils.getTodaytime());
		content.put("target_no", "");
		content.put("dev_eff_date", modForm.getDevEffDate());
		content.put("dev_end_date", modForm.getDevEndDate());
		content.put("service_type", modForm.getServiceType());
		content.put("sim_mobile", modForm.getSimMobile());
		content.put("sim_eff_date", modForm.getSimEffDate());
		content.put("sim_end_date", modForm.getSimEndDate());
		JSONObject secretObj = new JSONObject();
		String secretData = commonService.encryptSecretData(content.toString());
		secretObj.put("secret_data", secretData);
		logger.info("=========CALL : bindUserDevice  content ==========>" + content.toString());
		
		String url =  commonService.getApiUrl() + "/TiotDvrWeb/json/bindUserDevice.html";
		String result = CallWebApi.callHttps(secretObj.toString(), url, "application/json", "POST", 0, "UTF-8");
		
		//API 呼叫失敗(如:timeout) 或 @errCde != '00'
		if(result.equals("error")) {
			errMsg = myContext.getMessage("smdd003f.message.bindDeviceFail") + myContext.getMessage("timeout.error");
			return errMsg;
		}
		JSONObject resultObj = new JSONObject(result);
		//API回傳errCde=00
		if("00".equals(resultObj.getString("errCde"))) {
			TbDeviceExample tbDeviceExample = new TbDeviceExample();
			tbDeviceExample.createCriteria().andSnEqualTo(modForm.getSn());
			TbDevice tbDevice = transformTbDevice(modForm);
			tbDeviceManualMapper.updateByPrimaryKey(tbDevice);
		} else {
			errMsg = myContext.getMessage("smdd003f.message.bindDeviceFail") + resultObj.getString("errMsg");
		}
		return errMsg;
	}
	
	/**
	 * 使用@car_no 混淆處理：(target_no設定為空 目前用不到)
	 *	以左邊數來第3及4碼進行遮碼，例: 
	 *	ABC-1234  AB**1234
	 */
	private String blockString(String str) {
		String returnStr = str;
		if(StringUtils.isNotBlank(str)) {
			if(str.length() >= 3) {
				String firstString = str.substring(0, 2);
				String appendString = str.substring(3,str.length());
				returnStr = firstString + "*" + appendString;
			}
			if(str.length() >= 4) {
				String firstString = str.substring(0, 2);
				String appendString = str.substring(4,str.length());
				returnStr = firstString + "**" + appendString;
			}
		}
		return returnStr;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : insertData
	 * Description : 新增資料
	 * </pre>
	 * @author Marks
	 * @since 2020/06/05
	 *
	 * @param AjaxForm
	 */
	public AjaxForm insertData(SMDD003FModForm modForm, AjaxForm ajaxForm) throws Exception {
		//檢核會員EMAIL ： 
		Map<String, Object> searchEmailMap = new HashMap<String, Object>();
		searchEmailMap.put("email", modForm.getEmail());
		Long count = tbCustomerManualMapper.countByExample(searchEmailMap);
		if(count > 0) {
			throw new CustomValidationException(myContext.getMessage("smdd003f.message.emailFail"));
		}
		
		//產生8碼亂數密碼
		modForm.setPsd(tbSqlManualMapper.getRandomPwd());
		
		//取得會員編號
		Map<String, Object> userIdSearchMap = new HashMap<String, Object>();
		userIdSearchMap.put("pi_cde_type", "USERID");
		userIdSearchMap.put("pi_cde", "MTTW");
		tbSqlManualMapper.getUserId(userIdSearchMap);
		if(Constants.STATUS_00.equals(userIdSearchMap.get("po_err_cde"))) {
			modForm.setUserId(userIdSearchMap.get("po_seq").toString());
		} else {
			throw new CustomValidationException(userIdSearchMap.get("po_err_msg").toString());
		}
		
		//開啟手動控制commit / rollback
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		//新增會員
		TbCustomer tbCustomer = transformTbCustomerForInsert(modForm);
		tbCustomerManualMapper.insertSelective(tbCustomer);

		if(StringUtils.isNotBlank(modForm.getSn())) {
			//呼叫設備綁定
			String errMsg = processCallBindUserDevice(modForm);
			
			if(StringUtils.isNotBlank(errMsg)) {
				transactionManager.rollback(status);
				throw new CustomValidationException(errMsg);
			}
		}

		transactionManager.commit(status);	
		
		sendAccountEmail(modForm.getEmail(), modForm.getPsd());
		
		ajaxForm.setResult(AjaxResultEnum.OK);
		ajaxForm.setMessage(myContext.getMessage("insert.success.message"));
		return ajaxForm;
	}
	
	
	/**
	 * 
	 * <pre>
	 * Method Name : updateData
	 * Description : 更新資料
	 * </pre>
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param TbCustomer
	 * @return SMDD003FModForm
	 */
	public int updateData(SMDD003FModForm modForm) throws Exception {
		String errorMessage = myContext.getMessage("update.fail.message") + ":";
		String[] excludeFields = new String[] { "crUser", "crDate", "datestamp", "userstamp" };
		int updateCount = 0;
 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", modForm.getUserId());
		// 查詢主檔是否存在
		List<TbCustomer> customer_lsit = tbCustomerManualMapper.selectByExample(map);

		if (customer_lsit.size() != 1) {
			logger.debug("master data not found. carVehicles : {} " + customer_lsit);
			errorMessage += myContext.getMessage("master.notfound");
			throw new Exception(errorMessage);
		} else {
			modForm.setPsd(customer_lsit.get(0).getPassword());
			modForm.setCrDate(CwDateUtils.transferDate(customer_lsit.get(0).getCrDate()));
			modForm.setCrUser(customer_lsit.get(0).getCrUser());
		}

		// 比對頁面資料與原始資料是否相同 否則更新
		TbCustomer record = this.transformTbCustomer(modForm);
		if (0 != CompareToBuilder.reflectionCompare(customer_lsit.get(0), record, excludeFields)) {

			//開啟手動控制commit / rollback
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionManager.getTransaction(def);
			
			// 修改主檔
			updateCount = tbCustomerManualMapper.updateByPrimaryKey(record);
			// 主檔修改失敗
			if (updateCount != 1) {
				errorMessage += myContext.getMessage("master.update.fail");
				throw new Exception(errorMessage);
			}
			
			if(StringUtils.isNotBlank(modForm.getSn())) {
				String errMsg = processCallBindUserDevice(modForm);
				if(StringUtils.isNotBlank(errMsg)) {
					transactionManager.rollback(status);
					throw new Exception(errMsg);
				}
			}
			transactionManager.commit(status);
		}
		
		logger.debug("method updateData complate.");
		return updateCount;
	}
	
	/**
	 * <pre>
	 * Method Name : execuetUnBindDevice
	 * Description : 設備解除綁定
	 * </pre>
	 * @param modForm
	 * @return AjaxForm
	 * @author Marks
	 * @since 2020/06/08
	 */
	public AjaxForm execuetUnBindDevice(SMDD003FModForm modForm) throws Exception {
		AjaxForm ajaxForm = new AjaxForm();
		ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
		JSONObject content = new JSONObject();
		content.put("device_type", modForm.getDeviceType());
		content.put("user_id", modForm.getUserId());
		content.put("sn", modForm.getSn());
		content.put("trx_time", CwDateUtils.getTodaytime());
		content.put("userstamp", modForm.getUserstamp());
		
		JSONObject secretObj = new JSONObject();
		String secretData = commonService.encryptSecretData(content.toString());
		secretObj.put("secret_data", secretData);
		
		String url = commonService.getApiUrl() + "/TiotDvrWeb/json/removeDevice.html";
		String result = CallWebApi.callHttps(secretObj.toString(), url, "application/json", "POST", 0, "UTF-8");
		
		if(result.equals("error")) {
			ajaxForm.setMessage(myContext.getMessage("smdd003f.message.bindDeviceFail") + myContext.getMessage("timeout.error"));
			return ajaxForm;
		}
		JSONObject resultObj = new JSONObject(result);
		if(resultObj.has("errCde") && "00".equals(resultObj.getString("errCde"))) {
			TbDeviceExample example = new TbDeviceExample();
			example.createCriteria().andSnEqualTo(modForm.getSn());
			TbDevice tbDevice = getTbDeviceBySn(modForm.getSn(), "");
			tbDevice.setDeviceStatus(Constants.DEVICE_STATUS_REMOVE);
			tbDevice.setUserId(null);
			tbDevice.setBindDate(null);
			tbDeviceManualMapper.updateByPrimaryKey(tbDevice);
			
			ajaxForm.setResult(AjaxResultEnum.OK);
			ajaxForm.setMessage(myContext.getMessage("smdd003f.message.bindDeviceSuccess") + resultObj.getString("errMsg"));
		} else {
			ajaxForm.setMessage(myContext.getMessage("smdd003f.message.bindDeviceFail") + resultObj.getString("errMsg"));
		}
		return ajaxForm;
	}
	
	/**
	 * <pre>
	 * Method Name : initMilageView
	 * Description : 里程數查詢頁面初始
	 * </pre>
	 * @param userId
	 * @param milageQueryForm
	 * @author Marks
	 * @since 2020/06/08
	 */
	public void initMilageView(String userId,SMDD003FMilageQueryForm milageQueryForm) {
		TbCustomer tbCustomer = getByPK(userId);
		if(Objects.nonNull(tbCustomer)) {
			milageQueryForm.setEmail(tbCustomer.getEmail());
			milageQueryForm.setUserName(tbCustomer.getUserName());
			milageQueryForm.setUserId(tbCustomer.getUserId());
			milageQueryForm.setCarNo(tbCustomer.getCarNo());
		}
	}
	
	/**
	 * <pre>
	 * Method Name : queryMilage
	 * Description : 里程數查詢
	 * </pre>
	 * @param milageQueryForm
	 * @return String
	 * @author Marks
	 * @since 2020/06/08
	 */
	public String queryMilage(SMDD003FMilageQueryForm milageQueryForm) throws Exception {
		JSONObject content = new JSONObject();
		content.put("user_id", milageQueryForm.getUserId());
		content.put("start_date", milageQueryForm.getDateStart());
		content.put("end_date", milageQueryForm.getDateEnd());
		content.put("trx_time", CwDateUtils.getTodaytime());
		JSONObject secretObj = new JSONObject();
		String secretData = commonService.encryptSecretData(content.toString());
		secretObj.put("secret_data", secretData);
		
		String url = commonService.getApiUrl() + "/TiotDvrWeb/json/getUserMileage.html";
		String result = CallWebApi.callHttps(secretObj.toString(), url, "application/json", "POST", 0, "UTF-8");

		JSONObject resultObj = new JSONObject(result);
		if(resultObj.has("errCde") && "00".equals(resultObj.getString("errCde"))) {
			String totalMileage = resultObj.optString("total_mileage");
			return totalMileage;
		}
		return "";
	}
	
	/**
	 * <pre>
	 * Method Name : queryDeviceBySn
	 * Description : 依sn查詢設備
	 * </pre>
	 * @param sn
	 * @return SMDD003FModForm
	 * @author Marks
	 * @since 2020/06/08
	 */
	public SMDD003FModForm queryDeviceBySn(String sn) {
		SMDD003FModForm modForm = new SMDD003FModForm();
		if(StringUtils.isNotBlank(sn)) {
			sn = StringEscapeUtils.escapeHtml(sn);
			TbDevice tbDevice = getTbDeviceBySn(sn, Constants.DEVICE_STATUS_BINDING);
			if(Objects.nonNull(tbDevice)) {
				modForm.setSimMobile(tbDevice.getSimMobile());
				modForm.setServiceType(tbDevice.getServiceType());
				modForm.setDevEffDate(CwDateUtils.transferDate(tbDevice.getDevEffDate()));
				modForm.setDevEndDate(CwDateUtils.transferDate(tbDevice.getDevEndDate()));
				modForm.setSimEffDate(CwDateUtils.transferDate(tbDevice.getSimEffDate()));
				modForm.setSimEndDate(CwDateUtils.transferDate(tbDevice.getSimEndDate()));
				modForm.setDeviceStatus(ESAPI.encoder().decodeForHTML(commonService.getLookupCdeDscr(Constants.DESTA, tbDevice.getDeviceStatus())));
			}
		}
		return modForm;
	}
	
	/**
	 * <pre>
	 * Method Name : countTbCustomer
	 * Description : 查詢tbCustomer筆數
	 * </pre>
	 * @param smdd003fModForm
	 * @return Long
	 * @author Marks
	 * @since 2020/07/02
	 */
	public Long countTbCustomer(SMDD003FModForm smdd003fModForm) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("email", smdd003fModForm.getEmail());
		return tbCustomerManualMapper.countByExample(searchMap);
	}
	
	/**
	 * <pre>
	 * Method Name : executeUpdateEmail
	 * Description : 修改email
	 * </pre>
	 * @param smdd003fModForm
	 * @author Marks
	 * @since 2020/07/02
	 */
	public void executeUpdateEmail(SMDD003FModForm smdd003fModForm) {
		TbCustomer tbCustomer = new TbCustomer();
		tbCustomer.setEmail(smdd003fModForm.getEmail());
		TbCustomerExample example = new TbCustomerExample();
		example.createCriteria().andUserIdEqualTo(smdd003fModForm.getUserId());
		tbCustomerManualMapper.updateByExampleSelective(tbCustomer, example);
	}
	
	/**
	 * <pre>
	 * Method Name : executeResetPwd
	 * Description : 重設密碼並重寄信件
	 * </pre>
	 * @param smdd003fModForm
	 * @author Marks
	 * @since 2020/07/02
	 */
	public void executeResetPwd(SMDD003FModForm smdd003fModForm) {
		String reset = tbSqlManualMapper.getRandomPwd();
		TbCustomer tbCustomer = new TbCustomer();
		tbCustomer.setPassword(pgSsoWtCheckMapper.getEncryptString(reset));
		TbCustomerExample example = new TbCustomerExample();
		example.createCriteria().andUserIdEqualTo(smdd003fModForm.getUserId());
		tbCustomerManualMapper.updateByExampleSelective(tbCustomer, example);
		
		sendAccountEmail(smdd003fModForm.getEmail(), reset);
	}
	
	
	private void sendAccountEmail(String email, String pwd) {
		Map<String, String> mailContent = emailUtil.getCreateAccountMailContent(email, pwd);
		
		Map<String, String> mailSettingMp = emailUtil.checkActAdnPwd();
		//發送帳號密碼通知信件
		emailUtil.sendCheckMessage(mailSettingMp, mailContent);
	}
	
	/**
	 * 檢查設備SN碼是否存在
	 * @return
	 */
	public long queryTbDevice(String sn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sn", sn);
		long count = tbDeviceManualMapper.countByExample(map);
		return count;
	}
}
