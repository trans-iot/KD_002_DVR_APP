package tw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import tw.com.exception.CustomDataErrorException;
import tw.com.exception.CustomDateOverlapException;
import tw.com.exception.CustomPcException;
import tw.com.exception.CustomValidationException;

public class ExceptionUtils {
	//protected final static Logger logger = Logger.getLogger(ExceptionUtils.class);
	
	//@ComLogger
	//private static Logger logger;

	private ExceptionUtils() {
	}

	public static String parseException(Exception e) {
		String message = "";
        MessageSourceAccessor messages = LocaleMessageAccessor.getMessageSourceAccessor();
		if (e instanceof CannotCreateTransactionException) {
			message = messages.getMessage("sys.error.label")+" : "+messages.getMessage("sys.error.db.msg");
			//logger.error(messages.getMessage("cw.sys.error.label")+" : " + e);
			//logger.error(message);
		}else if(e instanceof TransactionSystemException){
			String msg = e.getMessage();
			if(msg.indexOf("ORA-") != -1){
				message = msg.substring(msg.indexOf("ORA-"));
			}
//			message = messages.getMessage("cw.sys.error.label")+" : "+e.getMessage();

			//logger.error(messages.getMessage("cw.sys.error.label")+" : " + e.getMessage());
			//logger.error("Exception : {}{}", e.getMessage(), e);
		} else if (e instanceof DataIntegrityViolationException) {
			if (e instanceof CustomDateOverlapException) {
				message = e.getMessage();
				//logger.error(messages.getMessage("cw.sys.error.label")+":" + e);
				//logger.error(message);
			} else if(e instanceof CustomDataErrorException){
				message = e.getMessage();
				//logger.error(messages.getMessage("cw.sys.error.label")+":" + e);
				//logger.error(message);
			} else {
				String msg = e.getMessage();
				if(msg.indexOf("ORA-") != -1){
					message = msg.substring(msg.lastIndexOf("ORA-"));
				}
				//message = messages.getMessage("cw.sys.error.label")+" : "+messages.getMessage("cw.sys.error.violation.msg");
				// test
				
				//logger.error(messages.getMessage("cw.sys.error.label")+" : " + e);
				//logger.error(message);
			}
		} else if (e instanceof DataAccessException) {
			if (e instanceof UncategorizedSQLException) {
				message = e.getMessage();
				//logger.error("Exception : {}{}", e.getMessage(), e);
			} else {
				message = e.getMessage();
				//logger.error("Exception : {}{}", e.getMessage(), e);
			}
		} else if (e instanceof CustomPcException) {
			message = e.getMessage();
			//logger.error("Exception : {}{}", e.getMessage(), e);
		} else if (e instanceof CustomValidationException) {
			message = e.getMessage();
			//logger.error( messages.getMessage("cw.check.label")+ messages.getMessage("cw.sys.error.label")+" : " + e);
		} else {
			message += e;
		}

		return message;
	}
	
	/*
	 * Parse ORACLE RAISE_APPLICATION_ERROR message
	 * Return custom error message if raised by RAISE_APPLICATION_ERROR ,otherwise null.
	 * @since 2010/07/16 13:17:50
	 * @author kevin
	 */
	public static String parseCustomAppException(Exception e) {
		String message = null;
		String errormsg = null;
		Pattern pattern = Pattern.compile("((ORA-)|(ora-))[\\d]+(\\W)+");
		Matcher matcher = pattern.matcher(e.getMessage());
		if (matcher.find()){
			message = matcher.group();
		}
		if (message != null){
			String[] msgs	= message.split(":");
			int code = 0;
			try {
				code = Integer.parseInt(msgs[0].replaceAll("[a-zA-a]", ""));
			} catch(Exception ex){
				return null;
			}
			if (code >= -20999 && code <= -20000){
				errormsg = msgs[1];
			}
		}
		return errormsg;
	}

	@SuppressWarnings("unused")
	private static String getMessage(Exception e) {
		Throwable cause = e.getCause();
		return cause.getMessage();
	}
	/**
	 * 
	 * <pre>
	 * Method Name : getErrorMassage
	 * Description : 取得錯誤訊息, e.getMessage為空的話就使用預設錯誤訊息
	 * </pre>
	 * @since 2013/2/21
	 * @author gary 
	 *
	 * @param e
	 * @param defaultErrMsg
	 * @return String
	 */
	public static String getErrorMassage(Throwable e, String defaultErrMsg) {
		
		String errMsg = e.getMessage();

		String returnMsg ="";
		
		if(StringUtils.isEmpty(errMsg)){
			returnMsg = defaultErrMsg;
		}else{
			returnMsg = errMsg.replace("java.lang.Exception:", "").replace("+", "").trim();
		}
		
		return returnMsg;
	}
	/**
	 * 
	 * <pre>
	 * Method Name : getFullStackTrace
	 * Description : 
	 * </pre>
	 * @since 2013/3/20
	 * @author gary 
	 *
	 * @param throwable
	 * @return String
	 */
	public static String getFullStackTrace(Throwable throwable) {
		return org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(throwable);
	}
}
