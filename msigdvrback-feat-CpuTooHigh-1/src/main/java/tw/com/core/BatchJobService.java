/**
 *  Comware Copyright 2012
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 19, 2012
 **/
package tw.com.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.firebase.app.JsonService;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.manual.TbBatchJobLogMapper;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbDeviceMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbNewsManualMapper;
import tw.msigDvrBack.manual.TbSqlManualMapper;
import tw.msigDvrBack.manual.VwCustCarMapper;
import tw.msigDvrBack.manual.VwCustomerMapper;
import tw.msigDvrBack.mapper.TbGfbMsgMapper;
import tw.msigDvrBack.mapper.TbNewsUserMapper;
import tw.msigDvrBack.mapper.VwNewsMapper;
import tw.msigDvrBack.persistence.TbBatchJobLog;
import tw.msigDvrBack.persistence.TbCustomer;
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbGfbMsg;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.VwCustCar;
import tw.msigDvrBack.persistence.VwCustomer;
import tw.msigDvrBack.persistence.VwNews;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.AESUtilDmm;
import tw.util.CallWebApi;
import tw.util.CwDateUtils;
import tw.util.EmailUtil;

@Service
public class BatchJobService extends BaseService {

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	private VwCustomerMapper vwCustomerMapper;

	@Autowired
	private VwCustCarMapper vwCustCarMapper;

	@Autowired
	private VwNewsMapper vwNewsMapper;

	@Autowired
	private TbGfbMsgMapper tbGfbMsgMapper;

	@Autowired
	private TbBatchJobLogMapper tbBatchJobLogMapper;

	@Autowired
	protected JsonService jsonService;

	@Autowired
	private TbNewsUserMapper tbNewsUserMapper;

	@Autowired
	private TbDeviceMapper tbDeviceMapper;

	@Autowired
	private TbNewsManualMapper tbNewsManualMapper;

	@Autowired
	protected TbCustomerManualMapper tbCustomerManualMapper;

	@Autowired
	protected TbSqlManualMapper tbSqlManualMapper;
	
