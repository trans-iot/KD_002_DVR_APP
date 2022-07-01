/**
 *  基本資料維護設定模組- 群組維護
 *  設定 SS_USER_ROE <~> SSO_USER_PROF
 *  
 *  
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.core.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.CwDateUtils;

@Controller
@RequestMapping("/SYS002F")
public class SYS002FController extends BaseController {
	
	private final static String VIEW = "SYS001M/SYS002F";
	private final static String INSERT_VIEW = "SYS001M/SYS002F_M";
	private final static String UPDATE_VIEW = "SYS001M/SYS002F_M";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private SYS002FService sys002FService;
	
	@Autowired
	private MyContext myContext;
	
	private SYS002FQueryForm queryForm;
	
	@ModelAttribute("queryForm")
    public SYS002FQueryForm initQueryForm() {
		if (queryForm!=null) {
			return queryForm;
		}
		return new SYS002FQueryForm();
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
	public ModelAndView query(@ModelAttribute("queryForm")SYS002FQueryForm queryForm) {
		queryForm.setQueryClicked("true");
		int totalCount = sys002FService.countTotal(queryForm);
		
		String checkPage = checkPage(queryForm.getPages(),queryForm.getPerPageNum(),totalCount);
		queryForm.setPages(checkPage);
		
		List<TbSysMod> list = sys002FService.queryList(queryForm);

		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	
	@RequestMapping("/insert")
	public ModelAndView showInsertForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginUser = String.valueOf(session.getAttribute(CwConstants.USER_ID));
		
		SYS002FModForm command = new SYS002FModForm();
		command.setAction("insert");
		command.setCrUser(loginUser);
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		List<TbSysMod> list= sys002FService.getModIdList();
		model.put("parentModList", list);
		
		ModelAndView mav = new ModelAndView(INSERT_VIEW);
		mav.addObject("model",model);
		mav.addObject("command", command);
		
		return mav;
	}
	
	
	/**
	 * 新增模組
	 * @param form modForm
	 * @return 
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm insertData(SYS002FModForm form) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		String pPgProjId = form.getpPgProjId(); 
		String pgProjId = form.getPgProjId();
		//將parentModId送到後端檢查是否還有上層模組代碼
		int chkCnt = sys002FService.chkModParent(pPgProjId);
		if(chkCnt>0){
			//上層模組代碼指定錯誤
			ajaxForm.setMessage(myContext.getMessage("sys002f.add.parent.error"));
			return ajaxForm;
		}
		
		TbSysMod key = new TbSysMod();
		key.setPgProjId(pgProjId);
		
		try{
			//檢核是否有相同的主機代碼及模組代碼存在
			int ukCnt = sys002FService.countByapModId(pgProjId);
			if (ukCnt !=0){
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("sys002f.proj.pg.id") + 
						  myContext.getMessage("violate.constraint.please.fill.in.again.message");
				ajaxForm.setMessage(message);
				return ajaxForm;
			}
			
			int pkCnt = sys002FService.countByPkey(key);
			if (pkCnt !=0){
				ajaxForm.setResult(AjaxResultEnum.INSERT_FAIL);
				message = myContext.getMessage("sys002f.proj.pg.id") + " + " +
						  myContext.getMessage("violate.constraint.please.fill.in.again.message");
			}else{
				int addCount = sys002FService.insertData(form);
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
			logger.error("sys002f insert error {} {}" ,exp.getMessage(),exp.getStackTrace());
			exp.printStackTrace();
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	private SYS002FModForm transform(TbSysMod obj){
		SYS002FModForm form = new SYS002FModForm();
		form.setPgProjId(obj.getPgProjId());
		form.setPgProjDscr(obj.getPgProjDscr());
		form.setpPgProjId(obj.getpPgProjId());
		form.setSeqNo(obj.getSeqNo().toString());
		form.setCrUser(obj.getCrUser());
		form.setCrDate(CwDateUtils.transferDate(obj.getCrDate()));
		form.setUserstamp(obj.getUserstamp());
		form.setDatestamp(obj.getDatestamp());
		return form;
	}
	/**
	 * 依Pkey修改模組資料
	 * @param apId 主機代碼
	 * @param modId 模組代碼
	 * @param effDate 生效日期
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView showUpdateForm(String pgProjId){
		
		TbSysMod tbSysMod = sys002FService.queryByKey(pgProjId);
		if(tbSysMod==null){
			//導向錯誤訊息顯示頁面
			return noDataView("/SYS002F/query.html");
		}
		Map<String,Object> model = new HashMap<String,Object>();
		
		//修改時，產生上層模組下拉選單
		List<TbSysMod> list= sys002FService.getModIdList();
		model.put("parentModList", list);
		
		SYS002FModForm command = transform(tbSysMod);
		command.setAction("update");
		
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("model", model);
		mav.addObject("command", command);
		return mav;
	}
	
	/**
	 * 更新模組主檔
	 * @param form SYS002FModForm
	 * @return
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm updateDate(SYS002FModForm form){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		String pPgProjId = form.getpPgProjId(); 
		String pgProjId = form.getPgProjId();

		//將parentModId送到後端檢查是否還有上層模組代碼
		int chkCnt = sys002FService.chkModParent(pPgProjId);
		if(chkCnt>0){
			//上層模組代碼指定錯誤
			ajaxForm.setMessage(myContext.getMessage("sys002f.add.parent.error"));
			return ajaxForm;
		}
		
		int chkPgCnt = sys002FService.chkHasPg(pgProjId);
		if(chkPgCnt>0){
			if(!StringUtils.isNotBlank(pPgProjId)){
				//尚有關聯的程式，無法修改上層模組代碼
				ajaxForm.setMessage(myContext.getMessage("sys002f.edit.parent.error"));
				return ajaxForm;
			}
		}
		
		try {
			
			int count = sys002FService.updateData(form);
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
			logger.error("SYS002F update ERROR {} {}",e.getMessage(),e.getStackTrace());
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
	
	/**
	 * 依PK查詢模組主檔
	 * @param apId 主機代碼
	 * @param modId 模組代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView showDetailForm(String pgProjId) {
		
		TbSysMod  tbSysMod = sys002FService.queryByKey(pgProjId);
		if(tbSysMod==null){
			//導向錯誤訊息顯示頁面
			return noDataView("/SYS002F/query.html");
		}
		SYS002FModForm command = transform(tbSysMod);
		command.setAction("detail");
		
		Map<String,Object> model = new HashMap<String, Object>();
		
		//產生上層模組下拉選單
		List<TbSysMod> list= sys002FService.getModIdList();
		model.put("parentModList", list);
		ModelAndView mav = new ModelAndView(UPDATE_VIEW);
		mav.addObject("command", command);
		mav.addObject("model", model);
		return mav;
	}
	
	/**
	 * 依PK刪除模組主檔
	 * @param apId 主機代碼
	 * @param modId 模組代碼
	 * @param effDate  生效日期
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxForm deleteData(String pgProjId){
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		
		//檢查該模組底下是否有被子模組參照到
		int chkCnt = sys002FService.chkHasChild(pgProjId);
		if(chkCnt>0){
			//尚有關聯的子模組，無法刪除
			ajaxForm.setMessage(myContext.getMessage("sys002f.delete.mod.error"));
			return ajaxForm;
		}
		
		int chkPgCnt = sys002FService.chkHasPg(pgProjId);
		if(chkPgCnt>0){
			//尚有關聯的程式，無法刪除
			ajaxForm.setMessage(myContext.getMessage("sys002f.delete.pg.error"));
			return ajaxForm;
		}
		
		int count = sys002FService.deleteByKey(pgProjId);
		if (count >= 0) {
			ajaxForm.setResult(AjaxResultEnum.OK);
			message = myContext.getMessage("delete.success.message");
		} else {
			ajaxForm.setResult(AjaxResultEnum.DELETE_FAIL);
			message = myContext.getMessage("delete.fail.message");
			logger.debug("tbSysMod delete fail Pkey => pgProjId : "+pgProjId);
		}
		ajaxForm.setMessage(message);
		return ajaxForm;
	}
}
