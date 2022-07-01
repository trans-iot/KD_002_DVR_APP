/**
 *  基本資料維護設定模組 - 系統維護
 *  參考資料代碼維護查詢
 *  
 *  
 *  @since: 1.0 
 *  @author: gary
 **/
package tw.msigDvrBack.smdd001m;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.common.BillObject;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbLookupType;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;
import tw.util.ExceptionUtils;

/**
 * 參考資料代碼維護查詢
 * SMDD001F_SMDD001F
 * 
 * @author Gary
 * @since 2013/01/03
 */
@Controller
@RequestMapping("/SMDD001F")
public class SMDD001FController extends BaseController 
{
	private final static String VIEW = "SMDD001M/SMDD001F";
	private final static String UPDATE_VIEW = "SMDD001M/SMDD001F_M";
	private final static String CRUD_DETAIL_VIEW = "SMDD001M/SMDD001FD_M";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private SMDD001FService smdd001FService;
	
	private SMDD001FQueryForm queryForm;
	private BaseForm detailQueryForm;
	private BaseForm crudDetailQueryForm;

	/** 
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * @since 2013/1/4
	 * @author gary 
	 *
	 * @return SMDD001FQueryForm 
	 */
	@ModelAttribute("queryForm")
	public SMDD001FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		this.queryForm = new SMDD001FQueryForm();
		return this.queryForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : initDetailForm
	 * Description : 明細頁面查詢form
	 * </pre>
	 * @since 2013/1/8
	 * @author gary 
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
	 * @since 2013/1/9
	 * @author gary 
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
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param session
	 * @return ModelAndView 
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = null;
		this.queryForm = initQueryForm();
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
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param queryForm
	 * @return ModelAndView 
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") SMDD001FQueryForm queryForm) {
		queryForm.setQueryClicked("true");
		int totalCount = this.smdd001FService.countTotal(queryForm);
		String checkPage = checkPage(queryForm.getPages(), queryForm.getPerPageNum(), totalCount);
		queryForm.setPages(checkPage);

