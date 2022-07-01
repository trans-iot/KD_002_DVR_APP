package tw.firebase.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.CommonService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.TbGfbDeviceIdManualMapper;
import tw.msigDvrBack.manual.TbGfbMsgManualMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbNewsManualMapper;
import tw.msigDvrBack.manual.TbNewsUserManualMapper;
import tw.msigDvrBack.persistence.TbGfbDeviceId;
import tw.msigDvrBack.persistence.TbGfbMsg;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbNews;
import tw.spring.ComLogger;
import tw.spring.MyContext;

@Service
public class JsonService extends BaseService {

	private static final String URL = "https://fcm.googleapis.com/fcm/notification";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private TbGfbMsgManualMapper tbGfbMsgManualMapper;

	@Autowired
	private TbNewsManualMapper tbNewsManualMapper;

	@Autowired
	private TbNewsUserManualMapper tbNewsUserManualMapper;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	private TbGfbDeviceIdManualMapper tbGfbDeviceIdManualMapper;

	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;
	
	@Autowired
	private CommonService commonService;

	public JSONObject executeJobPush() throws IOException {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", Constants.SYSPR);
		queryMap.put("lookupCde", Constants.GFB);
		List<TbLookupCde> gfbLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMap);
		TbLookupCde gfbLookupCde = gfbLookupCdeList.get(0);
		Messaging.GOOGLE_KEY = gfbLookupCde.getType2();
		
