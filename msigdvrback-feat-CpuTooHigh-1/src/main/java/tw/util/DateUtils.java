package tw.util;

import java.text.*;
import java.util.*;

import org.apache.commons.lang.*;


/**
 * 
 * @author Paul
 * @since 2008/9/8 下午 3:54:34
 */
public class DateUtils {
	
	public static final String DATE_PATTERN = "yyyy/MM/dd";
	
	private DateUtils() {
	
	}
	/**
	 * 
	 * @return
	 */
	public static Date today() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date ROC2Date(String date) throws ParseException {
		String sDate = ROC2DateString(date);
		if (sDate == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);		
		return sdf.parse(sDate);
	}
	
	
	/**
	 * 民國年轉日期 (pattern 的年 "yyyy" 必須為4個 y )
	 * @param rocDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	public static Date ROC2Date(String rocDate,String pattern)throws ParseException{
		if(rocDate == null || pattern == null ){
			throw new ParseException( "Date Format Error !", 0 );
		}		
		if(rocDate.length() == pattern.length() ){
			return formatDate( rocDate, pattern);
		}else{
			String yPattern = "yyyy";
			int yearGap = pattern.length() - rocDate.length();
			String newYearPattern = yPattern.substring(yearGap);
			int[] yearRange = getYearStringRange(pattern);
			if(yearRange[1]-yearRange[0] != 4){
				throw new ParseException("Pattern y 必須有四個",0);				
			}
			String newPattern = pattern.replaceAll(pattern.substring(yearRange[0],yearRange[1]), newYearPattern);
			return formatDate( rocDate, newPattern);
		}
		
	}
	
	/**
	 * 民國年轉日期共用程式
	 * 20141227 修改為西元年
	 * @param rocDate
	 * @param newPattern
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	private static Date formatDate(String rocDate,String newPattern)throws ParseException{
		Date date = null;
		StringBuffer rocSb = new StringBuffer( rocDate );
		StringBuffer patternSb = new StringBuffer( newPattern );
		int[] yearRange = getYearStringRange(newPattern);
		if(!"".equals(rocDate.substring(yearRange[0],yearRange[1]))){
			int centeryYear = Integer.parseInt( rocDate.substring(yearRange[0],yearRange[1]) );
			rocDate = rocSb.replace(yearRange[0],yearRange[1], String.valueOf(centeryYear)).toString();
			newPattern = patternSb.replace(yearRange[0],yearRange[1], "yyyy" ).toString();
			SimpleDateFormat sdf = new SimpleDateFormat(newPattern);
			date = sdf.parse(rocDate);
		}
		return date;
	}
	
	/**
	 * 日期轉民國年
	 * @param centuryDateString
	 * @param inputPattern
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	public static String Date2ROC(String centuryDateString,String inputPattern) throws ParseException {
		return Date2ROC(centuryDateString,inputPattern,inputPattern);
	}
	
	/**
	 * 日期轉民國年
	 * @param centuryDateString
	 * @param inputPattern
	 * @param outputString
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	public static String Date2ROC(String centuryDateString,String inputPattern,String outputString) throws ParseException {
//		MessageSourceAccessor msa = LocaleMessageAccessor.getMessageSourceAccessor();
//		msa.getMessage( "cw.date.format.error" )
		if (centuryDateString == null || inputPattern == null ){
			throw new ParseException( "日期轉民國年發生錯誤", 0 );
		}		
		SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);		
		Date centuryDate = sdf.parse(centuryDateString);		
		return Date2ROC( centuryDate, outputString);		
	}
	
	/**
	 * 日期轉 ROC DATE 字串
	 * @param centuryDate
	 * @param outputString
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	public static String Date2ROC(Date centuryDate,String outputString) throws ParseException {
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		String centuryYear = sdfYear.format(centuryDate);
		int rocYear = Integer.parseInt(centuryYear) - 1911;		
		int[] yearRange = getYearStringRange(outputString);
		outputString = outputString.replaceAll(outputString.substring(yearRange[0],yearRange[1]), String.valueOf(rocYear));
		SimpleDateFormat sdf2 = new SimpleDateFormat(outputString);
		String rocDateString = sdf2.format(centuryDate);		
		return rocDateString;
	}

	/**
	 * 取得 pattern 位置
	 * @param datePattern
	 * @return
	 * @version Derek 2008/09/09
	 */
	private static int[] getYearStringRange(String datePattern){
		int[] range = new int[2];
		if(datePattern.indexOf("y")!=-1){			
			int start = datePattern.indexOf("y");
			int end = datePattern.lastIndexOf("y")+1;
			range[0] = start;
			range[1] = end;
			return range;
		}else{
			range[0] = 0;
			range[1] = 0;
			return range;
		}
	}
	
	/**
	 * 民國轉西元字串
	 * @param date
	 * @param inputPattern
	 * @param outputPattern
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	public static String ROC2DateString(String date ,String inputPattern ,String outputPattern) throws ParseException {
		if (date == null || "".equals(date) || inputPattern == null )
			return null;
		Date centuryDate = ROC2Date(date,inputPattern);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		if(outputPattern != null || !"".equals(outputPattern)){
			sdf =  new SimpleDateFormat(outputPattern);
		}
		return sdf.format(centuryDate);
	}
	
	/**
	 * 民國轉西元字串 預設以 yyyy/MM/dd
	 * @param date
	 * @return
	 * @throws ParseException
	 * 
	 */
	public static String ROC2DateString(String date ,String pattern) throws ParseException {
		if (date == null || "".equals(date))
			return null;		
		return ROC2DateString(date , pattern,pattern);
	}
	
	/**
	 * 民國轉西元字串 預設以 yyyy/MM/dd
	 * @param date
	 * @return
	 * @throws ParseException
	 * 
	 */
	public static String ROC2DateString(String date) throws ParseException {
		if (date == null || "".equals(date))
			return null;		
		return ROC2DateString(date , DATE_PATTERN,DATE_PATTERN);
	}
	/**
	 * 
	 * Description : 
	 * @since 2014/12/25
	 * @author Gary 
	 *
	 * @param s
	 * @param pattern
	 * @return
	 * @throws ParseException Date
	 */
	public static Date parseDate(String s, String pattern) throws ParseException {
		if (StringUtils.isEmpty(s)) return null;
		return org.apache.commons.lang.time.DateUtils.parseDate(s, new String[]{pattern});
	}
}
