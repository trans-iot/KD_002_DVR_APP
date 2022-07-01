package tw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author Paul
 * @since 2008/9/8 下午 3:54:34
 */
public class RocDateUtils {
	
	public static final String DATE_PATTERN = "yyyy/MM/dd";
	
	private RocDateUtils() {
	
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
			return formatROCDate( rocDate, pattern);
		}else{
			String yPattern = "yyyy";
			int yearGap = pattern.length() - rocDate.length();
			String newYearPattern = yPattern.substring(yearGap);
			int[] yearRange = getYearStringRange(pattern);
			if(yearRange[1]-yearRange[0] != 4){
				throw new ParseException("Pattern y 必須有四個",0);				
			}
			String newPattern = pattern.replaceAll(pattern.substring(yearRange[0],yearRange[1]), newYearPattern);
			return formatROCDate( rocDate, newPattern);
		}
		
	}
	
	/**
	 * 民國年轉日期共用程式
	 * @param rocDate
	 * @param newPattern
	 * @return
	 * @throws ParseException
	 * @version Derek 2008/09/09
	 */
	private static Date formatROCDate(String rocDate,String newPattern)throws ParseException{
		Date date = null;
		StringBuffer rocSb = new StringBuffer( rocDate );
		StringBuffer patternSb = new StringBuffer( newPattern );
		int[] yearRange = getYearStringRange(newPattern);
		if(!"".equals(rocDate.substring(yearRange[0],yearRange[1]))){
			int centeryYear = Integer.parseInt( rocDate.substring(yearRange[0],yearRange[1]) )+1911;
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
	public static String Date2ROC(Date centuryDate,String outputString){
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
	
	
	public static String dateToString(Date date) {
		if (date==null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		return sdf.format(date);
	}
	
		
}