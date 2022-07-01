/**
 *  基本資料維護設定模組 - 系統維護
 *  設定 SSO_MOD_PROF <~> SSO_PG_PROF <~> SSO_SYSTEM
 *  
 *  
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.core.system;

import java.util.Date;
import java.util.HashMap;
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

import tw.com.core.SessionData;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbSysMod;
import tw.msigDvrBack.persistence.TbSysPg;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;

@Controller
@RequestMapping("/SYS004F")
public class SYS004FController extends BaseController {
	
	private final static String VIEW = "SYS001M/SYS004F";
	private final static String INSERT_VIEW = "SYS001M/SYS004F_M";
	private final static String UPDATE_VIEW = "SYS001M/SYS004F_M";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;
	
	@Autowired
	private SYS004FService sys004FService;
	
	private SYS004FQueryForm queryForm;
	
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
	
	
	@ModelAttribute("queryForm")
    public SYS004FQueryForm initQueryForm() {
		if (queryForm!=null) {
			return queryForm;
		}
		queryForm = new SYS004FQueryForm();
		return new SYS004FQueryForm();
	}
	
	@RequestMapping("/query")
	public ModelAndView query(@ModelAttribute("queryForm")SYS004FQueryForm queryForm) {
		queryForm.setQueryClicked("true");
		
		int totalCount = sys004FService.countTotal(queryForm);

		String checkPage = checkPage(queryForm.getPages(),queryForm.getPerPageNum(),totalCount);
		queryForm.setPages(checkPage);
		
		List<TbSysPg> list = sys004FService.queryList(queryForm);
		
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	@RequestMapping("/insert")
	public ModelAndView showInsertForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginUser = String.valueOf(session.getAttribute(CwConstants.USER_ID));
		
		SYS004FModForm command = new SYS004FModForm();
		command.setAction("insert");
		command.setCrUser(loginUser);
		
		//修改時，產生模組下拉選單
		List<TbSysMod> modList= sys004FService.getModIdList();
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("modList",modList);
		
		ModelAndView mav = new ModelAndView(INSERT_VIEW);
		mav.addObject("model",model);
		mav.addObject("command", command);
		
		return mav;
	}
	
	/**
	 * 新增程式
	 * @param form modForm
	 * @return 
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(SYS004FModForm form) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		String pgId = form.getPgId();
		Date effDate = (CwDateUtils.formatDate(form.getEffDate()));
		TbSysPg key = new TbSysPg();
		key.setPgId(pgId);
		key.setEffDate(effDate);
		
		try{
			int pkCnt = sys004FService.countByPkey(key);
			if(pkCnt!=0){
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("sys004f.ap.id") + " + "
						+ myContext.getMessage("sys004f.pg.id") + " + "
						+ myContext.getMessage("eff.date.label") + " + "
						+ myContext.getMessage("violate.constraint.please.fill.in.again.message");
			}else{
				int addCnt = sys004FService.insertData(form);
				if(addCnt <=0){
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
			logger.error("sys004f insert error {} {}" ,exp.getMessage(),exp.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依Pkey修改模組資料
	 * @param apId 主機代碼
	 * @param pgId 程式代碼
	 * @param effDate 生效日期
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView showUpdateForm(String pgId, HttpServletRequest request){
		TbSysPg key = new TbSysPg();
		key.setPgId(pgId);
		
		TbSysPg tbSysPg = sys004FService.queryByKey(key);
		if(tbSysPg==null){
			//導向錯誤訊息顯示頁面
			return noDataView("/SYS004F/query.html");
		}
		
		//修改時，產生模組下拉選單
		List<TbSysMod> modList= sys004FService.getModIdList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modList",modList);
		
		SYS004FModForm command = transfer(tbSysPg);
		command.setAction("update");
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("model", map);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 更新程式主檔
	 * @param form SYS004FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateDate(SYS004FModForm form){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		try {
			int count = sys004FService.updateData(form);
			if(count > 0){
				ajaxForm.setResult(AjaxResultEnum.OK);
				message = myContext.getMessage("update.success.message");
			}else{
				ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
				message = myContext.getMessage("update.fail.message");
			}
		}catch (Exception e) {
			ajaxForm.setResult(AjaxResultEnum.UPDATE_FAIL);
			message = e.getMessage();
			logger.error("SYS004F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依PK查詢程式主檔
	 * @param apId 主機代碼
	 * @param pgId 模組代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String pgId) {
		TbSysPg key = new TbSysPg();
		key.setPgId(pgId);
		
		TbSysPg tbSysPg = sys004FService.queryByKey(key);
		if(tbSysPg==null){
			//導向錯誤訊息顯示頁面
			return noDataView("/SYS004F/query.html");
		}
		
		//修改時，產生模組下拉選單
		List<TbSysMod> modList= sys004FService.getModIdList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modList",modList);
		
		SYS004FModForm command = transfer(tbSysPg);
		command.setAction("detail");
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("model", map);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 依PK刪除程式主檔
	 * @param pgId 程式代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(String pgId,Date effDate){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		TbSysPg key = new TbSysPg();
		key.setPgId(pgId);
		key.setEffDate(effDate);
		
		int count = sys004FService.deleteByKey(key);
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("CwTbSsoPgProf delete fail Pkey => apId : "+" pgId : "+key.getPgId()+" , effDate : {} ",key.getEffDate());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	private SYS004FModForm transfer(TbSysPg obj){
		SYS004FModForm form = new SYS004FModForm();
		form.setPgProjId(obj.getPgProjId());
		form.setPgId(obj.getPgId());
		form.setPgName(obj.getPgName());
		form.setSeqNo(obj.getSeqNo().toString());
		form.setEffDate(CwDateUtils.transferDate(obj.getEffDate()));
		form.setEndDate(CwDateUtils.transferDate(obj.getEndDate()));
		form.setCrDate(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDate(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		return form;
	}
}