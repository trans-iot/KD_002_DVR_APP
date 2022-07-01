package tw.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import tw.msigDvrBack.common.CommonService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.spring.ComLogger;

@Component
public class EmailUtil {

	@ComLogger
	private Logger logger;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeMapper;

	/**
	 * 發送驗證信或簡訊
	 * 
	 * @param account
	 * @param po_VERIFY_URL
	 * @param po_VERIFY_CODE
	 * @return
	 */
	public boolean sendCheckMessage(Map<String, String> emailSettingMp, Map<String, String> mailContent) {
		try {
			String header = mailContent.get("header");
			String subject = mailContent.get("subject");
			String content = mailContent.get("content");
			String account = mailContent.get("mailTo");
			if (sendEmail_Html(emailSettingMp, account, content, subject, header)) {
				logger.info("email : 發送" + account + "  成功");
				return true;
			} else {
				logger.info("email : 發送" + account + "  失敗");
				return false;
			}
		} catch (Exception e) {
			logger.error("snedMessage {}", e.getMessage());
		}
		return false;
	}

	/**
	 * 取得並設定email相關設定
	 */
	public Map<String, String> checkActAdnPwd() {
		Map<String, String> emailSettingMp = new HashMap<String, String>();
		logger.debug("____________checkActAdnPwd_IN_______________");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lookupType", Constants.SYSPR);
		paramMap.put("lookupCde", Constants.SMTPIP);
		TbLookupCde lookup = (TbLookupCde) tbLookupCdeMapper.selectByExample(paramMap).get(0);
		emailSettingMp.put("emailHost", lookup.getDscr());
		emailSettingMp.put("emailAccount", lookup.getType1());
		emailSettingMp.put("emailPsd", lookup.getType2());
		emailSettingMp.put("emailType", lookup.getType3());
		
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("lookupType", "SYSPR");
		paramMap2.put("lookupCde", "FROMEMAIL");
		TbLookupCde lookup2 = (TbLookupCde) tbLookupCdeMapper.selectByExample(paramMap2).get(0);
		emailSettingMp.put("fromEmail", lookup2.getType1());

		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("lookupType", "SYSPR");
		paramMap3.put("lookupCde", "SMTPPR");
		TbLookupCde lookup3 = (TbLookupCde) tbLookupCdeMapper.selectByExample(paramMap3).get(0);
		if (lookup3 != null) {
			emailSettingMp.put("startTls", lookup3.getType1());
		}
		return emailSettingMp;
	}

