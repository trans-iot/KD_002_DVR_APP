package tw.msigDvrBack.omom001m;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.common.BillObject;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbCustCar;
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;

/**
 * 會員服務異動 OMOM001F_OMOM002F
 * 
 * @author Bob
 * @since 2018/10/31
 */
@Controller
@RequestMapping("/OMOM002F")
public class OMOM002FController extends BaseController {
	private final static String VIEW = "OMOM001M/OMOM002F";
	private final static String UPDATE_VIEW = "OMOM001M/OMOM002F_M";
	private final static String Q001_VIEW = "OMOM001M/OMOM002F_Q001";
	private final static String Q002_VIEW = "OMOM001M/OMOM002F_Q002";
	private final static String REMOVE_DEVICE_VIEW = "OMOM001M/OMOM002F_RD";
	private final static String RESET_CARNO_VIEW = "OMOM001M/OMOM002F_RC";
	private final static String USERID_SEARCH_VIEW = "OMOM001M/OMOM002F_US";
	private final static String UPDATE_TB_CUST_CAR_VIEW = "OMOM001M/OMOM002F_UC";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private OMOM002FService omom002FService;

	@Autowired
	private OMOM003FService omom003FService;

	private OMOM002FQueryForm queryForm;

	private BaseForm detailQueryForm;

	private OMOM002FUserIdQueryForm userIdQueryForm;

