/**
 *  @since 1.0 
 *  @author : alanlin
 *  @since: Aug 20, 2012
 **/
package tw.msigDvrBack.common;

public class CwConstants {

	private static CwConstants cwConstants;
	private CwConstants() {
		
	}
	
	public static CwConstants getInstance() {
		if (cwConstants==null) {
			cwConstants = new CwConstants();
		}
		return cwConstants;
	}
	//版號
	public final static String VERSION = "202106251200";
	//上版時間
	public final static String UPDATE_VERSION_DATE = "2021/06/25 12:00";
		
	public final static String BO = "billObject";
	public final static String SD = "sessionData";
	public final static String BUS_CNTR_ID = "busCntrId";
	public final static String QUERY_ERR_PAGE = "system/queryError";
	public final static String GO_BACK_URL = "goBackUrl";
	public final static String AJAX_OK = "OK";
	public final static String AJAX_FAIL = "FAIL";
	
	public final static String USER_ID = "userId";
	public final static String USER_NAME = "userName";
	public final static String ROLE_ID = "roleId";
	public final static String ROLE_LIST = "roleList";
	
	public final static String ROLE_ADMIN = "spas.admin";
	
	public final static String DEFAULT_PASSWORD = "123456";
	
	public final static String PO_ERR_CDE = "poErrCde";
	
	public final static String PO_ERR_MSG = "poErrMsg";
	
	public final static String SEPARATOR = "/";
	
	public final static String SEPARATOR01 = "\\";
	
	public final static String EMPTYSTRING = "";

	public final static String TRUST_VALID = "[0-9a-zA-Z_]+";
	
	
	

	/**
	 * 工單種類
	 *  01:新合約申請
	 *  02:取消交易
	 *  03:增減服務項目
	 *  04:暫停話
	 *  05:復話
	 *  06:退租
	 *  07:變更計費方式
	 *  10:購買商品
	 */
	public final static String WO_TYPE_01 = "01";
	public final static String WO_TYPE_02 = "02";
	public final static String WO_TYPE_03 = "03";
	public final static String WO_TYPE_04 = "04";
	public final static String WO_TYPE_05 = "05";
	public final static String WO_TYPE_06 = "06";
	public final static String WO_TYPE_07 = "07";
	public final static String WO_TYPE_10 = "10";
	
	public final static String SPLIT_CHAR = "㊣"; //特殊符號
	public final static String ARRAY_CHAR = "★"; //特殊符號
	
	public static class Action {
		public final static String INSERT = "insert";
		public final static String UPDATE = "update";
		public final static String DETAIL = "detail";
	}

	public String splitChar = SPLIT_CHAR;
	public String arrayChar = ARRAY_CHAR;
	public String getSplitChar() {
		return splitChar;
	}
	public void setSplitChar(String splitChar) {
		this.splitChar = splitChar;
	}
	public String getArrayChar() {
		return arrayChar;
	}
	public void setArrayChar(String arrayChar) {
		this.arrayChar = arrayChar;
	}
}
