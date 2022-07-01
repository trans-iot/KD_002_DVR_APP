package tw.msigDvrBack.smdd001m;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ToStringBuilder;
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
import tw.msigDvrBack.omom001m.OMOM002FService;
import tw.msigDvrBack.persistence.TbCustCar;
import tw.msigDvrBack.persistence.TbCustOpenid;
import tw.msigDvrBack.persistence.TbCustomer;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.OWASPAPI;

/**
 * 參考會員資料維護 SMDD001F_SMDD003F
 * 
 * @author Bob
 * @since 2018/10/22
 */
@Controller
@RequestMapping("/SMDD003F")
public class SMDD003FController extends BaseController {
	private final static String VIEW = "SMDD001M/SMDD003F";
	private final static String UPDATE_VIEW = "SMDD001M/SMDD003F_M";
	private final static String MILAGE_VIEW = "SMDD001M/SMDD003F_Milage";
	private final static String EMAIL_VIEW = "SMDD001M/SMDD003F_ChgEmail";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private SMDD003FService smdd003FService;
	
	@Autowired
	private OMOM002FService omom002FService;

	private SMDD003FQueryForm queryForm;
	private BaseForm detailQueryForm;
	private BaseForm crudDetailQueryForm;

	/**
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * 
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @return SMDD003FQueryForm
	 */
	@ModelAttribute("queryForm")
	public SMDD003FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		this.queryForm = new SMDD003FQueryForm();
		return this.queryForm;
	}
	

	/**
	 * 
	 * <pre>
	 * Method Name : initDetailForm
	 * Description : 明細頁面查詢form
	 * </pre>
	 * 
	 * @author Bob
	 * @since 2018/10/22
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
	 * Method Name : initDtlCommandForm
	 * Description : 可編輯明細頁面查詢form
	 * </pre>
	 * 
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @return BaseForm
	 */
	@ModelAttribute("crudDetailQueryForm")
	public BaseForm initDtlCommandForm() {
		if (this.crudDetailQueryForm != null) {
			return this.crudDetailQueryForm;
		}
		this.crudDetailQueryForm = new BaseForm();
		return this.crudDetailQueryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : index
	 * Description :
	 * </pre>
	 * 
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = null;
		this.queryForm = initQueryForm();
		Map<String, String> custstatusaDscr = getCommonService().selectLookupCdeAndDscr(Constants.CU_STA);
		mav.addObject("custstatusaDscr", custstatusaDscr);
		mav.addObject("queryForm", this.queryForm);
		mav.addObject("totalCount", new Integer(0));
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : query
	 * Description :
	 * </pre>
	 * 
	 * @author Bob
	 * @since 2018/10/22
	 *
	 * @param queryForm
	 * @return ModelAndView
	 * @throws ParseException 
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") SMDD003FQueryForm form) throws ParseException {
		queryForm.setQueryClicked("true");
		Long totalCount = this.smdd003FService.countTotal(form);
		String checkPage = checkPage(queryForm.getPages(), form.getPerPageNum(), totalCount);
		queryForm.setPages(checkPage);

		List<TbCustomer> list = this.smdd003FService.queryList(form);
		// 取得 會員狀態
		Map<String, String> custstatusaDscr = getCommonService().selectLookupCdeAndDscr(Constants.CU_STA);
		ModelAndView mav = new ModelAndView(VIEW);

		mav.addObject("custstatusaDscr", custstatusaDscr);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	
	/**
	 * @description 新增會員頁面
	 * @method showInsertForm
	 * @param queryForm
	 * <pre>
	 * 
	 * @author Marks
	 * @since 2020/06/04
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView showInsertForm(HttpServletRequest request,SMDD003FQueryForm queryForm) {
		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		SMDD003FModForm modForm = new SMDD003FModForm();
		logger.debug("modForm :{}", ToStringBuilder.reflectionToString(modForm));

		modForm.setCrUser(loginUser);
		modForm.setUserstamp(loginUser);
		modForm.setPsdStatus(Constants.PWD_STATUS_FIRST);
		modForm.setAction("insert");

		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		bindCommonMav(mav);
		mav.addObject("command", modForm);
		return mav;
	}

	/**
	 * 依PK查詢會員
	 * 
	 * @param brand
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String detailuserId) {

		SMDD003FModForm modForm = smdd003FService.queryByKey(detailuserId);
		logger.debug("modForm :{}", ToStringBuilder.reflectionToString(modForm));

		modForm.setAction("detail");

		List<TbCustOpenid> tbcustopenid_list = smdd003FService.queryByKey_openid(detailuserId);

		TbCustCar tbCustCar = omom002FService.queryByPK(detailuserId);

		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		bindCommonMav(mav);
		mav.addObject("list", tbcustopenid_list);
		mav.addObject("command", modForm);
		mav.addObject("tbCustCar", tbCustCar);
		return mav;
	}

	@RequestMapping("/update")
	public ModelAndView showUpdateForm(HttpServletRequest request, String updatetuserId) {
		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		// MasterForm
		SMDD003FModForm smdd003fModForm = new SMDD003FModForm();
		smdd003fModForm.setCrUser(loginUser);
		smdd003fModForm.setUserstamp(loginUser);

		smdd003fModForm = smdd003FService.queryByKey(updatetuserId);

		smdd003fModForm.setAction("update");
		TbCustCar tbCustCar = omom002FService.queryByPK(updatetuserId);

		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		bindCommonMav(mav);
		mav.addObject("command", smdd003fModForm);
		mav.addObject("tbCustCar", tbCustCar);
		return mav;
	}
	
	/**
	 * 新增會員資料
	 * 
	 * @param smdd003fModForm
	 * @return
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(HttpServletRequest request, SMDD003FModForm smdd003fModForm) {

		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		smdd003fModForm.setUserstamp(loginUser);
		smdd003fModForm.setDatestamp(new java.util.Date());

		AjaxForm ajaxForm = new AjaxForm();
		ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);

		try {
			if(smdd003FService.queryTbDevice(OWASPAPI.decodeForHTML(smdd003fModForm.getSn())) > 0) {
				// 執行insert
				ajaxForm = smdd003FService.insertData(smdd003fModForm, ajaxForm);
			} else {
				ajaxForm.setMessage(myContext.getMessage("smdd003f.message.snNotExists"));
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
			String message = e.getCause().getMessage();
			ajaxForm.setMessage(message);
			logger.error("SMDD003F insert ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		return ajaxForm;
	}

	/**
	 * 更新會員資料
	 * 
	 * @param form
	 *            SMDD003FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateData(HttpServletRequest request, SMDD003FModForm smdd003fModForm) {

		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		smdd003fModForm.setUserstamp(loginUser);
		smdd003fModForm.setDatestamp(new java.util.Date());

		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		try {
			if(smdd003FService.queryTbDevice(OWASPAPI.decodeForHTML(smdd003fModForm.getSn())) > 0) {
				// 執行update
				int count = smdd003FService.updateData(smdd003fModForm);
				if (count > 0) {
					ajaxForm.setResult(AjaxResultEnum.OK);
					message = myContext.getMessage("update.success.message");
				} else {
					ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
					message = myContext.getMessage("update.fail.message");
				}
			} else {
				message = myContext.getMessage("smdd003f.message.snNotExists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage().replace("java.lang.Exception:", "");
			logger.error("SMDD003F update ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	
	/**
	 * 依PK刪除
	 * 
	 * @param deleteSeqNo
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(String deleteUserId) throws Exception {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		int count = smdd003FService.deleteByKey(deleteUserId);
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("tbNews delete fail Pkey => deleteSeqNo : " + deleteUserId);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	
	/**
	 * @description 設備解除綁定
	 * @method unBindDevice
	 * @param form
	 *            SMDD003FModForm
	 * @author Marks           
	 * @return
	 */
	@RequestMapping(value = "/unBindDevice", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm unBindDevice(HttpServletRequest request, SMDD003FModForm smdd003fModForm) {

		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		smdd003fModForm.setUserstamp(loginUser);
		smdd003fModForm.setDatestamp(new java.util.Date());

		AjaxForm ajaxForm = new AjaxForm();

		try {
			// 執行設備解除綁定
			ajaxForm = smdd003FService.execuetUnBindDevice(smdd003fModForm);
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			ajaxForm.setMessage(e.getMessage().replace("java.lang.Exception:", ""));
			logger.error("SMDD003F update ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		return ajaxForm;
	}
	
	/**
	 * 里程數頁面
	 * 
	 * @param form
	 *            SMDD003FModForm
	 * @return
	 */
	@RequestMapping(value = "/miageView", method = RequestMethod.POST)
	public ModelAndView showInitMiageView(String milageUserId) {
		queryForm.setQueryClicked("true");
		
		SMDD003FMilageQueryForm milageQueryForm = new SMDD003FMilageQueryForm();
		smdd003FService.initMilageView(milageUserId, milageQueryForm);
		ModelAndView mav = new ModelAndView(MILAGE_VIEW);
		mav.addObject("milageQueryForm", milageQueryForm);
		return mav;
	}
	
	/**
	 * 里程數查詢
	 * 
	 * @param form
	 *            SMDD003FModForm
	 * @return
	 */
	@RequestMapping(value = "/queryMilage", method = RequestMethod.POST)
	public ModelAndView queryMilage(@ModelAttribute("milageQueryForm") SMDD003FMilageQueryForm milageQueryForm) throws Exception {
		queryForm.setQueryClicked("true");

		String totalMileage = smdd003FService.queryMilage(milageQueryForm);
		ModelAndView mav = new ModelAndView(MILAGE_VIEW);
		mav.addObject("totalMileage", totalMileage);
		return mav;
	}
	
	/**
	 * @description ajax依SN號查詢設備
	 * @method queryDevice
	 * 
	 * @author Marks
	 * @since 2020/06/24
	 * @param sn
	 * @return ajaxForm
	 */
	@RequestMapping(value = "/queryDevice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public AjaxForm queryDevice(String sn) throws Exception {

		AjaxForm ajaxForm = new AjaxForm();
		ajaxForm.setResult(AjaxResultEnum.OK);

		try {
			// 執行query
			SMDD003FModForm modForm = smdd003FService.queryDeviceBySn(sn);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tbDevice", modForm);
			ajaxForm.setMap(map);
				
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.QUERY_FAIL);
			String message = e.getCause().getMessage();
			ajaxForm.setMessage(message);
			logger.error("SMDD003F insert ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		return ajaxForm;
	}
	
	
	/**
	 * @description 顯示修改email頁面
	 * @method showEditEmailView
	 * 
	 * @author Marks
	 * @since 2020/07/02
	 * @param emailUserId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public ModelAndView showEditEmailView(String emailUserId, String email) {
		ModelAndView mav = new ModelAndView(EMAIL_VIEW);
		mav.addObject("userId", emailUserId);
		mav.addObject("email", email);
		return mav;
	}
	
	/**
	 * @description 修改email
	 * @method doUpdateEmail
	 * 
	 * @author Marks
	 * @since 2020/07/02
	 * @param sn
	 * @return ajaxForm
	 */
	@RequestMapping(value = "/doUpdateEmail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public AjaxForm doUpdateEmail(SMDD003FModForm smdd003fModForm) throws Exception {
		String message = "";
		AjaxForm ajaxForm = new AjaxForm();
		ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);

		try {
				Long count = smdd003FService.countTbCustomer(smdd003fModForm);	
				if(count > 0) {
					message = myContext.getMessage("tw.updateEmail.message1");
				} else {
					smdd003FService.executeUpdateEmail(smdd003fModForm);
					message = myContext.getMessage("update.success.message");
					ajaxForm.setResult(AjaxResultEnum.OK);
				}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getCause().getMessage();
			ajaxForm.setMessage(message);
			logger.error("SMDD003F updateEmail ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	
	/**
	 * @description 會員重寄密碼信件
	 * @method doResetPwd
	 * 
	 * @author Marks
	 * @since 2020/07/02
	 * @param sn
	 * @return ajaxForm
	 */
	@RequestMapping(value = "/doResetPwd", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public AjaxForm doResetPwd(SMDD003FModForm smdd003fModForm) throws Exception {
		String message = "";
		AjaxForm ajaxForm = new AjaxForm();
		ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);

		try {
				smdd003FService.executeResetPwd(smdd003fModForm);
				message = myContext.getMessage("tw.resetPwd.success.message");
				ajaxForm.setResult(AjaxResultEnum.OK);
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getCause().getMessage();
			ajaxForm.setMessage(message);
			logger.error("SMDD003F updateEmail ERROR {} {}", e.getMessage(), e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	
	private void bindCommonMav(ModelAndView mav) {
		// 取得 客戶狀態
		Map<String, String> custstatusaDscr = getCommonService().selectLookupCdeAndDscr(Constants.CU_STA);
		// 取得服務種類 
		Map<String, String> serviceTypeDscr = getCommonService().selectLookupCdeAndDscr(Constants.LOOKUP_SERVICETY);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("custstatusaDscr", custstatusaDscr);
		model.put("serviceTypeDscr", serviceTypeDscr);
		mav.addObject("model", model);
	}

}