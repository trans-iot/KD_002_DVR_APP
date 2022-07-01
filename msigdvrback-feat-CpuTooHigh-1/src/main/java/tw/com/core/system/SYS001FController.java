/**
 *  基本資料維護設定模組 - 使用者維護
 *  SSO_USER_PROF
 *  
 *  
 *  @since: 1.0 
 *  @author: Olson
 **/
package tw.com.core.system;

import java.util.ArrayList;
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

import tw.com.core.SessionData;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbLookupCde;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.msigDvrBack.persistence.TbSysUserRoleMap;
import tw.msigDvrBack.persistence.VwSysUser;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.ExceptionUtils;

@Controller
@RequestMapping("/SYS001F")
public class SYS001FController extends BaseController {

	private final static String VIEW = "SYS001M/SYS001F";
	private final static String INSERT_VIEW = "SYS001M/SYS001F_M";
	private final static String UPDATE_VIEW = "SYS001M/SYS001F_M";

	@ComLogger
	private Logger logger;

	@Autowired
	private SYS001FService sys001FService;
	
	@Autowired
	private MyContext myContext;
	
	private SYS001FQueryForm queryForm;
	
	@ModelAttribute("queryForm")
    public SYS001FQueryForm initQueryForm() {
		if (queryForm!=null) {
			return queryForm;
		}
		queryForm = new SYS001FQueryForm();
		return queryForm;
	}
	
	@RequestMapping("index")
	public ModelAndView index(HttpSession session) {
		logger.debug("index page");
		SessionData sessionData = (SessionData)session.getAttribute(CwConstants.SD);
		boolean isAdmin = checkRole(sessionData, CwConstants.ROLE_ADMIN);
		if (isAdmin) {
			
		}
		this.queryForm = null;
		this.queryForm = initQueryForm();
		//取得[營運公司]下拉選單
		//queryForm.setCorpIdMap(getCommonService().selectCorpIdByUserId());
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("queryForm", queryForm);
		mav.addObject("deptList", sys001FService.findDeptDscrList());
		return mav;
	}
	
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm")SYS001FQueryForm queryForm) {
		queryForm.setQueryClicked("true");
		int totalCount = sys001FService.countTotal(queryForm);
		
		String checkPage = checkPage(queryForm.getPages(),queryForm.getPerPageNum(),totalCount);
		queryForm.setPages(checkPage);

		List<VwSysUser> list = sys001FService.queryList(queryForm);
		
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("list", list);
		mav.addObject("deptList", sys001FService.findDeptDscrList());
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	@RequestMapping("/insert")
	public ModelAndView showInsertForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginUser = String.valueOf(session.getAttribute(CwConstants.USER_ID));
		
		//MasterForm
		SYS001FModForm sys001fModForm = new SYS001FModForm();
		sys001fModForm.setAction("insert");
		sys001fModForm.setCrUser(loginUser);
		
		//塞入bcIdList deptIdList
		prepareDdlList(sys001fModForm);
		logger.debug("sys001fModForm DDLList getDeptIdList():{}",sys001fModForm.getDeptNoList());
		
		//DetailForm - userRoleMap
		TbSysUserRoleMap tbSysUserRoleMap = new TbSysUserRoleMap();
		SYS001FModUserRoleMapForm sys001fModUserRoleMapForm = sys001FService.transDtlUserRoleMap(tbSysUserRoleMap);
		List<SYS001FModUserRoleMapForm> sys001FModUserRoleMapList = new ArrayList<SYS001FModUserRoleMapForm>();
		sys001FModUserRoleMapList.add(sys001fModUserRoleMapForm);
		
		SYS001FModUserRoleMapDetailForm sys001fModUserRoleMapDetailForm = new SYS001FModUserRoleMapDetailForm();
		sys001fModUserRoleMapDetailForm.setUserRoleMapList(sys001FModUserRoleMapList);
		
		//將所有form傳入mainForm
		SYS001FMainForm mainForm = new SYS001FMainForm();
		mainForm.setSys001fModForm(sys001fModForm);
		mainForm.setSys001fModUserRoleMapDetailForm(sys001fModUserRoleMapDetailForm);
		
		//取得帳號狀態
		List<TbLookupCde> statuslist = sys001FService.getDscrByType(Constants.USER_STATUS);
		
		//取得角色的下拉選單
		List<TbSysRole> roleList = sys001FService.getRoleList();
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("statuslist", statuslist);
		model.put("roleList", roleList);
		
		ModelAndView mav = new ModelAndView(INSERT_VIEW);
		mav.addObject("model",model);
		mav.addObject("command", mainForm);
		