	@Autowired
	private EmailUtil emailUtil;
	/**
	 * 明台關懷推播產生
	 * 
	 * @author Jerry
	 * @since 2018/10/23
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public String executeJobCareCreate() throws Exception {

		JSONObject json = new JSONObject();
		String Joutput = null;
		try {
			
			// 20181115 Jerry edit:不使用mybatis內建方法，改使用自訂方式
			Map<String, Object> mapForExample = new HashMap<String, Object>();
			mapForExample.put("lookupType", Constants.STR_APPPR);
			mapForExample.put("lookupCde", Constants.STR_CAREDAY);	

			
			// 取得value為關懷通知推播-未登入使用APP天數
			// ,type1為系統日當天發送時間
			List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(mapForExample);
			
			Long valueDay = 0L;
			String SendDate = null;
			
			if (list.size() > 0) {
				TbLookupCde record = list.get(0);
				logger.info("=================Type1" + record.getType1());
				logger.info("=================Value" + record.getValue());
				if (record.getValue()!= null){
				valueDay = record.getValue();
				}
				if (record.getType1()!= null){
				SendDate = record.getType1();
				}
//				logger.info("record.getType1()"+record.getType1());
//				logger.info("record.getValue()"+record.getValue());
			}
			Calendar calendar = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat dateFormatForPushTime = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
			Date date = new Date();
			String DateFormat = dateFormat.format(date);
			Date DateParse = dateFormat.parse(DateFormat);
//			logger.info("DateParse" + DateParse);
			calendar.setTime(DateParse);
			calendar.add(Calendar.DATE, Math.toIntExact(valueDay));
			// 相加後的結果 APP登入時間 = 系統日+天數


//			logger.info("addDate" + addDate);

			logger.info("valueDay"+valueDay);
			// 20181122 Jerry edit:不使用mybatis內建方法，改使用自訂方式
			Map<String, Object> mapForSelectByAppLoginTime = new HashMap<String, Object>();
			mapForSelectByAppLoginTime.put("valueDay", valueDay*(-1));
//			logger.info("valueDay"+valueDay);
			List<VwCustomer> VwCusList2 = vwCustomerMapper.SelectByAppLoginTime(mapForSelectByAppLoginTime);
			String GfbDeviceId = null;
			logger.info("VwCusList2.size()"+VwCusList2.size());
			if (VwCusList2.size() > 0) {
				for (int i = 0; i < VwCusList2.size(); i++) {

					VwCustomer vwRecord2 = VwCusList2.get(i);
					logger.info("第" + i + "筆vwRecord.getGfbDeviceId()" + vwRecord2.getGfbDeviceId());

					GfbDeviceId = vwRecord2.getGfbDeviceId();
					// 判斷vw_customer.gfb_device_id是否為空值，如為空值則此筆資料不寫入。
					if (!"".equals(GfbDeviceId) && GfbDeviceId != null) {
			

						TbGfbMsg record = new TbGfbMsg();
						record.setMsgType(myContext.getMessage("batchJob.jobCareCreate.msgType"));
						record.setMsgClass(myContext.getMessage("batchJob.jobCareCreate.msgClass"));
						record.setMsgTitle(myContext.getMessage("batchJob.jobCareCreate.msgTitle"));
						record.setMsgText(myContext.getMessage("batchJob.jobCareCreate.msgText1") + valueDay + myContext.getMessage("batchJob.jobCareCreate.msgText2"));
						record.setUserId(vwRecord2.getUserId());
						record.setGfbDeviceId(vwRecord2.getGfbDeviceId());
						record.setPushStatus(myContext.getMessage("batchJob.jobCareCreate.msgPushStatus"));

						String addSendDate = DateFormat + " " + SendDate;
						Date sendDateParse = dateFormatForPushTime.parse(addSendDate);
//						logger.info("sendDateParse" + sendDateParse);
						record.setPrePushTime(sendDateParse);

						// 將此筆資料寫入至TB_GFB_MSG Google FireBase 訊息推播處理擋
						Integer countIns = tbGfbMsgMapper.insertSelective(record);
//						logger.info("TF :" + countIns);

						if (countIns == 1) {

							json.put("err_cde", "00");
							json.put("err_msg", "");
							Joutput = json.toString();
						} else {
							json.put("err_cde", "01");
							json.put("err_msg", myContext.getMessage("batchJob.errMsg.noData"));
							Joutput = json.toString();
						}
					}
					else {
						json.put("err_cde", "00");
						json.put("err_msg", "");
						Joutput = json.toString();
					}

				}
			} else {
				//vw_customer無資料
				json.put("err_cde", "00");
				json.put("err_msg", "");
				Joutput = json.toString();
			}

			return Joutput;

		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", myContext.getMessage("batchJob.errMsg.exception"));
			Joutput = json.toString();
			e.printStackTrace();
			return Joutput;
		}
	}

	/**
	 * 明台保單到期前N天提醒資料產生
	 * 
	 * @author Jerry
	 * @since 2018/10/24
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public String executeJobEndUBICreate() throws Exception {

		JSONObject json = new JSONObject();
		String Joutput = null;
		try {
			
			// 20181121 Jerry edit:不使用mybatis內建方法，改使用自訂方式
			Map<String, Object> mapForExample = new HashMap<String, Object>();
			mapForExample.put("lookupType", Constants.STR_APPPR);
			mapForExample.put("lookupCde", Constants.STR_INSURDAY);	
			
			// 取得value為對照時間天數
			// ,type1為系統日當天發送時間
			List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(mapForExample);
			Long ValueDay = null;
			String SendDate = null;
			if (list.size() > 0) {
				TbLookupCde record = list.get(0);

				logger.info("=================Type1" + record.getType1());
				logger.info("=================Value" + record.getValue());

				ValueDay = record.getValue();
				
				SendDate = record.getType1();
//				logger.info("record.getType1()"+record.getType1());
//				logger.info("record.getValue()"+record.getValue());
			}
//			Calendar calendar = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat dateFormatForPushTime = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
			Date date = new Date();
			String DateFormat = dateFormat.format(date);

			// 撈取vw_cust_car，撈取條件為 END_UBI_DATE UBI車險到期日 = 系統日+天數
			// 20181115 Jerry edit:不使用mybatis內建方法，改使用自訂方式
			Map<String, Object> mapForEndUbiDate = new HashMap<String, Object>();
			mapForEndUbiDate.put("ValueDay", ValueDay);

			List<VwCustCar> VwCusList2 = vwCustCarMapper.selectByEndUbiDate(mapForEndUbiDate);
			String GfbDeviceId = null;
			if (VwCusList2.size() > 0) {
				for (int i = 0; i < VwCusList2.size(); i++) {

					VwCustCar vwRecord2 = VwCusList2.get(i);
//					logger.info("第" + i + "筆vwRecord.getGfbDeviceId()" + vwRecord2.getGfbDeviceId());
					GfbDeviceId = vwRecord2.getGfbDeviceId();
					// 判斷vw_cust_car.gfb_device_id是否為空值，如為空值則此筆資料不寫入。
					if (!"".equals(GfbDeviceId) && GfbDeviceId != null) {


						TbGfbMsg record = new TbGfbMsg();
						record.setMsgType(myContext.getMessage("batchJob.jobEndUBICreate.msgType"));
						record.setMsgClass(myContext.getMessage("batchJob.jobEndUBICreate.msgClass"));
						record.setMsgTitle(myContext.getMessage("batchJob.jobEndUBICreate.msgTitle"));
						record.setMsgText(myContext.getMessage("batchJob.jobEndUBICreate.msgText1") + ValueDay + myContext.getMessage("batchJob.jobEndUBICreate.msgText2"));
						record.setUserId(vwRecord2.getUserId());
						record.setGfbDeviceId(vwRecord2.getGfbDeviceId());
						record.setPushStatus(myContext.getMessage("batchJob.jobEndUBICreate.msgPushStatus"));


						String addSendDate = DateFormat + " " + SendDate;
						Date sendDateParse = dateFormatForPushTime.parse(addSendDate);
						logger.info("addSendDate" + addSendDate);
						logger.info("系統日" + DateFormat);
						logger.info("SendDate" + SendDate);
						logger.info("sendDateParse" + sendDateParse);
						// tb_gfb_msg.pre_push_time = 系統日+ SendDate
						record.setPrePushTime(sendDateParse);

						// 將此筆資料寫入至TB_GFB_MSG Google FireBase 訊息推播處理擋
						Integer countIns = tbGfbMsgMapper.insertSelective(record);
//						logger.info("TF :" + countIns);

						if (countIns == 1) {

							json.put("err_cde", "00");
							json.put("err_msg", "");
							Joutput = json.toString();
						} else {
							json.put("err_cde", "00");
							json.put("err_msg", "");
							Joutput = json.toString();
						}
					}else{
						//沒有GFB_DEVICE_ID
						json.put("err_cde", "00");
						json.put("err_msg", "");
						Joutput = json.toString();
					}


				}
			} else {
				//vw_cust_car無資料
				json.put("err_cde", "00");
				json.put("err_msg", "");
				Joutput = json.toString();
			}



			return Joutput;

		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", myContext.getMessage("batchJob.errMsg.exception"));
			Joutput = json.toString();
			e.printStackTrace();
			return Joutput;
		}

	}

	/**
	 * 明台優惠消息推播資料產生
	 * 
	 * @author Jerry
	 * @since 2018/10/23
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public String executeJobPromoCreate() throws Exception {
		JSONObject json = new JSONObject();
		String Joutput = null;
		try {
			// 撈取vw_news最新消息設定檔，撈取pre_push_time < 一小時的資料

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -1);
//			logger.info(format.format(Calendar.getInstance().getTime()));
//			logger.info(format.format(cal.getTime()));

			
			Map<String, Object> mapHour = new HashMap<String, Object>();
			//測試用
//			mapHour.put("dToday", "2018-11-16 13:00:00");
//			mapHour.put("dDiffHour", "2018-11-16 12:00:00");
			mapHour.put("dToday", format.format(Calendar.getInstance().getTime()));
			mapHour.put("dDiffHour", format.format(cal.getTime()));
			
			List<VwNews> NewsList = vwNewsMapper.selectHourValue(mapHour);

//			logger.info("總共幾筆" + NewsList.size());
			logger.info("NewsSize"+NewsList.size());
			if (NewsList.size() > 0) {

				for (int i = 0; i < NewsList.size(); i++) {
					VwNews getNews = NewsList.get(i);
//					logger.info("總共幾筆" + NewsList.size());
//					logger.info("目前第幾筆" + i);

					String newsMsgClass = getNews.getMsgClass();
	

					Integer SeqNo = getNews.getSeqNo();

							TbGfbMsg record = new TbGfbMsg();
							record.setMsgType(myContext.getMessage("batchJob.jobPromoCreate.msgType"));
							record.setMsgClass(newsMsgClass);
							record.setMsgTitle(myContext.getMessage("batchJob.jobPromoCreate.msgTitle"));
							record.setMsgText(getNews.getTitle());
							record.setNewsSeqNo(getNews.getSeqNo());
							record.setPrePushTime(getNews.getPrePushTime());
							record.setUserId(getNews.getUserId());
							record.setGfbDeviceId(getNews.getGfbDeviceId());
							record.setPushStatus(myContext.getMessage("batchJob.jobPromoCreate.msgPushStatus"));

							// err_email tb_news.cr_user的email
//							TbNewsExample TbNewsExample = new TbNewsExample();
//							TbNewsExample.Criteria NewsCri = TbNewsExample.createCriteria();
//							NewsCri.andCrUserEqualTo(getNews.getCrUser());
//							String crUser = "";
							
							// 20181121 Jerry edit:不使用mybatis內建方法，改使用自訂方式
							Map<String, Object> mapForTbNews = new HashMap<String, Object>();
							mapForTbNews.put("crUser", getNews.getCrUser());

							record.setErrEmail(getNews.getEmail());


							//將tb_news 資料PUSH_STATUS更新為W(待推播)
								Map<String, Object> mapForUpdatePushStatus = new HashMap<String, Object>();
								mapForUpdatePushStatus.put("seqNo", SeqNo);
								logger.info("將tb_news 資料PUSH_STATUS更新為W(待推播)"+SeqNo);

								
								Integer countForUpdatePushStatus = tbNewsManualMapper.updatePushStatus(mapForUpdatePushStatus);
								

								if (countForUpdatePushStatus != 1) {
									json.put("err_cde", "01");
									json.put("err_msg", myContext.getMessage("batchJob.errMsg.updateFalse"));
									Joutput = json.toString();
								}

							// 判斷此筆資料是否為個人推播(msg_class=PERSONAL)，如為個人推播則更新TB_NEWS_USER會員最新消息個人推播檔
							// 將TB_NEWS_USER.push_status更新為W(待推播)
								logger.info("PERSONAL???"+newsMsgClass);
							if ("PERSONAL".equals(newsMsgClass)) {
								logger.info("PERSONAL???IN"+newsMsgClass);
								// 20181115 Jerry edit:不使用mybatis內建方法，改使用自訂方式
								Map<String, Object> mapForUpdateStatus = new HashMap<String, Object>();
								mapForUpdateStatus.put("seqNo", getNews.getNewsUserSeq());
								logger.info("SeqNo"+getNews.getNewsUserSeq());

								Integer countForUpdateStatus = tbNewsUserMapper.updateStatusBySeqNo(mapForUpdateStatus);
								
								
								if (countForUpdateStatus != 1) {
									json.put("err_cde", "01");
									json.put("err_msg", myContext.getMessage("batchJob.errMsg.updateFalse"));
									Joutput = json.toString();
								}
							}

							Integer countIns = tbGfbMsgMapper.insertSelective(record);

							if (countIns == 1) {

								json.put("err_cde", "00");
								json.put("err_msg", "");
								Joutput = json.toString();
							} else {
								json.put("err_cde", "01");
								json.put("err_msg", myContext.getMessage("batchJob.errMsg.insertFalse"));
								Joutput = json.toString();
							}

				}

			}else{
				json.put("err_cde", "00");
				json.put("err_msg", "");
				Joutput = json.toString();

		
				return Joutput;
			}

			return Joutput;
		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", myContext.getMessage("batchJob.errMsg.exception"));
			Joutput = json.toString();

			e.printStackTrace();
			return Joutput;
		}

	}

	/**
	 * 設備資訊同步
	 * 
	 * @author Jerry
	 * @since 2018/10/23
	 * @return
	 * @throws Exception
	 */