		List<Map<String, Object>> list = this.smdd001FService.getLookupData(queryForm);
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : insert
	 * Description : lookupType insert page
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param session
	 * @return ModelAndView 
	 */
	@RequestMapping("/insert")
	public ModelAndView insertPage(HttpSession session) {
		BillObject bo = (BillObject)session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();
		SMDD001FModForm command = new SMDD001FModForm();
		command.setAction("insert");
		command.setCrUser(loginUser);
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("command", command);
		mav.addObject("moduleCdeList", this.smdd001FService.getModuleCdeData());
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : insertData
	 * Description : lookupType insert action
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param modForm
	 * @return AjaxForm 
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(SMDD001FModForm modForm, HttpSession session) {
		logger.debug("insertData.modForm: " +  ToStringBuilder.reflectionToString(modForm, ToStringStyle.MULTI_LINE_STYLE));
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			// 依PK查詢新增資料是否已存在
			int cnt = this.smdd001FService.countByPK(modForm.getLookupType().toUpperCase());
			if (cnt != 0) {
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("smdd001f.header1.label") + 
						  myContext.getMessage("violate.constraint.please.fill.in.again.message");
			} else {
				String loginUser = ((BillObject)session.getAttribute(CwConstants.BO)).getUserProf().getSysUserId();
				String now = CwDateUtils.getTodaytime();
				modForm.setCrUser(loginUser);
				modForm.setCrDate(now);
				modForm.setUserstamp(loginUser);
				modForm.setDatestamp(now);
				int insertCnt = this.smdd001FService.insertLookupTypeData(modForm);
				if (insertCnt < 0) {
					ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
					message = myContext.getMessage("insert.fail.message");
				} else {
					ajaxForm.setResult(AjaxResultEnum.OK);
					message = myContext.getMessage("insert.success.message");
				}
			}
		} catch(Exception e) {
			ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
			message = e.getMessage();
			logger.error("insertData {}", e.getMessage());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : updatePage
	 * Description : lookupType update page
	 * </pre>
	 * @since 2013/1/11
	 * @author gary 
	 *
	 * @param lookupType
	 * @return ModelAndView 
	 */
	@RequestMapping("/update")
	public ModelAndView updatePage(@RequestParam("seq") String lookupType) {
		logger.debug("updatePage.lookupType: " + lookupType);
		return showPageData(lookupType, "update");
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : updatePage
	 * Description : lookupType detail page
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param lookupType
	 * @return ModelAndView 
	 */
	@RequestMapping("/detail")
	public ModelAndView detailPage(
			@RequestParam("seq") String lookupType,
			@ModelAttribute("detailQueryForm") BaseForm detailQueryForm) {
		logger.debug("detailPage.detailQueryForm: " + ToStringBuilder.reflectionToString(detailQueryForm, ToStringStyle.MULTI_LINE_STYLE));
		ModelAndView mav = this.showPageData(lookupType, "detail");
		mav.addObject("totalCount", this.smdd001FService.countDetailDataByLookupType(lookupType));
		mav.addObject("list", this.smdd001FService.getDetailDataByLookupType(lookupType, detailQueryForm, true));
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : showPageData
	 * Description : private method for [detailPage, updatePage]
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param lookupType
	 * @param actionType
	 * @return ModelAndView 
	 */
	private ModelAndView showPageData(String lookupType, String actionType) {
		TbLookupType tbLookupType = this.smdd001FService.getByPK(lookupType);
		if (tbLookupType == null) {
			// 導向錯訊息顯示頁面
			return noDataView("/SMDD001F/query.html");
		}
		
		SMDD001FModForm command = this.transfer(tbLookupType);
		command.setAction(actionType);
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("command", command);
		List<TbLookupCde> moduleCdeList = this.smdd001FService.getModuleCdeData();
		mav.addObject("moduleCdeList", moduleCdeList);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : updateData
	 * Description : lookupType update
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param modForm
	 * @return AjaxForm 
	 */
	@RequestMapping("/updateData")
	@ResponseBody
	public AjaxForm updateData(SMDD001FModForm modForm) {
		logger.debug("updateData.modForm: " + ToStringBuilder.reflectionToString(modForm, ToStringStyle.MULTI_LINE_STYLE));
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int count = this.smdd001FService.updateData(modForm);
			if (count > 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		} catch (Throwable e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : deleteData
	 * Description : delete lookupType associated lookupCde
	 * </pre>
	 * @since 2013/1/11
	 * @author gary 
	 *
	 * @param lookupType
	 * @return AjaxForm 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(@RequestParam("seq") String lookupType) {
		logger.debug("deleteData.lookupType: " + lookupType);
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int detailCnt = this.smdd001FService.deleteDetailBySeq(lookupType);
			int count = this.smdd001FService.deleteBySeq(lookupType);
			if (detailCnt >= 0 && count >= 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("delete.success.message");
			}
		} catch(Exception e) {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail");
			logger.debug("delete fail: ", lookupType);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : hasDetail
	 * Description : detect if has detail data for specific lookupType
	 * </pre>
	 * @since 2013/1/8
	 * @author gary 
	 *
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/hasDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm hasDetail(@RequestParam("seq") String lookupType) {
		logger.debug("lookupType: " + lookupType);
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			long cnt = this.smdd001FService.countDetailDataByLookupType(lookupType);
			Map<String, Object> map = new HashMap<String, Object>();
			if (cnt > 0) {	
				map.put("hasDetail", "true");
			} else {
				map.put("hasDetail", null);
			}
			ajaxForm.setResult(AjaxResultEnum.OK);
			ajaxForm.setMap(map);
		} catch(Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : crudDetailPage
	 * Description : to editable detail page
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/crudDetail")
	public ModelAndView crudDetailPage(
			@RequestParam("seq") String lookupType,
			@ModelAttribute("crudDetailQueryForm") BaseForm crudDetailQueryForm, 
			HttpSession session) {
		logger.debug("crudDetailPage.crudDetailQueryForm: " + ToStringBuilder.reflectionToString(crudDetailQueryForm, ToStringStyle.MULTI_LINE_STYLE));
		TbLookupType tbLookupType = this.smdd001FService.getByPK(lookupType);
		if (tbLookupType == null) {
			return noDataView("/SMDD001F/query.html");
		}
		String loginUser = ((BillObject)session.getAttribute(CwConstants.BO)).getUserProf().getSysUserId();
		crudDetailQueryForm.setQueryClicked("true");
		
		SMDD001FModForm command = this.transfer(tbLookupType);
		ModelAndView mav = new ModelAndView(CRUD_DETAIL_VIEW);
		// 明細編輯
		command.setAction("update");
		List<TbLookupCde> beanList = this.smdd001FService.getDetailDataByLookupType(lookupType, crudDetailQueryForm, true);
		List<SMDD001FModLookupCdeForm> list = new ArrayList<SMDD001FModLookupCdeForm>();
		for (TbLookupCde bean : beanList) {
			list.add(transfer(bean));
		}
		mav.addObject("loginUser", loginUser);
		mav.addObject("command", command);
		mav.addObject("moduleCdeList", this.smdd001FService.getModuleCdeData());
		mav.addObject("totalCount", this.smdd001FService.countDetailDataByLookupType(lookupType));
		mav.addObject("list", list);
		return mav;
	}
	/**
	 * 
	 * <pre>
	 * Method Name : crudDetailUpdate
	 * Description : editable detail page upadte action
	 * </pre>
	 * @since 2013/1/9
	 * @author gary 
	 *
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/crudDetailUpdate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm crudDetailUpdate(
			SMDD001FCrudDetailFormContext crudDetailForm,
			HttpSession session) {
		logger.debug("crudDetailUpdate.crudDetailForm: {}", ToStringBuilder.reflectionToString(crudDetailForm, ToStringStyle.MULTI_LINE_STYLE));
		for (SMDD001FModLookupCdeForm form : crudDetailForm.getCrudDetailForm()) {
			logger.debug("crudDetailForm.getCrudDetailForm(): {}", ToStringBuilder.reflectionToString(form, ToStringStyle.MULTI_LINE_STYLE));
		}
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		List<SMDD001FModLookupCdeForm> dataList = crudDetailForm.getCrudDetailForm();
		try {
			String loginUser = ((BillObject)session.getAttribute(CwConstants.BO)).getUserProf().getSysUserId();
			this.smdd001FService.updateBatch(this.myContext, loginUser, dataList);
			message = this.myContext.getMessage("update.success.message");
			ajaxForm.setResult(AjaxResultEnum.OK);
		} catch(Exception e) {
			logger.debug(ExceptionUtils.getFullStackTrace(e));
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = ExceptionUtils.getErrorMassage(e, this.myContext.getMessage("update.fail.message"));
		}
		ajaxForm.setMessage(message);
		// 明細編輯
		return ajaxForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : deleteDetailData
	 * Description : delete 1 lookupCde record
	 * </pre>
	 * @since 2013/1/11
	 * @author gary 
	 *
	 * @param lookupType
	 * @param lookupCde
	 * @return AjaxForm 
	 */
	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteDetailData(
			@RequestParam("lookupType") String lookupType,
			@RequestParam("lookupCde") String lookupCde) {
		logger.debug("deleteDetailData.lookupType: " + lookupType + ", lookupCde: " + lookupCde);
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int detailCnt = this.smdd001FService.deleteDetailDataByTypeAndCde(lookupType, lookupCde);
			if (detailCnt >= 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("delete.success.message");
			}
		} catch(Exception e) {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail");
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	/**
	 * 
	 * <pre>
	 * Method Name : transfer
	 * Description : CwTbLookupType to SMDD001FModForm
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param tbLookupType
	 * @return SMDD001FModForm 
	 */
	private SMDD001FModForm transfer(TbLookupType tbLookupType) {
		SMDD001FModForm modForm = new SMDD001FModForm();
		modForm.setLookupType(tbLookupType.getLookupType());
		modForm.setDscr(tbLookupType.getDscr());
		modForm.setValueDscr(tbLookupType.getValueDscr());
		modForm.setType1Dscr(tbLookupType.getType1Dscr());
		modForm.setType2Dscr(tbLookupType.getType2Dscr());
		modForm.setType3Dscr(tbLookupType.getType3Dscr());
		modForm.setCrUser(tbLookupType.getCrUser());
		modForm.setCrDate(CwDateUtils.transferDatetime(tbLookupType.getCrDate()));
		modForm.setUserstamp(tbLookupType.getUserstamp());
		modForm.setDatestamp(CwDateUtils.transferDatetime(tbLookupType.getDatestamp()));
		modForm.setSysIndic(tbLookupType.getSysIndic());
		return modForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : transfer
	 * Description : TbLookupCde to SMDD001FModLookupCdeForm
	 * </pre>
	 * @since 2013/1/7
	 * @author gary 
	 *
	 * @param tbLookupType
	 * @return SMDD001FModForm 
	 */
	private SMDD001FModLookupCdeForm transfer(TbLookupCde tbLookupCde) {
		SMDD001FModLookupCdeForm form = new SMDD001FModLookupCdeForm();
		form.setDscr(tbLookupCde.getDscr());
		form.setValue(tbLookupCde.getValue() != null ? new BigDecimal(tbLookupCde.getValue().toString()).setScale(6, RoundingMode.HALF_DOWN).toString() : "");
		form.setType1(tbLookupCde.getType1());
		form.setType2(tbLookupCde.getType2());
		form.setType3(tbLookupCde.getType3());
		form.setCrUser(tbLookupCde.getCrUser());
		form.setCrDate(tbLookupCde.getCrDate());
		form.setUserstamp(tbLookupCde.getUserstamp());
		form.setDatestamp(CwDateUtils.transferDate(tbLookupCde.getDatestamp()));
		form.setLookupType(tbLookupCde.getLookupType());
		form.setLookupCde(tbLookupCde.getLookupCde());
		return form;
	}
}