
package tw.com.core;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import org.codehaus.jettison.json.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import tw.msigDvrBack.common.BaseController;
import tw.spring.ComLogger;






@Controller
@RequestMapping("/json")
public class BatchJobController extends BaseController {

	@ComLogger
	private Logger logger;


	@Autowired
	private BatchJobService batchJobService;

	/**
	 * 明台關懷推播產生
	 * 
	 * @author Jerry
	 * @since 2018/10/23
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobCareCreate", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String executeJobCareCreate(HttpServletRequest request, HttpSession session) throws Exception {
		JSONObject json = new JSONObject();

		String result = null;
		try {
			result = batchJobService.executeJobCareCreate();

			return result;

		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();
			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 明台保單到期前N天提醒資料產生
	 * 
	 * @author Jerry
	 * @since 2018/10/24
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobEndUBICreate", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String executeJobEndUBICreate() throws Exception {

		JSONObject json = new JSONObject();
		String result = null;
		try {
			result = batchJobService.executeJobEndUBICreate();

			return result;

		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();
			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 明台優惠消息推播資料產生
	 * 
	 * @author Jerry
	 * @since 2018/10/23
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobPromoCreate", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String executeJobPromoCreate(HttpServletRequest request, HttpSession session) throws Exception {
		JSONObject json = new JSONObject();
		String result = null;
		try {
			result = batchJobService.executeJobPromoCreate();

			return result;
		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();

			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 設備資訊同步
	 * 
	 * @author mingkun
	 * @since 2020/06/08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobSyncDevice", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String executeJobSyncDevice() throws Exception {
		JSONObject json = new JSONObject();
		String result;
		try {
			result = batchJobService.executeJobSyncDevice();
			return result;
		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 長期未使用DVR的會員關懷信件寄送
	 *
	 * @author mingkun
	 * @since 2020/06/05
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobEmailDeviceUnused", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String emailDeviceUnused() throws Exception {
		JSONObject json = new JSONObject();
		String result;
		try {
			result = batchJobService.jobEmailDeviceUnused();
			return result;
		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 每月提供會員駕駛行為分析報告通知信(連結由廠商準備)
	 *
	 * @author mingkun
	 * @since 2020/06/05
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/JobEmailDriveReport", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String jobEmailDriveReport() throws Exception {
		JSONObject json = new JSONObject();
		String result = "";
		try {
			result = batchJobService.jobEmailDriveReport();
			return result;
		} catch (Exception e) {
			json.put("err_cde", "01");
			json.put("err_msg", "系統發生錯誤，請聯絡系統管理員");
			result = json.toString();
			e.printStackTrace();
			return result;
		}
	}
}
