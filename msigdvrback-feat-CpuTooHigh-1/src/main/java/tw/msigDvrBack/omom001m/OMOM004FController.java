/**
 *  營運管理 - 營運管理
 *  最新消息維護
 *  
 *  @since: 1.0 
 *  @author: Bob
 **/
package tw.msigDvrBack.omom001m;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.BaseForm;
import tw.msigDvrBack.common.BillObject;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbNews;
import tw.msigDvrBack.persistence.VwNewsuserCustcar;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.ExceptionUtils;

/**
 * 最新消息維護 OMOM001F_OMOM004F
 * 
 * @author Bob
 * @since 2018/10/26
 */
@Controller
@RequestMapping("/OMOM004F")
public class OMOM004FController extends BaseController {
	private final static String VIEW = "OMOM001M/OMOM004F";
	private final static String UPDATE_VIEW = "OMOM001M/OMOM004F_M";
	private final static String SETTING_VIEW = "OMOM001M/OMOM004F_S";
	private final static String BATCH_INSERT_VIEW = "OMOM001M/OMOM004F_BI";
	private final static String BATCH_FILE_VIEW = "OMOM001M/OMOM004F_FI";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private OMOM004FService omom004FService;

	private OMOM004FQueryForm queryForm;
	private BaseForm detailQueryForm;
	private OMOM004FBatchInsertQueryForm batchInsertQueryForm;

