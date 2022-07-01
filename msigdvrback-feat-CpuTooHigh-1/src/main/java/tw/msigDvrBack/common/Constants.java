/**
 *  @since 1.0 
 **/
package tw.msigDvrBack.common;

public class Constants {

	private static Constants constants;

	private Constants() {

	}

	public static Constants getInstance() {
		if (constants == null) {
			constants = new Constants();
		}
		return constants;
	}

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

	public final static String DEPTARTMENT = "DEPT";
	public final static String PAGE= "PAGETY";
	public final static String APPLY_STATUS= "APPLYSTA";
	public final static String DETYP= "DETYP";
	public final static String DESTA= "DESTA";
	public final static String DESKEY= "3DESKEY";
	public final static String AESKEY= "AESKEY";
	public final static String TIOTUBI= "TIOTUBI";
	public final static String TIOTDVR= "TIOTDVR";
	public final static String WEBURL= "WEBURL";
	public final static String MSGCLASS= "MSGCLASS";
	public final static String PUSHSTA= "PUSHSTA";
	public final static String SYSPR= "SYSPR";
	public final static String SMTPIP= "SMTPIP";
	public final static String GFB= "GFB";
	public final static String GFBDG= "GFBDG";
	public final static String USER_STATUS = "USRTA";
	public final static String CAR_BRAND = "CABRD";
	public final static String UNUSEDDAYS = "UNUSEDDAYS";
	

	public final static String POI_PROVIDE = "POIPROVIDE";
	public final static String G_POI_TYPE = "GPOITYPE";
	public final static String T_POI_TYPE = "POITYPE";
	public final static String POI_STATUS = "POISTATUS";
	public final static String WS_URL = "URL";

	public final static int RANDON_CNT = 9999999;

	public final static String SPLIT_CHAR = "㊣"; // 特殊符號
	public final static String ARRAY_CHAR = "★"; // 特殊符號

	public final static String WO_TYPE = "WOTYPE"; // 工單種類下拉選單
	public final static String WO_STATUS = "WOSTATUS"; // 工單狀態下拉選單
	
	
	public final static String SYS_ID = "SYSID"; // 系統代碼下拉選單
	public final static String BILLING_SYS = "BILLINGSYS";  //BILLING 業者提供下拉選單,
	public final static String US_RTA = "USRTA";  // CUST_STATUS  客戶狀態下拉選單,	
	public final static String CU_STA = "CUSTA";  // CUST_STATUS  客戶狀態下拉選單,	
	public final static String OPENID_TY = "OPENIDTY";  // OPENIDTY    penID種類選單,	
	
	public final static String LAST_SECOND = " 23:59:59";  // OPENIDTY    penID種類選單,	
	public final static String FIRST_SECOND = " 00:00:00";  // OPENIDTY    penID種類選單,	
	
	public final static String LOOKUP_SERVICETY = "SERVICETY";  // SERVICETY    服務種類,	
	
	public final static String PWD_STATUS_FIRST = "FIRST";  // 密碼狀態 等待首次登入,	
	
	public final static String DEVICE_TYPE_DVR = "DVR"; //設備類型 DVR
	public final static String DEVICE_STATUS_BINDING = "BINDING";  // 設備狀態 綁定,	
	public final static String DEVICE_STATUS_REMOVE = "REMOVE";  // 設備狀態  解除綁定,	
	
	public final static String STATUS_00 = "00";  //00,	

	
	public final static String STR_ASCII = "ASCII";
	public final static String STR_AES = "AES";
	public final static String STR_FILPH = "FILPH";
	public final static String STR_PICPH = "PICPH";
	public final static String STR_PAGE = "PAGE";
	public final static String STR_SYNCIMAGE = "SYNCIMAGE";
	public final static String STR_APPPR = "APPPR";
	public final static String STR_CAREDAY = "CAREDAY";
	public final static String STR_INSURDAY = "INSURDAY";
	
	
	public final static String PKCS5PADDING = "AES/CBC/PKCS5Padding";
	
	
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