	public String executeJobSyncDevice() throws Exception {

		JSONObject json = new JSONObject();
		String output = null;
		// 取得UBI平台 網址
		// 20181121 Jerry edit:不使用mybatis內建方法，改使用自訂方式
		Map<String, Object> mapForExample = new HashMap<String, Object>();
		mapForExample.put("lookupType", Constants.WEBURL);
		mapForExample.put("lookupCde", Constants.TIOTDVR);

		List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(mapForExample);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
		String dateString = sdf.format(date);
		//fixed by Marks 2020/09/09
		if (list != null && list.size() > 0) {
			// 呼叫API【明台UBI_UBI平台_SIA】【13.設備LIST清單查詢】
			TbLookupCde record = list.get(0);
			String url = record.getType1() + "/TiotDvrWeb/json/getDeviceList.html";

			// 取得3DES KEY值
			// 20181121 Jerry edit:不使用mybatis內建方法，改使用自訂方式
			Map<String, Object> mapForExample3Des = new HashMap<String, Object>();
			mapForExample3Des.put("lookupType", Constants.AESKEY);
			mapForExample3Des.put("lookupCde", Constants.TIOTDVR);
			list = tbLookupCdeManualMapper.selectByExample(mapForExample3Des);
			String errcode = "";
			String errmsg = "";
			if (list != null && list.size() > 0) {
				//fixed 2020/10/14 hardcode
				TbLookupCde record2 = list.get(0);
				String key = new String();
				String iv = new String();
				if(record2 != null) {
					key = record2.getType1();
					iv = record2.getType2();
				}
				JSONObject jsonDevice = new JSONObject();
				jsonDevice.put("trx_time", dateString);
				String jsonDeviceOut = jsonDevice.toString();
				String jsonDeviceOutReplace = jsonDeviceOut.replace(CwConstants.SEPARATOR01, CwConstants.EMPTYSTRING);
				JSONObject jsonSecretData = new JSONObject();
				String jsonContent = AESUtilDmm.encrypt(jsonDeviceOutReplace, key, iv);
				jsonSecretData.put("secret_data", jsonContent);
				String result = CallWebApi.getUserStatusInfo(jsonSecretData.toString(), url, "application/json",
						"POST", 0, "UTF-8");
				JSONObject jsonResult = new JSONObject(result);

				if (jsonResult.has("errCde")) {
					errcode = jsonResult.getString("errCde");
				} else {
					errmsg = jsonResult.getString("errMsg");
					json.put("err_cde", "99");
					json.put("err_msg", myContext.getMessage("batchJob.errMsg.getDeviceApiError") + errmsg);
				}

				if (result != null && !"".equals(result) && "00".equals(errcode)) {
					Integer countDeleteTF = tbDeviceMapper.deleteAll();
					logger.info("countDeleteTF" + countDeleteTF);
					if (countDeleteTF == -1) {
//							logger.info("進入刪除失敗1");
						json.put("err_cde", "01");
						json.put("err_msg", myContext.getMessage("batchJob.errMsg.deleteFalse"));
					}

					// 逐筆將資料新增至TB_DEVICE設備主檔內
					if (jsonResult.has("device_list")) {
						JSONArray ArrayResult = jsonResult.getJSONArray("device_list");
						logger.info("JsonResult" + jsonResult);
						for (int i = 0; i < ArrayResult.length(); ++i) {
							String deviceType = "";
							String sn = "";
							String imei = "";
							String deviceStatus = "";
							String userId = "";
							String bindDate = "";
							String devEffDate = "";
							String devEndDate = "";
							String targetNo = "";
							String serviceType = "";
							String simMobile = "";
							String simEffDate = "";
							String simEndDate = "";
							String uploadDate = "";
							String crUser = "";
							String crDate = "";
							String userStamp = "";
							String dateStamp = "";

							JSONObject resultVal = ArrayResult.getJSONObject(i);
							logger.info("resultVal: {}", toJson(resultVal));
							if (resultVal.has("device_type")) {
								deviceType = resultVal.getString("device_type");
							}
							if (resultVal.has("sn")) {
								sn = resultVal.getString("sn");
							}
							if (resultVal.has("imei")) {
								imei = resultVal.getString("imei");
							}
							if (resultVal.has("device_status")) {
								deviceStatus = resultVal.getString("device_status");
							}
							if (resultVal.has("user_id")) {
								userId = resultVal.getString("user_id");
							}
							if (resultVal.has("bind_date")) {
								bindDate = resultVal.getString("bind_date");
							}
							if (resultVal.has("dev_eff_date")) {
								devEffDate = resultVal.getString("dev_eff_date");
							}

							if (resultVal.has("dev_end_date")) {
								devEndDate = resultVal.getString("dev_end_date");
							}
							if (resultVal.has("target_no")) {
								targetNo = resultVal.getString("target_no");
							}
							if (resultVal.has("service_type")) {
								serviceType = resultVal.getString("service_type");
							}
							if (resultVal.has("sim_mobile")) {
								simMobile = resultVal.getString("sim_mobile");
							}
							if (resultVal.has("sim_eff_date")) {
								simEffDate = resultVal.getString("sim_eff_date");
							}
							if (resultVal.has("sim_end_date")) {
								simEndDate = resultVal.getString("sim_end_date");
							}
							if (resultVal.has("upload_date")) {
								uploadDate = resultVal.getString("upload_date");
							}
							if (resultVal.has("cr_user")) {
								crUser = resultVal.getString("cr_user");
							}
							if (resultVal.has("cr_date")) {
								crDate = resultVal.getString("cr_date");
							}
							if (resultVal.has("userstamp")) {
								userStamp = resultVal.getString("userstamp");
							}
							if (resultVal.has("datestamp")) {
								dateStamp = resultVal.getString("datestamp");
							}

							logger.info("---------------E----------------");
							// 逐筆將資料新增到TB_DEVICE設備主檔
							TbDevice deviceRecord = new TbDevice();
							deviceRecord.setDeviceType(deviceType);
							deviceRecord.setSn(sn);
							deviceRecord.setImei(imei);
							deviceRecord.setDeviceStatus(deviceStatus);
							deviceRecord.setUserId(userId);
							if (bindDate != null && !"".equals(bindDate)) {
								deviceRecord.setBindDate(CwDateUtils.formatDateTime(bindDate));
							}
							if (devEffDate != null && !"".equals(devEffDate)) {
								deviceRecord.setDevEffDate(CwDateUtils.formatDate(devEffDate));
							}
							if (devEndDate != null && !"".equals(devEndDate)) {
								deviceRecord.setDevEndDate(CwDateUtils.formatDate(devEndDate));
							}
							deviceRecord.setTargetNo(targetNo);
							deviceRecord.setServiceType(serviceType);
							deviceRecord.setSimMobile(simMobile);
							if (simEffDate != null && !"".equals(simEffDate)) {
								deviceRecord.setSimEffDate(CwDateUtils.formatDate(simEffDate));
							}
							if (simEndDate != null && !"".equals(simEndDate)) {
								deviceRecord.setSimEndDate(CwDateUtils.formatDate(simEndDate));
							}
							if (uploadDate != null && !"".equals(uploadDate)) {
								deviceRecord.setUploadDate(CwDateUtils.formatDateTime(uploadDate));
							}
							deviceRecord.setCrUser(crUser);
							if (crDate != null && !"".equals(crDate)) {
								deviceRecord.setCrDate(CwDateUtils.formatDateTime(crDate));
							}
							deviceRecord.setUserstamp(userStamp);
							if (dateStamp != null && !"".equals(dateStamp)) {
								deviceRecord.setDatestamp(CwDateUtils.formatDateTime(dateStamp));
							}
							tbDeviceMapper.insertSelective(deviceRecord);
						}
						json.put("err_cde", "00");
						json.put("err_msg", "");
					}
				}
			} else {
                json.put("err_cde", "01");
                json.put("err_msg", "基本參數未設定AESKEY/TIOTDVR");
			}
		} else {
            json.put("err_cde", "01");
            json.put("err_msg", "基本參數未設定WEBURL/TIOTDVR");
        }
		String jobPgName = "JobSyncDevice";
		String jobDscr = "設備資訊同步";
		insertTbBatchJobLog(jobPgName, jobDscr, json.getString("err_cde"), json.getString("err_msg"));
		output = json.toString();
		return output;
	}