	/**
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * 
	 * @since 2018/10/31
	 * @author Bob
	 *
	 * @return OMOM005FQueryForm
	 */
	@ModelAttribute("queryForm")
	public OMOM002FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		this.queryForm = new OMOM002FQueryForm();
		return this.queryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : initDetailForm
	 * Description : 明細頁面查詢form
	 * </pre>
	 * 
	 * @since 2018/11/02
	 * @author Bob
	 *
	 * @return BaseForm
	 */
	@ModelAttribute("detailQueryForm")
	public BaseForm initDetailForm() {
		if (this.detailQueryForm != null) {
			return this.detailQueryForm;
		}
		this.detailQueryForm = new BaseForm();
		return this.detailQueryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : initUserIdQueryForm
	 * Description :
	 * </pre>
	 * 
	 * @since 2018/11/02
	 * @author Bob
	 *
	 * @return OMOM002FUserIdQueryForm
	 */
	@ModelAttribute("userIdQueryForm")
	public OMOM002FUserIdQueryForm initUserIdQueryForm() {
		if (this.userIdQueryForm != null) {
			return this.userIdQueryForm;
		}
		this.userIdQueryForm = new OMOM002FUserIdQueryForm();
		return this.userIdQueryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : index
	 * Description :
	 * </pre>
	 * 
	 * @since 2018/10/31
	 * @author Bob
	 *
	 * @param session
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = null;
		this.queryForm = initQueryForm();
		mav.addObject("queryForm", this.queryForm);
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : query
	 * Description : 查詢
	 * </pre>
	 * 
	 * @since 2018/10/31
	 * @author Bob
	 *
	 * @param form
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") OMOM002FQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(VIEW);
		queryForm.setQueryClicked("true");
		List<Map<String, Object>> list = this.omom002FService.queryList(form);
		List<TbLookupCde> custaList = omom003FService.getDscrByType(Constants.CU_STA);
		mav.addObject("custaList", custaList);
		if (list.size() > 0) {
			mav.addObject("command", list.get(0));
			mav.addObject("searchResult", "exit");
		} else {
			mav.addObject("searchResult", "noexit");
		}
		return mav;
	}

	/**
	 * 
	 * 
	 * @param userId
	 *            String
	 * @return ModelAndView
	 */
	@RequestMapping("doQuery001")
	public ModelAndView doQuery001(String userId) {

		TbCustCar tbCustCar = omom002FService.query001(userId);

		ModelAndView mav = new ModelAndView(Q001_VIEW);
		mav.addObject("tbCustCar", tbCustCar);
		return mav;
	}

	/**
	 * 
	 * 
	 * @param userId
	 *            String
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("doQuery002")
	public ModelAndView doQuery002(String userId) throws Exception {
		ModelAndView mav = new ModelAndView(Q002_VIEW);

		List<TbDevice> tbDeviceList = omom002FService.query002(userId);
		List<TbLookupCde> detyplist = omom003FService.getDscrByType(Constants.DETYP);
		List<TbLookupCde> destaList = omom003FService.getDscrByType(Constants.DESTA);
		mav.addObject("detyplist", detyplist);
		mav.addObject("destaList", destaList);
		mav.addObject("tbDeviceList", tbDeviceList);

		return mav;
	}

	/**
	 * 
	 * 
	 * @param form
	 *            OMOM002FLovQueryForm
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("removeDeviceLov")
	public ModelAndView removeDeviceLov(OMOM002FLovQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(REMOVE_DEVICE_VIEW);
		form = omom002FService.decodeLovQueryForm(form);
		List<TbDevice> list = omom002FService.queryRemoveDevice(form.getUserId());
		List<TbLookupCde> destaList = omom003FService.getDscrByType(Constants.DESTA);
		List<TbLookupCde> detyplist = omom003FService.getDscrByType(Constants.DETYP);
		mav.addObject("destaList", destaList);
		mav.addObject("detyplist", detyplist);
		mav.addObject("command", form);
		mav.addObject("list", list);

		return mav;
	}

	/**
	 * 
	 * 
	 * @param form
	 *            OMOM002FLovQueryForm
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("resetCarNoLov")
	public ModelAndView resetCarNoLov(OMOM002FLovQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(RESET_CARNO_VIEW);
		form = omom002FService.decodeLovQueryForm(form);
		TbCustCar tbCustCar = omom002FService.queryByPK(form.getUserId());
		mav.addObject("command", form);
		mav.addObject("tbCustCar", tbCustCar);

		return mav;

	}

	/**
	 * 
	 * 
	 * @param userId
	 *            String
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("updateTbCustCarLov")
	public ModelAndView updateTbCustCarLov(OMOM002FLovQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(UPDATE_TB_CUST_CAR_VIEW);
		form = omom002FService.decodeLovQueryForm(form);
		TbCustCar tbCustCar = omom002FService.queryByPK(form.getUserId());
		mav.addObject("command", form);
		mav.addObject("tbCustCar", tbCustCar);
		return mav;

	}

	/**
	 * 
	 * 
	 * @param form
	 *            OMOM002FUserIdQueryForm
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("userIdSearchLov")
	public ModelAndView userIdSearchLov(OMOM002FUserIdQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(USERID_SEARCH_VIEW);
		this.userIdQueryForm = null;
		this.userIdQueryForm = initUserIdQueryForm();

		return mav;
	}

	@RequestMapping("/userIdQuery")
	public ModelAndView userIdQuery(HttpServletRequest request, OMOM002FUserIdQueryForm form) {
		ModelAndView mav = new ModelAndView(USERID_SEARCH_VIEW);
		List<Map<String, Object>> mapList = omom002FService.queryUserIdList(form);
		mav.addObject("list", mapList);
		mav.addObject("userIdQueryForm", form);
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : doRemoveDevice
	 * Description : 執行解除綁定
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom002FRomForm
	 * @return AjaxForm
	 */
//	@RequestMapping(value = "/doRemoveDevice", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxForm doRemoveDevice(HttpServletRequest request, OMOM002FRomForm omom002FRomForm) {
//
//		HttpSession session = request.getSession();
//		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
//		String loginUser = bo.getUserProf().getSysUserId();
//		omom002FRomForm.setUserstamp(loginUser);
//		AjaxForm ajaxForm = new AjaxForm();
//		String message = "";
//
//		try {
//			SimpleDateFormat format = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
//			omom002FRomForm.setTrx_time(format.format(new Date()));
//			// 執行update
//			Map<String, String> map = omom002FService.removeDevice(omom002FRomForm);
//			if (map.get("errCde") != null && map.get("errCde").equals("00")) {
//				Integer count = omom002FService.updateTbDevice(omom002FRomForm);
//				if (omom002FRomForm.getIs_reset().equals("Y")) {
//					Integer countCar = omom002FService.updateTbCustCar(omom002FRomForm);
//				}
//				ajaxForm.setResult(AjaxResultEnum.OK);
//				message = myContext.getMessage("omom002f.remove.device.success");
//			} else {
//				ajaxForm.setResult(AjaxResultEnum.FAIL);
//				message = myContext.getMessage("omom003f.remove.device.fail") + map.get("errMsg");
//			}
//		} catch (Exception e) {
//			ajaxForm.setResult(AjaxResultEnum.FAIL);
//			message = e.getMessage().replace("java.lang.Exception:", "");
//			logger.error("SMDD003F update ERROR {} {}", e.getMessage(), e.getStackTrace());
//		}
//		ajaxForm.setMessage(message);
//		return ajaxForm;
//	}

	/**
	 * 
	 * <pre>
	 * Method Name : checkDrviceStatus
	 * Description : 執行解除綁定
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom003FRomForm
	 * @return AjaxForm
	 */
//	@RequestMapping(value = "/doResetCarNo", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxForm doResetCarNo(HttpServletRequest request, OMOM002FResForm form) {
//
//		HttpSession session = request.getSession();
//		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
//		String loginUser = bo.getUserProf().getSysUserId();
//		AjaxForm ajaxForm = new AjaxForm();
//		String message = "";
//
//		try {
//			SimpleDateFormat format = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
//			form.setTrx_time(format.format(new Date()));
//			if (form.getIs_reset().equals("Y")) {
//				// 執行update
//				Map<String, String> map = omom002FService.resetMileage(form);
//				if (map.get("errCde") != null && map.get("errCde").equals("00")) {
//					Integer countCar = omom002FService.updateTbCustCarReset(form);
//					// 後台UI修改車牌時，檢查是否有正在申請UBI.如有，取消申請
//					Integer countUbi = omom002FService.executeCancelApplyingUbi(form);
//					ajaxForm.setResult(AjaxResultEnum.OK);
//					message = myContext.getMessage("update.success.message");
//				} else {
//					ajaxForm.setResult(AjaxResultEnum.FAIL);
//					message = myContext.getMessage("omom002f.reset.mileage.fail") + ":" + map.get("errMsg");
//				}
//			}else{
//				Integer countCar = omom002FService.updateTbCustCarReset(form);
//				// 後台UI修改車牌時，檢查是否有正在申請UBI.如有，取消申請
//				Integer countUbi = omom002FService.executeCancelApplyingUbi(form);
//				ajaxForm.setResult(AjaxResultEnum.OK);
//				message = myContext.getMessage("update.success.message");
//			}
//
//		} catch (Exception e) {
//			ajaxForm.setResult(AjaxResultEnum.FAIL);
//			message = myContext.getMessage("omom002f.reset.mileage.fail") + ":"
//					+ e.getMessage().replace("java.lang.Exception:", "");
//			logger.error("OMOM002F update ERROR {} {}", e.getMessage(), e.getStackTrace());
//		}
//		ajaxForm.setMessage(message);
//		return ajaxForm;
//	}

	/**
	 * 
	 * <pre>
	 * Method Name : checkDrviceStatus
	 * Description : 執行解除綁定
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom003FRomForm
	 * @return AjaxForm
	 */
	@RequestMapping(value = "/doUpdateTbCustCar", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm doUpdateTbCustCar(HttpServletRequest request, OMOM002FUpdCarForm form) {

		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		try {
			Integer countCar = omom002FService.updateTbCustCarLov(form);
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("update.success.message");
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.FAIL);
			message = myContext.getMessage("update.fail.message") + ":"
					+ e.getMessage().replace("java.lang.Exception:", "");
			logger.error("OMOMD002F update ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

}