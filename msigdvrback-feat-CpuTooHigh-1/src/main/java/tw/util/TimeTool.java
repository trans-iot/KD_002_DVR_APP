/*
 * SeparatePage.java
 *
 * Created on 2003年11月9日, 下午 8:20
 */

package tw.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * Time tag
 * @author  Derek
 */
public class TimeTool extends TagSupport { 
    
    private String fmtType;
    private String inputType = "";
    private String inputTime = "";
    private boolean changeToLocal = false;
    private boolean defaultValue = true;
    private Date time ;
    
    
    public void setFmtType(String fmtType){
        this.fmtType = fmtType;
    }
    public void setInputType(String inputType){
        this.inputType = inputType;
    }
    public void setInputTime(String inputTime){
        this.inputTime = inputTime;
    }
    
    public void setDefaultValue(Boolean defaultValue){
        this.defaultValue = defaultValue.booleanValue();
    }
    
    public void setTime(Date time) {
		this.time = time;
	}
    
	/**
     * 設定為 tw 時間
     * @param changeToTw boolean
     */    
    public void setChangeToLocal(Boolean changeToLocal){
        this.changeToLocal = changeToLocal.booleanValue();
    }
    
    /**
     * tag 主要方法
     * @throws JspException 例外
     * @return SKIP_BODY
     */    
    public int doStartTag() throws JspException{
        try{
        	
        	if (defaultValue) {
        		if(changeToLocal){      
        			if(time == null ){
        				if(!inputType.equals("") && !inputTime.equals("") ){
            				printStringROCTime();
            			}else{            				           				
    						printDefaultROCTime(new Date());
            			}                  				
        			}else{
        				printROCTime();
        			}        			      	
                }else{
                	if(time == null ){
        				if(!inputType.equals("") && !inputTime.equals("") ){
        					printCustomTime();
            			}else{
            				printDefaultROCTime(new Date());
            			}                  				
        			}else{
        				printNormalTime();
        			}        	
                }        		
			} else {
				if(changeToLocal){      
        			if(time == null ){
        				if(!inputType.equals("") && !inputTime.equals("") ){
            				printStringROCTime();
            			}else{
            				pageContext.getOut().write("");
            			}                  				
        			}else{
        				printROCTime();
        			}        			      	
                }else{
                	if(time == null ){
        				if(!inputType.equals("") && !inputTime.equals("") ){
        					printCustomTime();
            			}else{
            				pageContext.getOut().write("");
            			}                  				
        			}else{
        				printNormalTime();
        			}        	
                }        		
			}
        	//for input String
//            if(!inputType.equals("") && !inputTime.equals("")){
//                if(changeToLocal){                    
//                	printStringROCTime();
//                }else{
//                    try{
//                        printCustomTime();
//                    }catch(Exception e){
//                        //if exception happend , use default
//                        printTime();
//                    }
//                }
//            } else {
//				if (defaultValue) {
//					if (changeToLocal) {
//						this.time = new Date();
//						printROCTime();
//					} else {
//						printTime();
//					}
//				} else {
//					pageContext.getOut().write("");
//				}
//			}
        }catch(IOException ioe){
            throw new JspTagException(ioe.getMessage());
        }catch(Exception e){
//            System.out.println(e);
        }
        return SKIP_BODY;         //忽略 body content
    }
    
    
    /**
     * 印出自訂時間並format (用傳入之日期)
     * @throws Exception 例外
     */    
    public void printNormalTime()throws Exception{       
        SimpleDateFormat sdf2=new SimpleDateFormat(fmtType);
        String str = sdf2.format(this.time);
        pageContext.getOut().write( str );
    }
    
    /**
     * 印出自訂時間並format
     * @throws Exception 例外
     */    
    public void printCustomTime()throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat(inputType);
        Date date = sdf.parse(inputTime);
        SimpleDateFormat sdf2=new SimpleDateFormat(fmtType);
        String str = sdf2.format(date);
        pageContext.getOut().write( str );
    }
    
    /**
     * 由 String 轉印出自訂時間(西元轉民國)並format
     * @throws Exception 例外
     */    
    public void printStringROCTime()throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat(inputType);
        Date date = sdf.parse(inputTime);
        pageContext.getOut().write( getRocFormatDateString(date,fmtType) );
    }
    
    /**
     * 由 date 轉印出自訂時間(西元轉民國)並format
     * @throws Exception 例外
     */    
    public void printROCTime()throws Exception{    	
        pageContext.getOut().write( getRocFormatDateString(time,fmtType) );
    }
    
    
    
    /**
     * 由 date 轉印出自訂時間(西元轉民國)並format 列印預設的時間
     * @param date
     * @throws Exception
     */
    public void printDefaultROCTime(Date date)throws Exception{    	
        pageContext.getOut().write( getRocFormatDateString(date,fmtType) );
    }
    /**
     * get 西元轉民國 字串
     * @param date
     * @param fmtType
     * @return
     */
    public String getRocFormatDateString(Date date,String fmtType){
		SimpleDateFormat ySdf = new SimpleDateFormat("yyyy");
		String ySdfString = ySdf.format(date);
		int yCenturyInt = Integer.valueOf( ySdfString );
		String yRocString = String.valueOf(yCenturyInt-1911);	
		SimpleDateFormat daySdf = new SimpleDateFormat(fmtType);
		String dateString = daySdf.format(date);
		int yStart = fmtType.indexOf('y');
		int yEnd = fmtType.lastIndexOf('y');
		dateString = dateString.substring(0, yStart)+yRocString+dateString.substring(yEnd+1, dateString.length());
		return dateString;
	}
    
    
    /**
     * 印出系統時間並format
     * @throws Exception 例外
     */    
    public void printTime()throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fmtType);
        String str = sdf.format(date);
        pageContext.getOut().write( str );
    }
    
}





