package tw.msigDvrBack.omom001m;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.manual.TbCustomerManualMapper;
import tw.msigDvrBack.manual.TbLookupCdeManualMapper;
import tw.msigDvrBack.manual.TbSysUserMapper;
import tw.msigDvrBack.manual.TbWelcomePageManualMapper;
import tw.msigDvrBack.persistence.TbCustomer;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbLookupCdeKey;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.AESUtilDmm;
import tw.util.CallWebApi;
import tw.util.CwDateUtils;
import tw.util.PageUtils;

/**
 * 長期未使用DVR的客戶清單
 * OMOM001F_OMOM007F
 * 
 * @author mingkun
 * @since 2020/06/11
 */
@Service
public class OMOM007FService extends BaseService
{
	@Autowired
	private TbWelcomePageManualMapper tbWelcomePageMapper;

	@Autowired
	private TbLookupCdeManualMapper tbLookupCdeManualMapper;

	@Autowired
	protected TbCustomerManualMapper tbCustomerManualMapper;

	@Autowired
	private TbSysUserMapper tbSysUserMapper;
	
	@ComLogger
	private Logger logger;
	
	@Autowired
	private MyContext myContext;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);


	/**
	 * 設置邊框
	 *
	 * @param style
	 * @return
	 */
	private XSSFCellStyle setBorder(XSSFCellStyle style) {
		style.setBorderBottom((short) 1);
		style.setBorderTop((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		return style;
	}

	/**
	 * 寫入 data 至 file
	 * Description :  匯出excel
	 *
	 * @param request
	 * @return
	 */
	public XSSFWorkbook getExcelWorkbook(HttpServletRequest request, List<Map<String, Object>> list) {
		XSSFWorkbook wb = new XSSFWorkbook();

		// 字體格式
		XSSFFont font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index); // 顏色
		font.setFontName("微軟正黑體");
		font.setFontHeightInPoints((short) 12);

		XSSFCellStyle titleBorder = wb.createCellStyle();
		titleBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 靠中
		titleBorder.setFont(font);

		XSSFCellStyle setBorder3 = wb.createCellStyle();
		setBorder3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 靠中
		setBorder3.setFont(font);
		setBorder(setBorder3);

		XSSFCellStyle setBorder4 = wb.createCellStyle();
		setBorder4.setFillForegroundColor(HSSFColor.SEA_GREEN.index);//背景色
		setBorder4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		setBorder4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setBorder(setBorder4);

		String[] dataName = {
				"會員編號",
				"DVR S/N",
				"車牌號碼",
				"電子信箱",
				"未使用天數",
				"最後使用日期"
		};

		try {
			int colIndex = 0;
			XSSFSheet excelSheet = wb.createSheet("長期未使用DVR的客戶清單");//創建空白的sheet

			XSSFRow titleHeader = excelSheet.createRow(colIndex);
			for (int k = 0; k < dataName.length; k++) {
				titleHeader.createCell(k).setCellValue(dataName[k]);
				excelSheet.setColumnWidth(k, 256 * 20);
				titleHeader.getCell(k).setCellStyle(titleBorder);
			}

			//提取資料庫資料並寫入Excel
			colIndex = colIndex + 1;

			for (Map<String, Object> map : list) {
				XSSFRow excelValeue = excelSheet.createRow(colIndex);
				Object userId = map.get("userId");
				Object sn = map.get("sn");
				Object carsNo = map.get("carNo");
				Object email = map.get("email");
				Object unusedDays = map.get("unusedDays");
				Object uploadDate = map.get("uploadDate");

				excelValeue.createCell(0).setCellValue(userId == null? "": String.valueOf(userId));
				excelValeue.createCell(1).setCellValue(sn == null? "": String.valueOf(sn));
				excelValeue.createCell(2).setCellValue(carsNo == null? "": String.valueOf(carsNo));
				excelValeue.createCell(3).setCellValue(email == null? "": String.valueOf(email));
				excelValeue.createCell(4).setCellValue(unusedDays == null? "": String.valueOf(unusedDays));
				excelValeue.createCell(5).setCellValue(uploadDate == null? "": String.valueOf(uploadDate));
				for (int k = 0; k < dataName.length; k++) {
					excelSheet.setColumnWidth(k, 256 * 30);
					excelValeue.getCell(k).setCellStyle(setBorder3);
				}
				colIndex++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:" + e);
		}
		return wb;
	}

	public List<Map<String, Object>> queryList(OMOM007FQueryForm form, List<Map<String, Object>> realList) {
		int perPage = PageUtils.getPageInt(form.getPerPageNum());
		int curPage = PageUtils.getPageInt(form.getPages());
		List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
		if (realList.size() > 0) {
			int iter;
			if (curPage == 1) {
				//edit by Marks 2020/07/03 bug fix
				for(int i = 0 ; i < realList.size() ; i++) {
					pageList.add(realList.get(i));
				}
			} else {
				int iterMin = ((curPage-1) * perPage);
				int iterMax = (curPage * perPage) -1;
				iter = iterMin;
				if (iterMax > realList.size()) {
					while (iter < realList.size()) {
						if (realList.get(iter) != null) {
							pageList.add(realList.get(iter));
							iter++;
						} else {
							break;
						}
					}
				} else {
					while (iter <= iterMax) {
						if (realList.get(iter) != null) {
							pageList.add(realList.get(iter));
							iter++;
						} else {
							break;
						}
					}
				}
			}
		}
		return pageList;
	}

	public List<Map<String, Object>> getRealList(List<Map<String, Object>> list) {
		List<Map<String, Object>> realList = new ArrayList<Map<String, Object>>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String sn = String.valueOf(map.get("sn"));
				if (!"".equals(sn)) {
					realList.add(map);
				}
			}
		}
		return realList;
	}

	public List<Map<String, Object>> getTotalList(OMOM007FQueryForm form) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		String errCode = "00";
		String errMsg = "";
		statusMap.put("errCode", errCode);
		statusMap.put("errMsg", errMsg);
		String unusedDays = form.getDay();
		String apiUrl = null;
		String aesKey = null;
		String aesIv = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(statusMap); // 第一筆資料作為狀態MAP
		try {
			TbLookupCdeKey key = new TbLookupCdeKey();
			key.setLookupCde(Constants.TIOTDVR);
			key.setLookupType(Constants.WEBURL);
			TbLookupCde tbLookupCde = tbLookupCdeManualMapper.selectByPrimaryKey(key);
			if (tbLookupCde != null) {
				apiUrl = tbLookupCde.getType1();
			}


			key.setLookupCde(Constants.TIOTDVR);
			key.setLookupType(Constants.AESKEY);
			tbLookupCde = tbLookupCdeManualMapper.selectByPrimaryKey(key);
			if (tbLookupCde != null) {
				aesKey = tbLookupCde.getType1();
				aesIv = tbLookupCde.getType2();
			}

			if (!StringUtils.isEmpty(apiUrl) && !StringUtils.isEmpty(aesKey)
					&& !StringUtils.isEmpty(aesIv)) {
				apiUrl = apiUrl + "/TiotDvrWeb/json/getDeviceUnusedList.html";
				JSONObject apiJsonObject = new JSONObject();
				apiJsonObject.put("unused_days", unusedDays);
				apiJsonObject.put("trx_time", simpleDateFormat.format(new Date()));

				String jsonStr = apiJsonObject.toString();
				logger.info("OMOM007FService before encrypt: {}", jsonStr);
				String secretData = null;
				try {
					secretData = AESUtilDmm.encrypt(jsonStr, aesKey, aesIv);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("OMOM007FService after encrypt: {}", secretData);

				Map<String, Object> postMap = new HashMap<String, Object>();
				postMap.put("secret_data", secretData);
				String result = CallWebApi.getUserStatusInfo(toJson(postMap), apiUrl, "application/json",
						"POST", 0, "UTF-8");
//				String testResult = "{\"device_unused_list\":[{\"user_id\":\"MTTW00000036\",\"unused_days\":\"28\",\"sn\":\"ABCD0055\",\"upload_date\":\"2020/05/20\"}],\"errMsg\":\"\",\"errCde\":\"00\"}";
//				String mulitTestResult = "{\"device_unused_list\":[{\"user_id\":\"MTTW00000036\",\"unused_days\":\"28\",\"sn\":\"ABCD0055\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW10000037\",\"unused_days\":\"27\",\"sn\":\"ABCD0054\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000036\",\"unused_days\":\"26\",\"sn\":\"ABCD0053\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000034\",\"unused_days\":\"25\",\"sn\":\"ABCD0052\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000033\",\"unused_days\":\"24\",\"sn\":\"ABCD0051\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000032\",\"unused_days\":\"23\",\"sn\":\"ABCD0050\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000030\",\"unused_days\":\"22\",\"sn\":\"ABCD0049\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000036\",\"unused_days\":\"28\",\"sn\":\"ABCD0055\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW10000037\",\"unused_days\":\"27\",\"sn\":\"ABCD0054\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000036\",\"unused_days\":\"26\",\"sn\":\"ABCD0053\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000034\",\"unused_days\":\"25\",\"sn\":\"ABCD0052\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000036\",\"unused_days\":\"26\",\"sn\":\"ABCD0053\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000034\",\"unused_days\":\"25\",\"sn\":\"ABCD0052\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000033\",\"unused_days\":\"24\",\"sn\":\"ABCD0051\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000032\",\"unused_days\":\"23\",\"sn\":\"ABCD0050\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000030\",\"unused_days\":\"22\",\"sn\":\"ABCD0049\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW00000036\",\"unused_days\":\"28\",\"sn\":\"ABCD0055\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTTW10000037\",\"unused_days\":\"27\",\"sn\":\"ABCD0054\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000036\",\"unused_days\":\"26\",\"sn\":\"ABCD0053\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000034\",\"unused_days\":\"25\",\"sn\":\"ABCD0052\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000036\",\"unused_days\":\"26\",\"sn\":\"ABCD0053\",\"upload_date\":\"2020/05/20\"},{\"user_id\":\"MTDV00000034\",\"unused_days\":\"25\",\"sn\":\"ABCD0052\",\"upload_date\":\"2020/05/20\"}],\"errMsg\":\"\",\"errCde\":\"00\"}";
				// TODO 測試用(多筆)
				JSONObject jsonResult = new JSONObject(result);
				if (jsonResult.has("errCde")) {
					String errcode = jsonResult.getString("errCde");
					if ("00".equals(errcode)) {
						// 逐筆資料處理
						if (jsonResult.has("device_unused_list")) {
							JSONArray arrayResult = jsonResult.getJSONArray("device_unused_list");
							logger.info("JsonResult" + jsonResult);
							for (int i = 0; i < arrayResult.length(); i++) {
								JSONObject resultVal = arrayResult.getJSONObject(i);
								Map<String, Object> map = new HashMap<String, Object>();
								String sn = resultVal.getString("sn");
								String userId = resultVal.getString("user_id");
								String unused_days = resultVal.getString("unused_days");
								String uploadDate = resultVal.getString("upload_date"); //yyyy/MM/dd
								map.put("sn", sn);
								map.put("userId", userId);
								map.put("unusedDays", unused_days);
								map.put("uploadDate", uploadDate);
								if (!StringUtils.isEmpty(userId) && userId.length() > 0) {
									TbCustomer tbCustomer = tbCustomerManualMapper.selectByPrimaryKey(userId);
									if (tbCustomer == null) {
										map.put("email", "");
										map.put("carNo", "");
									} else {
										String email = tbCustomer.getEmail();
										String carNo = tbCustomer.getCarNo();
										map.put("email", email);
										map.put("carNo", carNo);
									}
								} else {
									map.put("email", "");
									map.put("carNo", "");
								}
								list.add(map);
							}
						}
					} else {
						errCode = "99";
						String apiErrMsg = jsonResult.getString("errMsg");
						errMsg = "呼叫一鍵理賠通知服務平台API失敗，錯誤訊息：" + apiErrMsg;
						statusMap.put("errCode", errCode);
						statusMap.put("errMsg", errMsg);
						list.set(0, statusMap);
					}
				} else {
					errCode = "99";
					errMsg = "apiErrCode 不等於 00";
					statusMap.put("errCode", errCode);
					statusMap.put("errMsg", errMsg);
					list.set(0, statusMap);
				}
			} else {
				errCode = "31";
				errMsg = "基本參數未設置";
				statusMap.put("errCode", errCode);
				statusMap.put("errMsg", errMsg);
				list.set(0, statusMap);
			}
		} catch (Exception e) {
			logger.error("getList error", e);
			errCode = "99";
			errMsg = "";
			statusMap.put("errCode", errCode);
			statusMap.put("errMsg", errMsg);
			list.set(0, statusMap);
		}
		return list;
	}
	
	/**
	 * 預備查詢條件，提供模糊查詢
	 */
	private Map<String,Object> fillupCri(OMOM007FQueryForm form) {
		String unusedDays = form.getUnusedDays();
		Map<String,Object> querymap = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(unusedDays)) {
			querymap.put("unusedDays", unusedDays);
		}
		return querymap;
 
	}
	
	//取得未使用天數的下拉選單，定義為5/10/15/20/25/30天
	public List<Map<String, Object>> getUnUsedDaysList(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		int initDay = 5;
		int maxDay = 30;
		while (initDay<=maxDay) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("day", String.valueOf(initDay));
			map.put("desc", initDay + "天");
			list.add(map);
			initDay = initDay + 1;
		}
		return list;
	}
}