	/**
	 * <pre>
	 * Method Name : initQueryForm
	 * Description : 查詢頁面form
	 * </pre>
	 * 
	 * @since 2018/10/26
	 * @author Bob
	 *
	 * @return OMOM004FQueryForm
	 */
	@ModelAttribute("queryForm")
	public OMOM004FQueryForm initQueryForm() {
		if (this.queryForm != null) {
			return this.queryForm;
		}
		this.queryForm = new OMOM004FQueryForm();
		return this.queryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : initDetailForm
	 * Description : 明細頁面查詢form
	 * </pre>
	 * 
	 * @since 2018/10/26
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
	 * Method Name : initDtlCommandForm
	 * Description : 可編輯明細頁面查詢form
	 * </pre>
	 * 
	 * @since 2018/10/26
	 * @author Bob
	 *
	 * @return BaseForm
	 */
	@ModelAttribute("batchInsertQueryForm")
	public OMOM004FBatchInsertQueryForm initBatchInsertQueryForm() {
		if (this.batchInsertQueryForm != null) {
			return this.batchInsertQueryForm;
		}
		this.batchInsertQueryForm = new OMOM004FBatchInsertQueryForm();
		return this.batchInsertQueryForm;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : index
	 * Description :
	 * </pre>
	 * 
	 * @since 2018/10/26
	 * @author Bob
	 *
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView(VIEW);
		this.queryForm = null;
		this.queryForm = initQueryForm();
		List<TbLookupCde> lookupMsgclassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		mav.addObject("lookupMsgclassList", lookupMsgclassList);
		mav.addObject("lookupPushstaList", lookupPushstaList);
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
	 * @since 2018/10/26
	 * @author Bob
	 *
	 * @param queryForm
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm") OMOM004FQueryForm form) throws Exception {
		ModelAndView mav = new ModelAndView(VIEW);
		queryForm.setQueryClicked("true");
		Long totalCount = this.omom004FService.countTotal(form);
		String checkPage = checkPage(queryForm.getPages(), form.getPerPageNum(), totalCount);
		queryForm.setPages(checkPage);

		List<TbNews> list = this.omom004FService.queryList(form);
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		mav.addObject("lookupPushstaList", lookupPushstaList);
		mav.addObject("lookupMsgclassList", lookupMsgClassList);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : showDetailForm
	 * Description : 查詢明細
	 * </pre>
	 * 
	 * @since 2018/10/26
	 * @author Bob
	 *
	 * @param detailUserId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(Integer detailSeqNo) {

		OMOM004FModForm modForm = omom004FService.queryByKey(detailSeqNo);
		logger.debug("modForm :{}", ToStringBuilder.reflectionToString(modForm));

		modForm.setAction("detail");

		Map<String, Object> model = new HashMap<String, Object>();

		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		mav.addObject("lookupPushstaList", lookupPushstaList);
		mav.addObject("lookupMsgclassList", lookupMsgClassList);
		mav.addObject("model", model);
		mav.addObject("command", modForm);
		return mav;
	}

	@RequestMapping("/update")
	public ModelAndView showUpdateForm(HttpServletRequest request, Integer updateSeqNo) {
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();
		// MasterForm
		OMOM004FModForm omom004fModForm = new OMOM004FModForm();
		omom004fModForm.setUserstamp(loginUser);

		omom004fModForm = omom004FService.queryByKey(updateSeqNo);
		omom004fModForm.setAction("update");
		Map<String, Object> model = new HashMap<String, Object>();
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		mav.addObject("lookupPushstaList", lookupPushstaList);
		mav.addObject("lookupMsgclassList", lookupMsgClassList);
		mav.addObject("model", model);
		mav.addObject("command", omom004fModForm);
		return mav;
	}

	/**
	 * 更新最新消息維護
	 * 
	 * @param form
	 *            OMOM004FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateData(HttpServletRequest request, OMOM004FModForm omom004fModForm) {

		HttpSession session = request.getSession();
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();

		omom004fModForm.setUserstamp(loginUser);
		omom004fModForm.setDatestamp(new java.util.Date());

		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		try {
			// 執行update
			int count = omom004FService.updateData(omom004fModForm);
			if (count > 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage().replace("java.lang.Exception:", "");
			logger.error("OMOM004F update ERROR {} {}", e.getMessage(), e.getStackTrace());
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
	public AjaxForm deleteData(Integer deleteSeqNo) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		int count = omom004FService.deleteByKey(deleteSeqNo);
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("tbNews delete fail Pkey => deleteSeqNo : " + deleteSeqNo);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	@RequestMapping("/setting")
	public ModelAndView showSettingForm(HttpServletRequest request, Integer settingSeqNo, String orderByClause) {
		ModelAndView mav = new ModelAndView(SETTING_VIEW);
		try {
			// MasterForm
			OMOM004FModForm omom004fModForm = new OMOM004FModForm();

			omom004fModForm = omom004FService.queryByKey(settingSeqNo);
			List<VwNewsuserCustcar> vwNewsuserCustcarList = omom004FService.queryVwNewsuserCustcarBySeqNo(settingSeqNo,
					orderByClause);
			Map<String, Object> model = new HashMap<String, Object>();
			List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
			List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
			mav.addObject("lookupPushstaList", lookupPushstaList);
			mav.addObject("lookupMsgclassList", lookupMsgClassList);
			mav.addObject("model", model);
			mav.addObject("command", omom004fModForm);
			mav.addObject("vwNewsuserCustcarList", vwNewsuserCustcarList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping("/insert")
	public ModelAndView showInsertForm(HttpServletRequest request, Integer settingSeqNo, String orderByClause) {
		ModelAndView mav = new ModelAndView(SETTING_VIEW);
		// MasterForm
		OMOM004FModForm omom004fModForm = new OMOM004FModForm();

		omom004fModForm = omom004FService.queryByKey(settingSeqNo);
		List<VwNewsuserCustcar> vwNewsuserCustcarList = omom004FService.queryVwNewsuserCustcarBySeqNo(settingSeqNo,
				orderByClause);
		Map<String, Object> model = new HashMap<String, Object>();
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		mav.addObject("lookupPushstaList", lookupPushstaList);
		mav.addObject("lookupMsgclassList", lookupMsgClassList);
		mav.addObject("model", model);
		mav.addObject("command", omom004fModForm);
		mav.addObject("vwNewsuserCustcarList", vwNewsuserCustcarList);
		return mav;
	}

	@RequestMapping("/batchInsert")
	public ModelAndView showBatchInsertForm(HttpServletRequest request, String seqNo) {
		ModelAndView mav = new ModelAndView(BATCH_INSERT_VIEW);
		this.batchInsertQueryForm = null;
		this.batchInsertQueryForm = initBatchInsertQueryForm();
		mav.addObject("batchInsertQueryForm", this.batchInsertQueryForm);
		if (seqNo != null) {
			mav.addObject("seqNo", seqNo);
		}
		return mav;
	}

	@RequestMapping("/batchInsertQuery")
	public ModelAndView batchInsertQuery(HttpServletRequest request,
			@ModelAttribute("batchInsertQueryForm") OMOM004FBatchInsertQueryForm batchInsertQueryForm, String seqNo) {
		ModelAndView mav = new ModelAndView(BATCH_INSERT_VIEW);
		try {

			List<Map<String, Object>> mapList = omom004FService.queryListByBatchInsertQueryForm(batchInsertQueryForm);
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, String> custstatusaDscr = getCommonService().selectLookupCdeAndDscr(Constants.CU_STA);
			mav.addObject("custstatusaDscr", custstatusaDscr);
			mav.addObject("seqNo", seqNo);
			mav.addObject("model", model);
			mav.addObject("list", mapList);
			mav.addObject("listSize", mapList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * 確認userId是否存在
	 * 
	 * @param checkUserIdExit
	 * @return
	 */
	@RequestMapping(value = "/checkUserIdExit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm checkUserIdExit(String checkedUserId, Integer checkedSeqNo) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";

		Integer count = omom004FService.checkUserIdExit(checkedUserId, checkedSeqNo);
		if (count == 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
		} else {
			ajaxForm.setResult(AjaxResultEnum.QUERY_FAIL);
			logger.debug("checkUserIdExit check fail Pkey => checkedUserId : " + checkedUserId);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	/**
	 * 確認userId是否存在
	 * 
	 * @param checkUserIdExit
	 * @return
	 */
	@RequestMapping(value = "/checkUserIdExitAll", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm checkUserIdExitAll(String checkedUserIdArray, Integer checkedSeqNoAll) {
		checkedUserIdArray = StringEscapeUtils.escapeHtml(checkedUserIdArray);
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		JSONArray array = new JSONArray(checkedUserIdArray);
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray duplicatedArray = omom004FService.checkUserIdExitAll(array, checkedSeqNoAll);
		if (duplicatedArray.length() == 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
		} else {
			ajaxForm.setResult(AjaxResultEnum.QUERY_FAIL);
			map.put("duplicatedArray", duplicatedArray.toString());
			ajaxForm.setMap(map);
			logger.debug("checkUserIdExitAll check fail Pkey => checkedSeqNoAll : " + checkedSeqNoAll);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	/**
	 * 確認userId是否存在
	 * 
	 * @param checkUserIdExit
	 * @return
	 */
	@RequestMapping(value = "/doBatchInsert", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm executeBatchInsert(HttpServletRequest request, String batchInsertArray) {
		HttpSession session = request.getSession();
		String message = "";
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();
		AjaxForm ajaxForm = new AjaxForm();
		try {
			JSONArray array = new JSONArray(batchInsertArray);
			int seqNo = array.getJSONObject(0).getInt("seqNo");
			OMOM004FModForm form = omom004FService.queryByKey(seqNo);
			if (form.getPushStatus().equals("N")) {
				Integer count = omom004FService.executeBatchInsert(array, loginUser);
				if (count > 0) {
					ajaxForm.setResult(AjaxResultEnum.OK);
					message = myContext.getMessage("save.success.label");
				} else {
					ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
					logger.debug("doBatchInsert insert fail Pkey => checkedSeqNoAll : " + array.toString());
				}
			} else {
				message = myContext.getMessage("omom004f.pushStatus.incorrect");
				ajaxForm.setResult(AjaxResultEnum.QUERY_FAIL);
			}
		} catch (Exception e) {
			logger.debug("upload.exception: {}", e.getCause().getMessage());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	@RequestMapping("/fileInsert")
	public ModelAndView showFileInsertForm(HttpServletRequest request, String seqNo) {
		ModelAndView mav = new ModelAndView(BATCH_FILE_VIEW);

		mav.addObject("seqNo", seqNo);
		return mav;
	}

	/**
	 * 確認pushStatus
	 * 
	 * @param checkUserIdExit
	 * @return
	 */
	@RequestMapping(value = "/checkPushStatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm checkPushStatus(HttpServletRequest request, Integer checkSeqNo) {
		String message = "";
		AjaxForm ajaxForm = new AjaxForm();
		try {
			OMOM004FModForm form = omom004FService.queryByKey(checkSeqNo);
			if (form.getPushStatus().equals("N")) {
				ajaxForm.setResult(AjaxResultEnum.OK);
			} else {
				message = myContext.getMessage("omom004f.pushStatus.incorrect");
				ajaxForm.setResult(AjaxResultEnum.QUERY_FAIL);
			}
		} catch (Exception e) {
			logger.debug("upload.exception: {}", e.getCause().getMessage());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

	/**
	 * 執行檔案上傳
	 * 
	 * @param seqNo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doFileInsert", method = RequestMethod.POST)
	public ModelAndView executeFileInsert(HttpSession session, MultipartRequest multiReq, HttpServletRequest request,
			String seqNo) {
		ModelAndView mav = new ModelAndView(BATCH_FILE_VIEW);
		try {
			BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
			String loginUser = bo.getUserProf().getSysUserId();
			// 獲得文件
			MultipartFile multipart = multiReq.getFile("uploadFile");
			// 允許檔案種類如下
			String allowedFileTypes = ".csv .CSV";
			String filename = null;
			String extension = "";
			List<HashMap<String, Object>> map = null;
			if (multipart != null) {
				// 獲得檔名
				filename = multipart.getOriginalFilename();

				// 抓取副檔名比對
				extension = FilenameUtils.getExtension(filename);

				if (allowedFileTypes.indexOf(extension.toLowerCase()) == -1) {
					mav.addObject("errMsg", myContext.getMessage("omom004f.file.insert.file.upload.file.type.error"));
				}
				map = omom004FService.executeFileInsert(multipart, loginUser, seqNo);
			}
			if (map != null) {
				mav.addObject("list", map);
				mav.addObject("disableUpload", true);
			} else {
				logger.debug("doFileInsert insert fail Pkey => seqNo : " + seqNo);
			}
		} catch (Exception e) {
			logger.debug("upload.exception: {}", e.getCause().getMessage());
			mav.addObject("errMsg", e.getCause().getMessage());
		}
		mav.addObject("seqNo", seqNo);
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * Method Name : insert
	 * Description : insert page
	 * </pre>
	 * 
	 * @since 2018/11/02
	 * @author Bob
	 *
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/insertDataPage")
	public ModelAndView insertPage(HttpSession session) {
		BillObject bo = (BillObject) session.getAttribute(CwConstants.BO);
		String loginUser = bo.getUserProf().getSysUserId();
		OMOM004FModForm command = new OMOM004FModForm();
		command.setAction("insert");
		command.setCrUser(loginUser);
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		List<TbLookupCde> lookupPushstaList = omom004FService.getDscrByType(Constants.PUSHSTA);
		List<TbLookupCde> lookupMsgClassList = omom004FService.getDscrByType(Constants.MSGCLASS);
		mav.addObject("lookupPushstaList", lookupPushstaList);
		mav.addObject("lookupMsgclassList", lookupMsgClassList);
		mav.addObject("command", command);
		return mav;
	}

	/**
	 * 新增使用者
	 * 
	 * @param form
	 *            modForm
	 * @return
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(OMOM004FModForm form) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int count = omom004FService.insertData(form);
			if (count > 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("insert.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("insert.fail.message");
			}
		} catch (Exception exp) {
			ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
			message = ExceptionUtils.parseException(exp).replace("java.lang.Exception:", "");
			;
			logger.debug("catch exception : {}{} ", message, exp);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}

}