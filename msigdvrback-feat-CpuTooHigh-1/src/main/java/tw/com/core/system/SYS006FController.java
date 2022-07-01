package tw.com.core.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tw.com.core.menu.MenuData;
import tw.com.core.menu.MenuTable;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.Constants;
import tw.msigDvrBack.persistence.TbSysRole;
import tw.spring.ComLogger;
import tw.spring.MyContext;

@Controller
@RequestMapping("/SYS006F")
public class SYS006FController extends BaseController {

	private final static String VIEW = "SYS001M/SYS006F";
	
	@ComLogger
	private Logger logger;

	@Autowired
	private SYS006FService sys006FService;
	
	@Autowired
	private MyContext myContext;
	
	private SYS006FQueryForm queryForm;
	
	@ModelAttribute("queryForm")
    public SYS006FQueryForm initQueryForm() {
		if (queryForm!=null) {
			return queryForm;
		}
		return new SYS006FQueryForm();
	}
	
	/**
	 * 頁面 default 帶出所有的Role清單
	 *
	 * 並且將 AP, MOD, PG List 也一併帶出
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		List<TbSysRole> roleList = sys006FService.querySsoRoleList(null);
		
		List<MenuData> menuList = sys006FService.queryAllList();
		String menuTable = MenuTable.convList(menuList,sys006FService.getModMap());
		
		this.queryForm = null;
		this.queryForm = initQueryForm();
		
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("queryForm", queryForm);
		mav.addObject("roleList", roleList);
		mav.addObject("menuTable", menuTable);
		mav.addObject("cwConstants", Constants.getInstance());
		return mav;
		
	}
	
	@RequestMapping("/ajaxLoad")
	@ResponseBody
	public AjaxForm ajaxLoad(String roleId) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		AjaxResultEnum ajaxResult;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String s = sys006FService.queryPgListByRoleId(roleId);
			//fixed by Marks 2020/09/09
			map.put("listStr", ESAPI.encoder().decodeForHTML(s));
			message = "";
			ajaxResult = AjaxResultEnum.OK;
		}
		catch (Exception e) {
			message = e.getMessage();
			ajaxResult = AjaxResultEnum.FAIL;
		}
		
		ajaxForm.setMessage(message);
		ajaxForm.setResult(ajaxResult);
		ajaxForm.setMap(map);
		return ajaxForm;
	}
	
	@RequestMapping("/ajaxSave")
	@ResponseBody
	public AjaxForm ajaxSave(String roleId, String ids) {
		AjaxForm ajaxForm = new AjaxForm();
		String message = "";
		AjaxResultEnum ajaxResult;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean flag = sys006FService.saveIds(roleId, ids);
			if (flag) {
				message = myContext.getMessage("save.success.label");
				ajaxResult = AjaxResultEnum.OK;
			}
			else {
				message = myContext.getMessage("save.fail.label");
				ajaxResult = AjaxResultEnum.INSERT_FAIL;
			}
		}
		catch (Exception e) {
			logger.error("ajaxSave {}", e.getMessage());
			message = e.getMessage();
			ajaxResult = AjaxResultEnum.FAIL;
		}
		
		ajaxForm.setMessage(message);
		ajaxForm.setResult(ajaxResult);
		ajaxForm.setMap(map);
		return ajaxForm;
	}
}
