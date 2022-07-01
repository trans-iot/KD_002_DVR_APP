package tw.msigDvrBack.omom001m;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import tw.msigDvrBack.persistence.TbDevice;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;

/**
 * 設備資料查詢
 * OMOM001F_OMOM003F
 * 
 * @author Bob
 * @since 2018/10/24
 */
@Controller
@RequestMapping("/OMOM003F")
public class OMOM003FController extends BaseController 
{
	private final static String VIEW = "OMOM001M/OMOM003F";
	private final static String UPDATE_VIEW = "OMOM001M/OMOM003F_M";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private OMOM003FService omom003FService;
	
	private OMOM003FQueryForm queryForm;
	
	private BaseForm detailQueryForm;

	/** 
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * @since 2018/10/22
	 * @author Bob 
	 *
	 * @return OMOM005FQueryForm 
	 */
	@ModelAttribute("queryForm")
	public OMOM003FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		this.queryForm = new OMOM003FQueryForm();
		return this.queryForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : initDetailForm
	 * Description : 明細頁面查詢form
	 * </pre>
	 * @since 2018/10/22
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
	 * Method Name : index
	 * Description : 
	 * </pre>
	 * @since 2018/10/22
	 * @author Bob 
	 *
	 * @param session
	 * @return ModelAndView 
	 * @throws Exception 
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = new OMOM003FQueryForm();
		this.queryForm = initQueryForm();
		List<TbLookupCde> detyplist = omom003FService.getDscrByType(Constants.DETYP);
		List<TbLookupCde> destaList = omom003FService.getDscrByType(Constants.DESTA);
		mav.addObject("detyplist", detyplist);
		mav.addObject("destaList", destaList);
		mav.addObject("queryForm", this.queryForm);
		mav.addObject("totalCount", new Integer(0));
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : query
	 * Description : 查詢
	 * </pre>
	 * @since 2018/10/22
	 * @author Bob 
	 *
	 * @param queryForm
	 * @return ModelAndView 
	 * @throws Exception 
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") OMOM003FQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(VIEW);
		queryForm.setQueryClicked("true");
		Long totalCount = this.omom003FService.countTotal(form);
		String checkPage = checkPage(queryForm.getPages(), form.getPerPageNum(), totalCount);
		queryForm.setPages(checkPage);
	
		List<TbDevice> list = this.omom003FService.queryList(form);
		List<TbLookupCde> detyplist = omom003FService.getDscrByType(Constants.DETYP);
		List<TbLookupCde> destaList = omom003FService.getDscrByType(Constants.DESTA);
		mav.addObject("detyplist", detyplist);
		mav.addObject("destaList", destaList);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : showDetailForm
	 * Description : 明細頁
	 * </pre>
	 * @since 2018/10/22
	 * @author Bob 
	 *
	 * @param detailuserId
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String detailDeviceType,String detailImei,String detailSn,String detailResult,String detailErrMessage) throws Exception {
		
		OMOM003FModForm modForm = omom003FService.queryByKey(detailDeviceType,detailImei,detailSn);
		logger.debug("modForm :{}", ToStringBuilder.reflectionToString(modForm));

		modForm.setAction("update");
		// 取得 會員狀態
		Map<String, String> custstatusaDscr = getCommonService().selectLookupCdeAndDscr(Constants.CU_STA);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("detailErrMessage", detailErrMessage);
		model.put("detailResult", detailResult);
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("custstatusaDscr",custstatusaDscr);
		mav.addObject("model",model);
		mav.addObject("command", modForm);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : checkDeviceStatus
	 * Description : 確認deviceStatus
	 * </pre>
	 * 
	 * @since 2018/10/22
	 * @author Bob
	 *
	 * @param omom003FRomForm
	 * @return AjaxForm
	 */
	@RequestMapping(value = "/checkDeviceStatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm checkDeviceStatus(HttpServletRequest request, OMOM003FRomForm omom003FRomForm){
		HttpSession session = request.getSession();
		BillObject bo = (BillObject)session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		try {
			OMOM003FModForm modForm = omom003FService.queryByKey(omom003FRomForm.getDevice_type(),omom003FRomForm.getImei(), omom003FRomForm.getSn());
			if(!modForm.getDeviceStatus().equals("BINDING")){
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("omom003f.already.remove.device");
			}else {
				ajaxForm.setResult(AjaxResultEnum.OK);
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage().replace("java.lang.Exception:", "");
			logger.error("SMDD003F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : checkDrviceStatus
	 * Description : 執行解除綁定
	 * </pre>
	 * @since 2018/10/22
	 * @author Bob 
	 *
	 * @param omom003FRomForm
	 * @return AjaxForm
	 */
	@RequestMapping(value = "/removeDevice", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm removeDevice(HttpServletRequest request, OMOM003FRomForm omom003FRomForm){
		
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		try {
			SimpleDateFormat format = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
			omom003FRomForm.setTrx_time(format.format(new Date()));
			//執行update
			Map<String,String> map = omom003FService.executeRemoveDevice(omom003FRomForm);
			Map<String,Object> errorMap = new HashMap<String,Object>();
			if(map.get("errCde")!= null && map.get("errCde").equals("00")){
				omom003FService.updateTbDevice(omom003FRomForm);
				errorMap.put("result", AjaxResultEnum.OK);
			} else {
				errorMap.put("result", AjaxResultEnum.FAIL);
				errorMap.put("errMessage",map.get("errMsg"));
			}
			ajaxForm.setResult(AjaxResultEnum.OK);
			ajaxForm.setMap(errorMap);
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.FAIL);
			message = e.getMessage();
			logger.error("SMDD003F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
}