		return mav;
	}
	
	/**
	 * Method Name : prepareDdlList
	 * Description : 新增修改下拉選單資料來源
	 * </pre>
	 * @since 2013/02/22
	 * @author Finn
	 * @param command void
	 */
	private void prepareDdlList(SYS001FModForm command){
		//取得部門
		command.setDeptNoList(this.sys001FService.findDeptDscrList());
	}
	
	/**
	 * 新增使用者
	 * @param form modForm
	 * @return 
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(SYS001FMainForm mainForm) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try{
			int count = sys001FService.insertData(mainForm);
			if (count > 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("insert.success.message");
			}
			else {
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("insert.fail.message");
			}
		}catch(Exception exp){
			ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
			message = ExceptionUtils.parseException(exp).replace("java.lang.Exception:", "");;
			logger.debug("catch exception : {}{} ",message, exp);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依PK刪除使用者
	 * @param userId 使用者帳號
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(String userId) {
		
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		//刪除先檢查是否有detail 如有，則不可刪除
		boolean delFlag = sys001FService.chkChildWhenDel(userId);
		if(delFlag==false){
			ajaxForm.setMessage(myContext.getMessage("sys001f.delete.child.error"));
			return ajaxForm;
		}
		
		int count = sys001FService.deleteByKey(userId);
		
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("CwTbSsoUserProf delete fail Pkey => userId : {}",userId);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依使用者帳號修改資料
	 * @param userId 使用者帳號
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView showUpdateForm(String userId){
		SYS001FMainForm mainForm = sys001FService.queryByKey(userId);
		
		mainForm.getSys001fModForm().setAction("update");
		
		//塞入bcIdList deptIdList
		prepareDdlList(mainForm.getSys001fModForm());
				
		//取得帳號狀態
		List<TbLookupCde> statuslist = sys001FService.getDscrByType(Constants.USER_STATUS);
		
		//取得角色的下拉選單
		List<TbSysRole> roleList = sys001FService.getRoleList();
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("statuslist", statuslist);
		model.put("roleList", roleList);
				
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("model", model);
		mav.addObject("command", mainForm);
		return mav;
	}
	
	@RequestMapping(value="/delUserRoleMapDtl", method=RequestMethod.POST)
	@ResponseBody
	public AjaxForm delUserRoleMapDtl(TbSysUserRoleMap record){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			//執行刪除
			int count = sys001FService.delUserRoleMapDetail(record);
			if (count > 0) {
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("delete.success.message");
			}
			else {
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("detail.delete.fail");
			}
		}catch(Exception exp){
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = ExceptionUtils.parseException(exp);
			logger.debug("catch exception : {}{} ",exp.getMessage(), exp.getStackTrace());
		}
		ajaxForm.setMessage(message);
		
		return ajaxForm;
	}
	
	
	/**
	 * 更新使用者資料
	 * @param form SYS001FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateData(SYS001FMainForm mainForm){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		try {
			//執行update
			int count = sys001FService.updateData(mainForm);
			if(count > 0){
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
			logger.error("SYS001F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依PK查詢使用者資料
	 * @param userId 使用者帳號
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String userId) {
		
		SYS001FMainForm mainForm = sys001FService.queryByKey(userId);
		logger.debug("mainForm :{}", ToStringBuilder.reflectionToString(mainForm));
		
		mainForm.getSys001fModForm().setAction("detail");
		
		//取得帳號狀態
		List<TbLookupCde> statuslist = sys001FService.getDscrByType(Constants.USER_STATUS);
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("statuslist", statuslist);
				
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);

		//塞入bcIdList deptIdList
		prepareDdlList(mainForm.getSys001fModForm());
		
		mav.addObject("model", model);
		mav.addObject("command", mainForm);
		return mav;
	}
	
	/**
	 * 重設使用者密碼
	 * @param form SYS001FModForm
	 * @return
	 */
	@RequestMapping(value = "/resetPw", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm resetPw(String userId){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int cnt = sys001FService.updateUserProf(userId);
			if(cnt > 0){
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
			logger.error("SYS001F update password ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	
	/**
	 * 停權使用者
	 * @param form SYS001FModForm
	 * 
	 * @author Marks
	 * @since 2020/08/20
	 * @return
	 */
	@RequestMapping(value = "/suspend", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm resetPw(SYS001FModForm modForm){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		try {
			int cnt = sys001FService.executeSuspendAccount(modForm.getUserId());
			if(cnt > 0){
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		} catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
			logger.error("SYS001F update password ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
}