		JSONObject jsonObject = new JSONObject();
		Date now = new Date();
		Messaging messaging = new Messaging();
		Map<String,Object> queryMapGfbdg = new HashMap<String,Object>();
		queryMapGfbdg.put("lookupType", Constants.SYSPR);
		queryMapGfbdg.put("lookupCde", Constants.GFBDG);
		List<TbLookupCde> gfbdgLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMapGfbdg);
		TbLookupCde gfbdgLookupCde = gfbdgLookupCdeList.get(0);
		// 1.撈取TB_GFB_MSG Google FireBase 訊息推播處理擋，撈取PRE_PUSH_TIME < 系統日 且
		// PUSH_STATUS = N 的資料
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prePushTimeLessThan", now);
		map.put("pushStatus","N" );
		List<TbGfbMsg> tbGfbMsgList = tbGfbMsgManualMapper.selectByExample(map);
		// 2.逐筆判斷，如msg_type != B，跳制步驟3，如msg_type = B則繼續2.1步驟
		for (TbGfbMsg tbGfbMsg : tbGfbMsgList) {
			// 2.1.將msg_type = B資料逐筆更新，若msg_class = PERSONAL則需多走步驟2.1.2
			if (tbGfbMsg.getMsgType().equals("B")) {
				// 2.1.1.更新TB_NEWS最新消息設定檔，將此筆資料PUSH_STATUS更新為W
				TbNews tbNews = new TbNews();
				tbNews.setSeqNo(tbGfbMsg.getNewsSeqNo());
				tbNews.setPushStatus("W");
				tbNewsManualMapper.updateByPrimaryKeySelective(tbNews);
				// 2.1.2.若msg_class = PERSONAL，更新TB_NEWS_USER會員最新消息個人推播擋，將此筆資料PUSH_STATUS更新為W
				if (tbGfbMsg.getMsgClass().equals("PERSONAL")) {
					Map<String,Object> updateMap = new HashMap<String,Object>();
					updateMap.put("newsSeqNo", tbGfbMsg.getNewsSeqNo());
					updateMap.put("userId",tbGfbMsg.getUserId() );
					updateMap.put("pushStatus","W" );
//					System.out.println(updateMap);
					tbNewsUserManualMapper.updateByExampleSelective(updateMap);
				}
			}
			// 3.呼叫google firebase進行推播
			String returnMsg = "";
			if (tbGfbMsg.getMsgClass().equals("PUBLIC")) {
				// 3.1.判斷如msg_class = PUBLIC，則進行群發推播
				returnMsg = messaging.doMessage(gfbdgLookupCde.getType2(), tbGfbMsg.getMsgTitle(), tbGfbMsg.getMsgText());
			} else if (tbGfbMsg.getMsgClass().equals("PERSONAL")) {
				// 3.2.判斷如msg_class = PERSONAL，則根據此筆資料之推播ID進行推播
				returnMsg = messaging.doMessage(tbGfbMsg.getGfbDeviceId(), tbGfbMsg.getMsgTitle(),
						tbGfbMsg.getMsgText());
			}
			logger.debug("returnMsg : " + returnMsg);
//			System.out.println(returnMsg);
			TbGfbMsg bean = new TbGfbMsg();
			JSONObject json = new JSONObject(returnMsg);
			// 3.3.根據推播結果將推送狀態更新，推播成功PUSH_STATUS=S，推播失敗PUSH_STATUS=F
			if (   json.has("success")&& (json.getInt("success") > 0 || (tbGfbMsg.getMsgClass().equals("PUBLIC") && !json.getJSONArray("results").getJSONObject(0).getString("error").equals("") ) ) ) {
				bean.setSeqNo(tbGfbMsg.getSeqNo());
				bean.setPushStatus("S");
				bean.setPushTime(new Date());
				bean.setCompDate(new Date());
				tbGfbMsgManualMapper.updateByPrimaryKeySelective(bean);
			} else {
				// 3.3.1.推播失敗，則將firebase失敗訊息寫入err_msg，
				bean.setPushTime(new Date());
				bean.setSeqNo(tbGfbMsg.getSeqNo());
				bean.setPushStatus("F");
				bean.setErrMsg(json.getJSONArray("results").getJSONObject(0).getString("error"));
				tbGfbMsgManualMapper.updateByPrimaryKeySelective(bean);
				// 3.3.2.推播失敗，發送 EMAIL 給 ERR_EMAIL名單
				// 主旨：推播失敗 - MSG_TITLE
				// 內容：推播失敗 - MSG_TITLE
				// MSG_TEXT
				sendEmail(tbGfbMsg.getErrEmail(), tbGfbMsg.getMsgTitle(), tbGfbMsg.getMsgText());
			}
			// 3.4.判斷NEWS_SEQ_NO是否為空，如不為空則更新TB_NEWS最新消息設定檔及TB_NEWS_USER會員最新消息檔。PUSH_STATUS=S，推播失敗PUSH_STATUS=F
			if (tbGfbMsg.getNewsSeqNo() != null) {
				String status = null;
				if (json.has("success")&& (json.getInt("success") > 0 || (tbGfbMsg.getMsgClass().equals("PUBLIC") && !json.getJSONArray("results").getJSONObject(0).getString("error").equals("") ) )) {
					status = "S";
				} else {
					status = "F";

				}
//				System.out.println(status);
				if (tbGfbMsg.getMsgClass().equals("PUBLIC")) {
					TbNews tbNews = new TbNews();
					tbNews.setSeqNo(tbGfbMsg.getNewsSeqNo());
					tbNews.setPushStatus(status);
					tbNews.setPushTime(new Date());
					tbNewsManualMapper.updateByPrimaryKeySelective(tbNews);
				} else if (tbGfbMsg.getMsgClass().equals("PERSONAL")) {
					Map<String,Object> updateMap = new HashMap<String,Object>();
					updateMap.put("newsSeqNo", tbGfbMsg.getNewsSeqNo());
					updateMap.put("userId",tbGfbMsg.getUserId() );
					updateMap.put("pushStatus",status );
					updateMap.put("pushTime",new Date() );
					tbNewsUserManualMapper.updateByExampleSelective(updateMap);
				}
			}
		}
		int count = tbNewsUserManualMapper.updatePushed();
		logger.info("updatePushed count : " + count);
		jsonObject.put("err_cde", "00");
		jsonObject.put("err_msg", "");
		return jsonObject;
	}

	public boolean sendEmail(String errEmail, String msgTitle, String msgText) {
		try {
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("lookupType", Constants.SYSPR);
			queryMap.put("lookupCde", Constants.SMTPIP);
			TbLookupCde lookup = tbLookupCdeManualMapper.selectByExample(queryMap).get(0);

			String emailHost = lookup.getDscr();
			String emailAccount = lookup.getType1();
			//password
			String emailKeyPass = lookup.getType2();
			
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			  
		    Properties props = new Properties();
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.host", emailHost);
		    //	config.emailHost  "smtp.gmail.com"
		   // props.put("mail.smtp.port", "587");  Gmail Port
			senderImpl.setJavaMailProperties(props);
			
		    //  設定 Mail Server   先暫時不要設定
		    senderImpl.setUsername(emailAccount);
		    //	config.emailAccount
		    senderImpl.setPassword(emailKeyPass);
		    //	config.emailPassword
		    
		    // 建立郵件訊息
		    MimeMessage mailMessage = senderImpl.createMimeMessage();
		    //未加 GBK 會產生亂碼
		    MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"GBK"); ;
		    StringBuffer html_footer =getHtml_Footer();
		    StringBuffer html_css = getHtml_Css();
		    
		    // 設定收件人、寄件人、主題與內文
		    messageHelper.setTo(errEmail);
		    // address
		    messageHelper.setFrom(emailAccount);
		    //config.emailAccount
		    messageHelper.setSubject(msgTitle);
		    
		    messageHelper.setText(
			    	  html_css.toString()
			    	  + "<br>"+ msgText
			    	 +html_footer.toString()
			       , true);
		    
		    // 傳送郵件
		    senderImpl.send(mailMessage); 
		   
		    logger.info("郵件傳送成功...");
		    logger.info("mailMessage: {}" + mailMessage);
			return true;
		} catch (Exception e) {
			logger.error("________________sendEmail_Html Exception   ___________________");
			logger.error("sendEmail {}", e.getMessage());
		} finally {
			
		}
		return false;
	}
	
	//Html CSS
	private StringBuffer getHtml_Css(){
		   StringBuffer html_css=new StringBuffer();
		    html_css.append("<html><head><META http-equiv=Content-Type content='text/html; charset=GBK'>")
		      .append("</head>  <body>");
		return html_css;
	}
	
	// Footer  
	private StringBuffer getHtml_Footer(){
	    StringBuffer html_footer=new StringBuffer();
	    html_footer.append("</body></html>");
		return html_footer;
	}

	public JSONObject executeJobGfbDeviceGroup() throws Exception {
		JSONObject jsonObject = new JSONObject();
		// 撈取 tb_lookup_cde, lookup_type=SYSPR,lookup_cde=GFB，取得Google
		// firebase相關參數
		Map<String,Object> queryMapgfb = new HashMap<String,Object>();
		queryMapgfb.put("lookupType", Constants.SYSPR);
		queryMapgfb.put("lookupCde", Constants.GFB);
		List<TbLookupCde> gfbLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMapgfb);
		TbLookupCde gfbLookupCde = gfbLookupCdeList.get(0);
		// 撈取 tb_lookup_cde, lookup_type=SYSPR,lookup_cde=GFBDG，取得Google
		// firebase群發Device Group 相關參數
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("lookupType", Constants.SYSPR);
		queryMap.put("lookupCde", Constants.GFBDG);
		List<TbLookupCde> gfbdgLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMap);
		TbLookupCde gfbdgLookupCde = gfbdgLookupCdeList.get(0);
		// 撈取尚未加入群組的gfb_device_id
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupStatus", "N");
		List<TbGfbDeviceId> tbGfbDeviceIdList = tbGfbDeviceIdManualMapper.selectByExample(map);
		if (tbGfbDeviceIdList.size() > 0) {

			// 將 gfb_device_id 加入 appUser-AllUser 群組
			JSONObject addGroupJsonObject = new JSONObject();
			addGroupJsonObject.put("operation", "add");
			addGroupJsonObject.put("notification_key_name", gfbdgLookupCde.getType1());
			addGroupJsonObject.put("notification_key", gfbdgLookupCde.getType2());
			JSONArray addGroupJsonArray = new JSONArray();
			for (TbGfbDeviceId tbGfbDeviceId : tbGfbDeviceIdList) {
				addGroupJsonArray.put(tbGfbDeviceId.getGfbDeviceId());
			}
			addGroupJsonObject.put("registration_ids", addGroupJsonArray);
			String response = addUserToGroup(addGroupJsonObject);
			JSONObject responseJsonObject = new JSONObject(response);
			if (!responseJsonObject.has("error")) {
				// 加入完畢後 update tb_gfb_device_id
				for (TbGfbDeviceId tbGfbDeviceId : tbGfbDeviceIdList) {
					Map<String,Object> updateMap = new HashMap<String,Object>();
					updateMap.put("gfbDeviceId", tbGfbDeviceId.getGfbDeviceId());
					updateMap.put("groupStatus", "Y");
					tbGfbDeviceIdManualMapper.updateByExampleSelective(updateMap);
				}
			} else {
//				logger.error(responseJsonObject.getString("error"));
				jsonObject.put("err_cde", "01");
				jsonObject.put("err_msg", "error");
				return jsonObject;
			}
		}
		jsonObject.put("err_cde", "00");
		jsonObject.put("err_msg", "");
		return jsonObject;
	}

	private String addUserToGroup(JSONObject addGroupJsonObject) throws IOException {
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.writeBytes(addGroupJsonObject.toString());
		outputStream.flush();
		outputStream.close();
		String response;
		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			InputStream inputStream = connection.getInputStream();
			response = inputstreamToString(inputStream);
			inputStream.close();
		} else {
			InputStream errorStream = connection.getErrorStream();
			response = inputstreamToString(errorStream);
			errorStream.close();
		}
		return response;
	}

	private HttpURLConnection getConnection() throws IOException {
		Map<String,Object> queryMapGfb = new HashMap<String,Object>();
		queryMapGfb.put("lookupType", Constants.SYSPR);
		queryMapGfb.put("lookupCde", Constants.GFB);
		List<TbLookupCde> gfbLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMapGfb);
		TbLookupCde gfbLookupCde = gfbLookupCdeList.get(0);
		Map<String,Object> queryMapGfbdg = new HashMap<String,Object>();
		queryMapGfbdg.put("lookupType", Constants.SYSPR);
		queryMapGfbdg.put("lookupCde", Constants.GFBDG);
		List<TbLookupCde> gfbdgLookupCdeList = tbLookupCdeManualMapper.selectByExample(queryMapGfbdg);
		TbLookupCde gfbdgLookupCde = gfbdgLookupCdeList.get(0);
		URL url = new URL(URL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "key=" + gfbLookupCde.getType2());
		httpURLConnection.setRequestProperty("Content-Type", "application/json");
		httpURLConnection.setRequestProperty("project_id", gfbdgLookupCde.getType3());
		return httpURLConnection;
	}

	private String inputstreamToString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 取得版本資訊
	 * 
	 * @author Nick
	 * @since 2018/12/05
	 * @return
	 */
	public JSONObject getVersion(){
		JSONObject jsonObj = new JSONObject();
		try {
			TbLookupCde cde = commonService.getAESKey();
			if(cde != null) {
				jsonObj.put("DB_available", true);
			}
		} catch (Exception e) {
			jsonObj.put("DB_available", e.getMessage());
		}
		jsonObj.put("version", CwConstants.VERSION);
		jsonObj.put("update_date", CwConstants.UPDATE_VERSION_DATE);
		return jsonObj;
	}

	public Map executePrintIdLog() {
		return pgSsoWtCheckMapper.executeGetLogonUser();
	}

}