	/**
	 * @param address 收件者信箱
	 * @param content 信件內容
	 * @param subjust 信件標題
	 * @param header  服務項目
	 * @return
	 */
	private boolean sendEmail_Html(Map<String, String> emailSettingMp, String address, String content, String subjust, String header) {
		logger.debug("________________sendEmail_IN___________________");
		Transport transport = null;
		try {
			String startTls = emailSettingMp.containsKey("startTls") ? (String) emailSettingMp.get("startTls") : "";
			String emailHost = emailSettingMp.containsKey("emailHost") ? (String) emailSettingMp.get("emailHost") : "";
			String emailAccount = emailSettingMp.containsKey("emailAccount") ? (String) emailSettingMp.get("emailAccount") : "";
			String emailPsd = emailSettingMp.containsKey("emailPsd") ? (String) emailSettingMp.get("emailPsd") : "";
			String emailType = emailSettingMp.containsKey("emailType") ? (String) emailSettingMp.get("emailType") : "";
			String fromEmail = emailSettingMp.containsKey("fromEmail") ? (String) emailSettingMp.get("fromEmail") : "";
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

			Properties props = new Properties();
			
			if ("N".equals(startTls)) {
				props.put("mail.smtp.starttls.enable", "false");
			} else {
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			}
			
			// 設定 Mail Server 有帳號密碼才需身分驗證
			if(StringUtils.isNotBlank(emailAccount)) {
				props.put("mail.smtp.auth", "true");
			}
			
			props.put("mail.smtp.host", emailHost);
			props.put("mail.transport.protocol", "smtp");
			// config.emailHost "smtp.gmail.com"
			// props.put("mail.smtp.port", "587"); Gmail Port
			props.put("mail.smtp.port", "25");
			senderImpl.setJavaMailProperties(props);
			logger.debug("________________sendProperties_END___________________");

			// 設定 Mail Server 有帳號密碼才做設定
			if(StringUtils.isNotBlank(emailAccount)) {
				// config.emailAccount
				senderImpl.setUsername(emailAccount);
				// config.emailPassword
				senderImpl.setPassword(emailPsd);
			}

			logger.debug("________________sendEmail_Html___________________");
			logger.debug("startTls = " + startTls);
			logger.debug("emailHost = " + emailHost);
			logger.debug("emailAccount = " + emailAccount);
			logger.debug("emailType = " + emailType);

			if ("AWS".equals(emailType)) {
				logger.debug("_____AWS____IN________");
				try {
					StringBuffer html_css = getHtml_Css();
					StringBuffer html_header = getHtml_Header(header);
					StringBuffer html_content = getHtml_Content(content);
					String content2 = html_css.toString() + html_header.toString() + html_content.toString();

					Session session = Session.getDefaultInstance(props);
					MimeMessage msg = new MimeMessage(session);

					msg.setFrom(new InternetAddress(fromEmail, fromEmail));
					logger.debug("setFrom = " + fromEmail);
					msg.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
					logger.debug("setTo = " + address);
					msg.setSubject(subjust, "utf-8");
					logger.debug("setSubject = " + subjust);
					msg.setContent(content2, "text/html; charset=UTF-8");
					logger.debug("setText = " + content2);

					// Create a transport.
					transport = session.getTransport();
					// Connect to Amazon SES using the SMTP username and
					// password you specified above.
					transport.connect(emailHost, emailAccount, emailPsd);

					// Send the email.
					transport.sendMessage(msg, msg.getAllRecipients());
					logger.debug("郵件傳送成功...");
					logger.debug("_________________________________________________________");
					return true;
				} catch (Exception e) {

					logger.error("________________sendEmail_Html Exception   ___________________");
					logger.error("_______________________________________________________________");
					logger.error("sendEmail {}", e.getMessage());

				} 
			} else {

				// 建立郵件訊息
				MimeMessage mailMessage = senderImpl.createMimeMessage();
				logger.debug("createMimeMessage = " + mailMessage);
				// 未加 GBK 會產生亂碼
				MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "GBK");
				;
				logger.debug("messageHelper = " + messageHelper);

				// 設定收件人、寄件人、主題與內文
				messageHelper.setTo(address);
				logger.debug("setTo = " + address);
				// address
				messageHelper.setFrom(fromEmail);
				logger.debug("setFrom = " + fromEmail);
				// config.emailAccount
				messageHelper.setSubject(subjust);
				logger.debug("setSubject = " + subjust);

				StringBuffer html_css = getHtml_Css();
//				StringBuffer html_headerimg = getHtml_Header_imgurl();
				StringBuffer html_header = getHtml_Header(header);
				StringBuffer html_content = getHtml_Content(content);
				StringBuffer html_hr = getHtml_Hr();
				StringBuffer html_footer = getHtml_Footer();
				messageHelper.setText(html_css.toString()
						// +html_headerimg.toString()
						+ html_header.toString() + html_content.toString()
				// +html_hr.toString()
				// +html_footer.toString()
						, true);
				logger.debug("setText = " + html_css.toString() + html_header.toString() + html_content.toString());
				// 傳送郵件
				senderImpl.send(mailMessage);

				logger.debug("郵件傳送成功...");
				logger.debug("_________________________________________________________");
				logger.debug(html_css.toString() + html_header.toString() + html_content.toString() + html_hr.toString()
						+ html_footer.toString());
				logger.debug("_________________________________________________________");
				return true;
			}
		} catch (Exception e) {
			logger.error("________________sendEmail_Html Exception   ___________________");
			logger.error("_______________________________________________________________");
			logger.error("sendEmail {}", e.getMessage());
		} finally {
			try {
				if (transport != null) transport.close();
			} catch(Exception e) {
				logger.error("close transport error => ", e.getMessage());
			}
		}
		return false;
	}

	// Html 內容
	private StringBuffer getHtml_Content(String content) {
		StringBuffer html_content = new StringBuffer();
		html_content.append("<div class=\"content\"  align=\"left\"> ").append("<P>")
				.append("<font style=\"color:#666666; word-wrap: break-word; font-size: 12pt; \" >")
				.append(content.toString()).append("</font>").append("</P>").append("<div>").append("</div>");
		return html_content;
	}

	// Footer 上的水平線
	private StringBuffer getHtml_Hr() {
		StringBuffer html_hr = new StringBuffer();
		html_hr.append("<hr size=\"1\" align=\"center\"  width=\"90%\" color=\"#9D9D9D\">");
		return html_hr;
	}

	// Footer
	private StringBuffer getHtml_Footer() {
		StringBuffer html_footer = new StringBuffer();
		html_footer.append("<div class=\"footer\"  align=\"center\"> ").append("&bull; ").append(
				"<a href= \"http://www.trans-iot.com/\" style=\"text-decoration:none;color:#cccc00 ;\">創星物聯</a>")
				.append("&bull; ")
				.append("<a href= \"mailto:service@trans-iot.com\" style=\"text-decoration:none;color:#cccc00 ;\">支援</a>")
				.append("<p>").append("TRANS-IOT TECHNOLOGY CO., LTD.  +886-2-55691688 ").append("<br>")
				.append(" 3F, 32, Jihu Rd.,, Taipei City, Taipei, 11492   Taiwan").append("<br>").append("</p>")
				.append("</div>").append("</body></html>");
		return html_footer;
	}

	// Html CSS
	private StringBuffer getHtml_Css() {
		StringBuffer html_css = new StringBuffer();
		html_css.append("<html><head><META http-equiv=Content-Type content='text/html; charset=GBK'> <style>")
				.append("  body {margin-top: 0;}").append(" .main {width: 100%;height: auto;}")
				.append(" .header {color: #cccc00; height: auto; word-wrap: break-word; font-size: 18pt;}")
				.append(" .content {color: #666666; word-wrap: break-word; font-size: 12pt;}")
				.append(" .footer{color: #cccc00; height: auto; word-wrap: break-word; font-size: 9pt; text-decoration:none;} .footer p{color: #666666;}")
				.append(" </style> </head>  <body>");
		return html_css;
	}

	// Html 標題
	private StringBuffer getHtml_Header(String header) {
		StringBuffer html_header = new StringBuffer();
		html_header.append("<div class=\"header\"  align=\"left\"> ").append("<P>")
				.append("<font style=\"color:#cccc00;  word-wrap: break-word; font-size: 18pt; \" >")
				.append(header.toString()).append("</font>").append("</P>").append("</div>");
		return html_header;
	}
	
	/**
	 * @description 取得會員資料新增帳號的發送信件內容
	 * @method getCreateAccountMailContent
	 * <pre>
	 * 
	 * @param 
	 * @author Marks
	 * @since 2020/07/06
	 * @return Map<String, String>
	 */
	public Map<String, String> getCreateAccountMailContent(String account, String pwd) {
		String hrefUrl = commonService.getApiUrl()+"/TiotDvrWeb/web/login.html";
		String subject = "明台車聯御守「安全駕駛聚樂部」會員-註冊成功通知";
		String header = "";
		StringBuffer content = new StringBuffer();
		content.append("親愛的會員您好，").append("<br/>")
			   .append("歡迎您成為明台產險車聯御守「安全駕駛聚樂部」之會員，") .append("<br/>")
			   .append("即日起您可享有明台車聯御守安全駕駛聚樂部所提供的各項會員專屬服務。").append("<br/>")
			   .append("<br/>")
			   .append("<br/>")
			   .append("請透過以下資訊及連結開啟您的專屬服務：").append("<br/>")
			   .append("服務連結：")
			   .append( "<A Target=\"_new\" Href=\""+hrefUrl+"\"").append(">"+hrefUrl+"</A>").append("<br/>")
			   .append("會員帳號：").append(account).append("<br/>")
			   .append("預設密碼：").append(pwd).append("<br/>")
			   .append("<br/>")
			   .append("★提醒您，本信件為系統自動寄發，請勿直接回覆，如有任何問題，歡迎撥打本公司服務專線：0800-").append("<br/>")
			   .append("528-528(週一至週五上午8:30至下午5:30，例假日及國定假日除外)，感謝您!★");
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("subject", subject);
		resultMap.put("header", header);
		resultMap.put("content", content.toString());
		resultMap.put("mailTo", account);
		return resultMap;
	}
	
	
	/**
	 * @description 取得長期未使用的DVR信件內容
	 * @method getCreateAccountMailContent
	 * <pre>
	 * 
	 * @param 
	 * @author Marks
	 * @since 2020/07/06
	 * @return Map<String, String>
	 */
	public Map<String, String> getDeviceUnusedMailContent(String account, Integer unusedDays) {
		String hrefUrl = commonService.getApiUrl()+"/TiotDvrWeb/web/login.html";
		String subject = "明台車聯御守「安全駕駛聚樂部」會員-DVR使用提醒";
		String header = "";
		StringBuffer content = new StringBuffer();
		content.append("親愛的會員您好，").append("<br/>")
			   .append("歡迎您安裝使用明台車聯御守DVR，成為明台產險「安全駕駛聚樂部」之會員，") .append("<br/>")
			   .append("提醒您已超過").append(unusedDays).append("天未使用DVR相關服務，").append("<br/>")
			   .append("若有任何問題，歡迎透過以下連結：").append("<br/>")
			   .append( "<A Target=\"_new\" Href=\""+hrefUrl+"\"").append(">"+hrefUrl+"</A>").append("<br/>")
			   .append("或明台官網 https://www.msig-mingtai.com.tw/").append("<br/>")
			   .append("或與客服中心0800-528-528聯絡").append("<br/>")
			   .append("明台車聯御守，用心守護您的行車安全").append("<br/>")
			   .append("<br/>")
			   .append("★提醒您，本信件為系統自動寄發，請勿直接回覆，如有任何問題，歡迎撥打本公司服務專線：").append("<br/>")
			   .append("0800-528-528(週一至週五上午8:30至下午5:30，例假日及國定假日除外)，感謝您!★");
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("subject", subject);
		resultMap.put("header", header);
		resultMap.put("content", content.toString());
		resultMap.put("mailTo", account);
		return resultMap;
	}
	
	
	/**
	 * @description 取得每月提供會員駕駛行為分析報告通知信內容
	 * @method getDriveReportMailContent
	 * <pre>
	 * 
	 * @param 
	 * @author Marks
	 * @since 2020/07/06
	 * @return Map<String, String>
	 */
	public Map<String, String> getDriveReportMailContent(String account, String href) {
		String subject = "明台車聯御守行車安全聚樂部會員-駕駛行為分析報告通知";
		String header = "";
		StringBuffer content = new StringBuffer();
		content.append("親愛的會員您好").append("<br/>")
			   .append("運用大數據分析，量身打造您專屬的駕駛行為分析報告").append("<br/>")
			   .append("提供您行車安全駕駛提醒，作為調整行車方式或駕駛習慣之參考").append("<br/>")
			   .append("明台車聯御守，用心守護您的行車安全").append("<br/>")
			   .append("查看您的駕駛行為分析報告請連結：").append("<br/>")
			   .append( "<A Target=\"_new\" Href=\""+href+"\"").append(">"+href+"</A>").append("<br/>")
			   .append("<br/>")
			   .append("★提醒您，本信件為系統自動寄發，請勿直接回覆，如有任何問題，歡迎撥打本公司服務專線：").append("<br/>")
			   .append("0800-528-528(週一至週五上午8:30至下午5:30，例假日及國定假日除外)，感謝您!★");
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("subject", subject);
		resultMap.put("header", header);
		resultMap.put("content", content.toString());
		resultMap.put("mailTo", account);
		return resultMap;
	}
}