	private void insertTbBatchJobLog(String jobPgName, String jobDscr, String errCde, String errMsg) {
		TbBatchJobLog tbBatchJobLog = new TbBatchJobLog();
		try {
			tbBatchJobLog.setJobPgName(jobPgName);
			tbBatchJobLog.setJobDscr(jobDscr);
			tbBatchJobLog.setRunDate(new Date());
			tbBatchJobLog.setErrCde(errCde);
			tbBatchJobLog.setErrMsg(errMsg);
			tbBatchJobLogMapper.insertSelective(tbBatchJobLog);
		} catch (Exception e) {
			logger.error("insertResultByError error:", e);
		}
	}

	public String jobEmailDriveReport() {
		JSONObject json = new JSONObject();
		String output = null;

		try {
			String apiUrl = null;
			Map<String, Object> mapForExample = new HashMap<String, Object>();
			mapForExample.put("lookupType", Constants.WEBURL);
			mapForExample.put("lookupCde", Constants.TIOTDVR);
			List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(mapForExample);
			if (list.size() > 0) {
				TbLookupCde record = list.get(0);
				apiUrl = record.getType1();
			}

			if (!StringUtils.isEmpty(apiUrl)) {
				// 取得要寄信的會員和駕駛報告
				List<Map<String, Object>> customerList = tbSqlManualMapper.getCustomerToSendEmail();
				Map<String,String> mailSettingMp = emailUtil.checkActAdnPwd(); //先取得email相關設定
				for (Map<String, Object> map : customerList) {
					String userId = String.valueOf(map.get("userId"));
					String email = String.valueOf(map.get("email"));
					String reportMon = String.valueOf(map.get("report_mon"));
					String href = apiUrl + "/TiotDvrWeb/web/driveReport.html?user_id=" + userId + "&report_mon=" + reportMon;
					
					Map<String,String> mailContentMap = emailUtil.getDriveReportMailContent(email, href);
					boolean sendEmail = emailUtil.sendCheckMessage(mailSettingMp, mailContentMap);
					TbBatchJobLog jobLog = new TbBatchJobLog();
					jobLog.setJobPgName("JobEmailDriveReport");
					jobLog.setJobDscr("每月提供會員駕駛行為分析報告通知信");
					jobLog.setRunDate(new Date());
					if (sendEmail) {
						jobLog.setErrCde("00");
						jobLog.setErrMsg("");
					} else {
						jobLog.setErrCde("01");
						jobLog.setErrMsg("寄信失敗");
					}
					tbBatchJobLogMapper.insertSelective(jobLog);
				}
				json.put("err_cde", "00");
				json.put("err_msg", "");
				output = json.toString();
			} else {
                json.put("err_cde", "01");
                json.put("err_msg", "基本參數未設定WEBURL/TIOTDVR");
            }
		} catch (Exception e) {
			logger.error("", e);
		}
		return output;
	}
	
