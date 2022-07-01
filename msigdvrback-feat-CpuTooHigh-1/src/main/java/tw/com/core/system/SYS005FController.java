package tw.com.core.system;

import java.util.Date;
import java.util.List;

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

import tw.com.core.SessionData;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.msigDvrBack.persistence.TbSysRoleKey;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;

@Controller
@RequestMapping("/SYS005F")
public class SYS005FController extends BaseController {

	private final static String VIEW = "SYS001M/SYS005F";
	private final static String INSERT_VIEW = "SYS001M/SYS005F_M";
	private final static String UPDATE_VIEW = "SYS001M/SYS005F_M";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private SYS005FService sys005FService;
	
	@Autowired
	private MyContext myContext;
	
	private SYS005FQueryForm queryForm;
	
	@ModelAttribute("queryForm")
    public SYS005FQueryForm initQueryForm() {
		if (queryForm!=null) {
			return queryForm;
		}
		return new SYS005FQueryForm();
	}
	
	@RequestMapping("index")
	public ModelAndView index(HttpSession session) {
		SessionData sessionData = (SessionData)session.getAttribute(CwConstants.SD);
		boolean isAdmin = checkRole(sessionData, CwConstants.ROLE_ADMIN);
		if (isAdmin) {
			
		} 
		this.queryForm = null;
		this.queryForm = initQueryForm();
		
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("queryForm", queryForm);
		return mav;
	}
	
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm")SYS005FQueryForm queryForm) {
		queryForm.setQueryClicked("true");
		int totalCount = sys005FService.countTotal(queryForm);
		
		String checkPage = checkPage(queryForm.getPages(),queryForm.getPerPageNum(),totalCount);
		queryForm.setPages(checkPage);
		
		List<TbSysRole> list = sys005FService.queryList(queryForm);

		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	@RequestMapping("/insert")
	public ModelAndView showInsertForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginUser = String.valueOf(session.getAttribute(CwConstants.USER_ID));
		
		SYS005FModForm command = new SYS005FModForm();
		command.setAction("insert");
		command.setCrUser(loginUser);
		
		ModelAndView mav = new ModelAndView(INSERT_VIEW);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 新增角色
	 * @param form modForm
	 * @return 
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(SYS005FModForm form) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		String roleId = form.getRoleId();
		String effDate = form.getEffDate();
		TbSysRoleKey key = new TbSysRoleKey();
		key.setRoleId(roleId);
		key.setEffDate(CwDateUtils.formatDate(effDate));
		try{
			int pkCnt = sys005FService.countByPkey(key);
			if (pkCnt !=0){
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("sys005f.role.id") + " + "
						+ myContext.getMessage("eff.date.label") + " + "
						+ myContext.getMessage("violate.constraint.please.fill.in.again.message");
			}else{
				int addCount = sys005FService.insertData(form);
				if (addCount <= 0) {
					ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
					message = myContext.getMessage("insert.fail.message");
				} else {
					ajaxForm.setResult(AjaxResultEnum.OK);
					message = myContext.getMessage("insert.success.message");
				}
			}
		}catch(Exception exp){
			ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
			message = exp.getMessage();
			logger.error("sys005f insert error {} {}" ,exp.getMessage(),exp.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依Pkey修改角色資料
	 * @param roleId 主機代碼
	 * @param effDate 生效日期
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView showUpdateForm(String roleId,Date effDate, HttpServletRequest request){
		TbSysRoleKey key = new TbSysRoleKey();
		key.setRoleId(roleId);
		key.setEffDate(effDate);
		
		TbSysRole tbSysRole = sys005FService.queryByKey(key);
		if(tbSysRole==null){
		//導向錯誤訊息顯示頁面
			return noDataView("/SYS005F/query.html");
		}
		
		SYS005FModForm command = transfer(tbSysRole);
		command.setAction("update");
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 更新模組主檔
	 * @param form SYS005FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateDate(SYS005FModForm form){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		
		
		try{
			int count = sys005FService.updateData(form);
			if(count > 0){
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			} else {
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		}catch(Exception e){
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
			logger.error("SYS005F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依PK查詢角色主檔
	 * @param roleId 角色代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String roleId,Date effDate) {
		TbSysRoleKey key = new TbSysRoleKey();
		key.setRoleId(roleId);
		key.setEffDate(effDate);
		TbSysRole tbSysRole = sys005FService.queryByKey(key);
		if(tbSysRole==null){
			//導向錯誤訊息顯示頁面
			return noDataView("/SYS005F/query.html");
		}
		
		SYS005FModForm command = transfer(tbSysRole);
		command.setAction("detail");
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 依PK刪除角色主檔
	 * @param roleId 角色代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(String roleId,Date effDate){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		TbSysRoleKey key = new TbSysRoleKey();
		key.setRoleId(roleId);
		key.setEffDate(effDate);
		
		int count = sys005FService.deleteByKey(key);
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("CwTbSsoRole  Pkey => roleId : "+key.getRoleId()+
					", effDate : {} ",key.getEffDate());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	private SYS005FModForm transfer(TbSysRole obj){
		SYS005FModForm form = new SYS005FModForm();
		form.setRoleId(obj.getRoleId());
		form.setEffDate(CwDateUtils.transferDate(obj.getEffDate()));
		form.setEndDate(CwDateUtils.transferDate(obj.getEndDate()));
		form.setCrUser(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDate(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		
		return form;
	}
}
