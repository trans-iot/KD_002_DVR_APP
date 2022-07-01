package tw.msigDvrBack.omom001m;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.TbCustCarManualMapper;
import tw.msigDvrBack.manual.TbCustSecurityManualMapper;
import tw.msigDvrBack.manual.TbCustUbiApplyMapper;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbDeviceManualMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.persistence.TbCustCar;
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.AESUtilDmm;
import tw.util.CallWebApi;

@Service
public class OMOM002FService extends BaseService {

	@Autowired
	private MyContext myContext;

	@Autowired
	private TbCustomerManualMapper tbCustomerManualMapper;

	@Autowired
	private TbCustCarManualMapper tbCustCarManualMapper;

	@Autowired
	private TbDeviceManualMapper tbDeviceManualMapper;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	private TbCustUbiApplyMapper tbCustUbiApplyMapper;

	@Autowired
	private TbCustSecurityManualMapper tbCustSecurityMapper;

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
	public List<Map<String, Object>> queryList(OMOM002FQueryForm form) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(form.getUserId())) {
			if (StringUtils.contains(form.getUserId(), "%")) {
				map.put("userIdLike", form.getUserId());
			} else {
				map.put("userId", form.getUserId());
			}
		}
		if (StringUtils.isNotBlank(form.getCarNo())) {
			if (StringUtils.contains(form.getCarNo(), "%")) {
				map.put("carNoLike", form.getCarNo());
			} else {
				map.put("carNo", form.getCarNo());
			}
		}
		if (StringUtils.isNotBlank(form.getEmail())) {
			if (StringUtils.contains(form.getEmail(), "%")) {
				map.put("emailLike", form.getEmail());
			} else {
				map.put("email", form.getEmail());
			}
		}
		if (StringUtils.isNotBlank(form.getUserName())) {
			if (StringUtils.contains(form.getUserName(), "%")) {
				map.put("userNameLike", form.getUserName());
			} else {
				map.put("userName", form.getUserName());
			}
		}
		List<Map<String, Object>> customerAndCustCar002 = tbCustomerManualMapper.getCustomerAndCustCar002(map);

		return customerAndCustCar002;
	}

	public TbCustCar query001(String userId) {
		return tbCustCarManualMapper.selectByPrimaryKey(userId);

	}

	public List<TbDevice> query002(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return tbDeviceManualMapper.selectByExample(map);
	}

	public List<TbDevice> queryRemoveDevice(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return tbDeviceManualMapper.selectByExample(map);
	}

	public TbCustCar queryByPK(String userId) {
		return tbCustCarManualMapper.selectByPrimaryKey(userId);
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
//	public Map<String, String> removeDevice(OMOM002FRomForm omom003fRomForm) throws Exception {
//		Map<String, String> map = new HashMap<String, String>();
//		Map<String, Object> queryMap = new HashMap<String, Object>();
//		queryMap.put("lookupType", Constants.DESKEY);
//		queryMap.put("lookupCde", Constants.TIOTUBI);
//		List<TbLookupCde> lookupList = tbLookupCdeManualMapper.selectByExample(queryMap);
//		//fixed by Marks 2020/09/09
//		String desKey = new String();
//		String iv = new String();
//		TbLookupCde lookupCde = null;
//		if(lookupList != null && lookupList.size() > 0) {
//			lookupCde = lookupList.get(0);
//			//fixed by Marks 2020/10/12 Hard code
//			if(lookupCde != null) {
//				desKey = lookupCde.getType1();
//				iv = lookupCde.getType2();
//			}
//		}
//		JSONObject jsonObject = new JSONObject(omom003fRomForm);
//
//		logger.info("removeDevice: omom003fRomForm", ToStringBuilder.reflectionToString(omom003fRomForm));
//		String secret_data = AESUtilDmm.encrypt(jsonObject.toString(), desKey, iv);
//		JSONObject content = new JSONObject();
//		content.put("secret_data", secret_data);
//		Map<String, Object> queryMapWeburl = new HashMap<String, Object>();
//		queryMapWeburl.put("lookupType", Constants.WEBURL);
//		queryMapWeburl.put("lookupCde", Constants.TIOTUBI);
//		List<TbLookupCde> lookupListDomain = tbLookupCdeManualMapper.selectByExample(queryMapWeburl);
//		String url = lookupListDomain.get(0).getType1() + "/TiotUbiWeb/json/removeDevice.html";
//		// String url =
//		// "http://localhost:8080/TiotUbiWeb/json/removeDevice.html";
//		// SIT環境
//		// String url =
//		// "http://13.112.1.27:8080/MsigDvrWeb/json/checkToken.html";
//		logger.info("removeDevice: CallWebApi url", url);
//		String result = CallWebApi.callHttps(content.toString(), url, "application/json", "POST", 0, "UTF-8");
//		logger.info("removeDevice: CallWebApi result", result);
//		if (result.equals("error")) {
//			map.put("errCde", "01");
//			map.put("errMsg", myContext.getMessage("timeout.error"));
//			return map;
//		}
//		map.put("errCde", (String) new JSONObject(result).get("errCde"));
//		map.put("errMsg", (String) new JSONObject(result).get("errMsg"));
//		return map;
//	}

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
	public Integer updateTbDevice(OMOM002FRomForm omom002FRomForm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceType", omom002FRomForm.getDevice_type());
		map.put("macAddress", omom002FRomForm.getMac_address());
		map.put("sn", omom002FRomForm.getSn());
		// 查詢主檔
		List<TbDevice> vwCustUbiApplyList = this.tbDeviceManualMapper.selectByExample(map);
		TbDevice tbDevice = vwCustUbiApplyList.get(0);
		tbDevice.setDeviceStatus("REMOVE");
		tbDevice.setUserId(null);
		tbDevice.setBindDate(null);
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("userId", omom002FRomForm.getUser_id());
		deleteMap.put("sysId", "MSIGUBI");
		tbCustSecurityMapper.deleteByExample(deleteMap);
		return tbDeviceManualMapper.updateByPrimaryKey(tbDevice);
	}

	public Integer updateTbCustCar(OMOM002FRomForm omom002fRomForm) {
		TbCustCar tbCustCar = tbCustCarManualMapper.selectByPrimaryKey(omom002fRomForm.getUser_id());
		tbCustCar.setEffMileageDate(new Date());
		tbCustCar.setMileageStatus("RESET");
		tbCustCar.setResetTime(new Date());
		tbCustCar.setUserId(omom002fRomForm.getUser_id());
		return tbCustCarManualMapper.updateByPrimaryKey(tbCustCar);
	}

//	public Map<String, String> resetMileage(OMOM002FResForm form) throws Exception {
//		Map<String, String> map = new HashMap<String, String>();
//		Map<String, Object> queryMap = new HashMap<String, Object>();
//		queryMap.put("lookupType", Constants.DESKEY);
//		queryMap.put("lookupCde", Constants.TIOTUBI);
//		List<TbLookupCde> lookupList = tbLookupCdeManualMapper.selectByExample(queryMap);
//		//fixed by Marks 2020/09/09
//		String desKey = new String();
//		String iv = new String();
//		TbLookupCde lookupCde = null;
//		if(lookupList != null && lookupList.size() > 0) {
//			lookupCde = lookupList.get(0);
//			//fixed by Marks 2020/10/12 Hard code
//			if(lookupCde != null) {
//				desKey = lookupCde.getType1();
//				iv = lookupCde.getType2();
//			}
//		}
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("user_id", form.getUser_id());
//		jsonObject.put("trx_time", form.getTrx_time());
//		logger.info("resetMileage: OMOM002FResForm", ToStringBuilder.reflectionToString(form));
//		String secret_data = AESUtilDmm.encrypt(jsonObject.toString(), desKey, iv);
//		JSONObject content = new JSONObject();
//		content.put("secret_data", secret_data);
//		Map<String, Object> queryMap2 = new HashMap<String, Object>();
//		queryMap2.put("lookupType", Constants.WEBURL);
//		queryMap2.put("lookupCde", Constants.TIOTUBI);
//		List<TbLookupCde> lookupListDomain = tbLookupCdeManualMapper.selectByExample(queryMap2);
//		String url = lookupListDomain.get(0).getType1() + "/TiotUbiWeb/json/resetMileage.html";
//		// String url =
//		// "http://localhost:8080/TiotUbiWeb/json/resetMileage.html";
//		// SIT環境
//		// String url =
//		// "http://13.112.1.27:8080/MsigDvrWeb/json/checkToken.html";
//		logger.info("resetMileage: CallWebApi url", url);
//		String result = CallWebApi.callHttps(content.toString(), url, "application/json", "POST", 0, "UTF-8");
//		logger.info("resetMileage: CallWebApi result", result);
//		if (result.equals("error")) {
//			map.put("errCde", "01");
//			map.put("errMsg", myContext.getMessage("timeout.error"));
//			return map;
//		}
//		map.put("errCde", (String) new JSONObject(result).get("errCde"));
//		map.put("errMsg", (String) new JSONObject(result).get("errMsg"));
//		return map;
//	}

	public Integer updateTbCustCarReset(OMOM002FResForm form) {
		TbCustCar tbCustCar = tbCustCarManualMapper.selectByPrimaryKey(form.getUser_id());
		tbCustCar.setCarNo(form.getNewCarNo());
		tbCustCar.setCarnoUpdate(new Date());
		if (form.getIs_reset().equals("Y")) {
			tbCustCar.setEffMileageDate(new Date());
			tbCustCar.setMileageStatus("RESET");
			tbCustCar.setResetTime(new Date());
		}
		return tbCustCarManualMapper.updateByPrimaryKey(tbCustCar);
	}

	public List<Map<String, Object>> queryUserIdList(OMOM002FUserIdQueryForm form) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(form.getUserId())) {
			if (StringUtils.contains(form.getUserId(), "%")) {
				map.put("userIdLike", form.getUserId());
			} else {
				map.put("userId", form.getUserId());
			}
		}
		if (StringUtils.isNotBlank(form.getCarNo())) {
			if (StringUtils.contains(form.getCarNo(), "%")) {
				map.put("carNoLike", form.getCarNo());
			} else {
				map.put("carNo", form.getCarNo());
			}
		}
		if (StringUtils.isNotBlank(form.getEmail())) {
			if (StringUtils.contains(form.getEmail(), "%")) {
				map.put("emailLike", form.getEmail());
			} else {
				map.put("email", form.getEmail());
			}
		}
		if (StringUtils.isNotBlank(form.getUserName())) {
			if (StringUtils.contains(form.getUserName(), "%")) {
				map.put("userNameLike", form.getUserName());
			} else {
				map.put("userName", form.getUserName());
			}
		}
		return tbCustomerManualMapper.getCustomerAndCustCar002(map);
	}

	public OMOM002FLovQueryForm decodeLovQueryForm(OMOM002FLovQueryForm form) throws Exception {
		form.setUserId(URLDecoder.decode(form.getUserId(), "UTF-8"));
		form.setEmail(URLDecoder.decode(form.getEmail(), "UTF-8"));
		form.setUserName(URLDecoder.decode(form.getUserName(), "UTF-8"));
		form.setMobilePhone(URLDecoder.decode(form.getMobilePhone(), "UTF-8"));
		return form;

	}

	public Integer executeCancelApplyingUbi(OMOM002FResForm form) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", form.getUser_id());
		return tbCustUbiApplyMapper.updateApplyingUbi(map);

	}

	public Integer updateTbCustCar(OMOM002FResForm form) {
		TbCustCar tbCustCar = new TbCustCar();
		tbCustCar.setCarNo(form.getNewCarNo());
		tbCustCar.setCarnoUpdate(new Date());
		return tbCustCarManualMapper.updateByPrimaryKey(tbCustCar);
	}

	public Integer updateTbCustCarLov(OMOM002FUpdCarForm form) throws ParseException {
		TbCustCar tbCustCar = tbCustCarManualMapper.selectByPrimaryKey(form.getUserId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (StringUtils.isNotBlank(form.getEffUbiDate())) {
			tbCustCar.setEffUbiDate(sdf.parse(form.getEffUbiDate()));
		} else {
			tbCustCar.setEffUbiDate(null);
		}
		if (StringUtils.isNotBlank(form.getEndUbiDate())) {
			tbCustCar.setEndUbiDate(sdf.parse(form.getEndUbiDate()));
		} else {
			tbCustCar.setEndUbiDate(null);
		}
		tbCustCar.setServicer(form.getServicer());
		tbCustCar.setUserId(form.getUserId());
		return tbCustCarManualMapper.updateByPrimaryKey(tbCustCar);
	}
}