	public String jobEmailDeviceUnused() throws Exception {
		JSONObject json = new JSONObject();
		String output = "";

		// 取得長期未使用DVR的會員清單
		Map<String, Object> mapForExample = new HashMap<String, Object>();
		mapForExample.put("lookupType", Constants.SYSPR);
		mapForExample.put("lookupCde", Constants.UNUSEDDAYS);

		Integer sysUnUsedDays = null;
		String apiUrl = "";
		String aesKey = new String();
		String aesIv = new String();
		String jobPgName = "JobEmailDeviceUnused";
		String jobDscr = "長期未使用DVR的會員關懷信件寄送";

		List<TbLookupCde> list = tbLookupCdeManualMapper.selectByExample(mapForExample);
		//fixed by Marks 2020/09/09
		TbLookupCde record = null;
		if (list != null && list.size() > 0) {
			record = list.get(0);
			//fixed by Marks 2020/10/12 Hard code
			if(record != null) {
				sysUnUsedDays = record.getValue().intValue();
			}
		}

		mapForExample.put("lookupType", Constants.WEBURL);
		mapForExample.put("lookupCde", Constants.TIOTDVR);
		list = tbLookupCdeManualMapper.selectByExample(mapForExample);
		record = list.get(0);
		apiUrl = record.getType1() + "/TiotDvrWeb/json/getDeviceUnusedList.html";

		mapForExample.put("lookupType", Constants.AESKEY);
		mapForExample.put("lookupCde", Constants.TIOTDVR);
		list = tbLookupCdeManualMapper.selectByExample(mapForExample);
		if (list != null && list.size() > 0) {
			record = list.get(0);
			aesKey = record.getType1();
			aesIv = record.getType2();
		}

		if (!StringUtils.isEmpty(apiUrl) && !StringUtils.isEmpty(aesKey) && !StringUtils.isEmpty(aesIv)
				&& sysUnUsedDays != null) {

//			String test = "2020/06/01 12:20:55";
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
			String trxTime = sdf.format(date);
			Map<String, Object> secretDataMap = new HashMap<String, Object>();
			secretDataMap.put("unused_days", String.valueOf(sysUnUsedDays));
			secretDataMap.put("trx_time", trxTime);

			String secretJson = toJson(secretDataMap);
			logger.info("jobEmailDeviceUnused before encrypt: {}", secretJson);
			String jsonContent = AESUtilDmm.encrypt(secretJson, aesKey, aesIv);
			logger.info("jobEmailDeviceUnused after encrypt: {}", jsonContent);

			Map<String, Object> secretData = new HashMap<String, Object>();
			secretData.put("secret_data", jsonContent);
			String result = CallWebApi.getUserStatusInfo(toJson(secretData), apiUrl, "application/json",
					"POST", 0, "UTF-8");
			JSONObject jsonResult = new JSONObject(result);

			String errcode = null;
			String errmsg;
			if (jsonResult.has("errCde")) {
				errcode = jsonResult.getString("errCde");
				if ("00".equals(errcode)) {
					// 逐筆資料處理
					if (jsonResult.has("device_unused_list")) {
						JSONArray arrayResult = jsonResult.getJSONArray("device_unused_list");
						logger.info("JsonResult" + jsonResult);
						
						Map<String, String> emailSettingMp = emailUtil.checkActAdnPwd(); //先取得email相關設定
						for (int i = 0; i < arrayResult.length(); i++) {
							JSONObject resultVal = arrayResult.getJSONObject(i);
							String sn = "";
							String userId = "";
							String unusedDays = "";
							String uploadData = "";
							if (resultVal.has("unused_days") && (!"".equals(resultVal.getString("unused_days")))) {
								unusedDays = resultVal.getString("unused_days");
								Integer unuseDaysInt = Double.valueOf(unusedDays).intValue();
								if (!(Double.valueOf(unusedDays).intValue() == sysUnUsedDays)) {
									continue;
								} else {
									userId = resultVal.getString("user_id");
									TbCustomer tbCustomer = tbCustomerManualMapper.selectByPrimaryKey(userId);
									if (tbCustomer == null) {
										continue;
									} else {

										String email = tbCustomer.getEmail();
										//取得信件內容
										Map<String, String> mailContentMap = emailUtil.getDeviceUnusedMailContent(email, unuseDaysInt);
										//寄信
                                        boolean sendEmail = emailUtil.sendCheckMessage(emailSettingMp, mailContentMap);
                                        TbBatchJobLog jobLog = new TbBatchJobLog();
                                        jobLog.setJobPgName("JobEmailDeviceUnused");
                                        jobLog.setJobDscr("長期未使用DVR的會員關懷信件寄送");
                                        jobLog.setRunDate(new Date());
                                        if (sendEmail) {
                                            jobLog.setErrCde("00");
                                            jobLog.setErrMsg("");
                                        } else {
                                            jobLog.setErrCde("01");
                                            jobLog.setErrMsg("寄信失敗");
                                        }
										tbBatchJobLogMapper.insertSelective(jobLog);
									}
								}
							}
						}
						json.put("err_cde", "00");
						json.put("err_msg", "");
					}
				} else {
					errmsg = jsonResult.getString("errMsg");
					json.put("err_cde", "99");
					json.put("err_msg", myContext.getMessage("batchJob.errMsg.getDeviceUnusedApiError") + "," + errmsg);
				}
			} else {
				errmsg = jsonResult.getString("errMsg");
				json.put("err_cde", "99");
				json.put("err_msg", myContext.getMessage("batchJob.errMsg.getDeviceUnusedApiError") + "," + errmsg);
			}
		} else {
			json.put("err_cde", "01");
			json.put("err_msg", "缺少系統參數");
		}
		insertTbBatchJobLog(jobPgName, jobDscr, json.getString("err_cde"), json.getString("err_msg"));
		output = json.toString();
		return output;
	}
}
