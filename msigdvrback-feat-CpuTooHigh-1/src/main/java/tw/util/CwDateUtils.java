/**
 *  @since 1.0 
 *  @author : alanlin
 *  @since: Aug 10, 2012
 *  @lastmodfiy 2012/08/28 Harry Chen
 **/
package tw.util;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class CwDateUtils {

	public final static String[] DATE_PATTERN = {"yyyy/MM/dd"};
	
	public final static String[] DATETIME_PATTERN = {"yyyy/MM/dd HH:mm:ss"};
	/**
	 * 轉換 mm/dd/yyyy 格式字串 變成 Date 物件
	 * 如果格式出錯，回傳 Null
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatDate(String strDate) {
		Date d = null;
		try {
			d = DateUtils.parseDate(strDate, DATE_PATTERN);
		}
		catch (Exception e) {}
		
		return d;
	}
	
	/**
	 * 轉換 mm/dd/yyyy hh:MM:ss 格式字串 變成 Date 物件
	 * 如果格式出錯，回傳 Null
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatDateTime(String strDate) {
		Date d = null;
		try {
			d = DateUtils.parseDate(strDate, DATETIME_PATTERN);
		}
		catch (Exception e) {}
		
		return d;
	}
	
	/**
	 * 轉換Date物件變成   mm/dd/yyyy 格式字串 
	 * 如果轉換出錯，回傳 Null
	 * 
	 * @param date
	 * @return String
	 */
	public static String transferDate(Date date) {
		String s = null;
		try {
			s = DateFormatUtils.format(date, DATE_PATTERN[0]);
		}
		catch (Exception e) {}

		return s;
	}
	
	/**
	 * 轉換Date物件變成   mm/dd/yyyy hh:MM:ss 格式字串 
	 * 如果轉換出錯，回傳 Null
	 * 
	 * @param date
	 * @return String
	 */
	public static String transferDatetime(Date date) {
		String s = null;
		try {
			s = DateFormatUtils.format(date, DATETIME_PATTERN[0]);
		}
		catch (Exception e) {}

		return s;
	}
	
	/**
	 * 取得sysdate 格式: yyyy/MM/dd
	 * @return String
	 */
	public static String getToday() {
		Date d = new Date();
		return transferDate(d);
	}
	
	/**
	 * 取得sysdate 格式: yyyy/MM/dd hh:mm:ss
	 * @return String
	 */
	public static String getTodaytime() {
		Date d = new Date();
		return transferDatetime(d);
	}
}
