/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Aug 29, 2012
 **/
package tw.com.core;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import tw.com.exception.CustomValidationException;
import tw.msigDvrBack.common.AjaxForm;
import tw.msigDvrBack.common.AjaxResultEnum;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbSysUserPwdHis;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.ExceptionUtils;
import tw.util.OWASPAPI;

@Controller
@RequestMapping("/sso")
public class MainController extends BaseController {

	@ComLogger
	private Logger logger;
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private SsoService ssoService;
	
	@Autowired
	private MyContext myContext;
	
	private final static String VIEW = "system/mainFrame";
	private final static String CHANGE_PWD_VIEW = "system/chgPwd";
	
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "roleId", required = false)String roleId
			, @RequestParam(value = "devUrl", required = false)String devUrl
			, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		logger.debug("index page");
		
		if(Objects.isNull(request)) {
			throw new CustomValidationException("empty request");
		}
		
		String userId = OWASPAPI.decodeForHTML(request.getRemoteUser());
		SessionData sessionData = getSessionData(userId, devUrl, request, session);
		
		if (sessionData.isResetPassword()) {
			logger.debug("redirect to chgPwd.jsp");
			return changePassword();
		}
		
		String menu = mainService.getMenuTreeBySessionData(sessionData);
		logger.debug("menu:{}", menu);
		boolean isAdmin = checkRole(sessionData, CwConstants.ROLE_ADMIN);
		isAdmin = true;
		if (isAdmin) {
			String requestUrl = request.getRequestURL().toString();
			int idx = StringUtils.indexOf(requestUrl, "/", requestUrl.indexOf(request.getContextPath()) + 1);
			String path = requestUrl.substring(0, idx) + "/sso/main.html";
			logger.debug("path:{}", path);
//			String adminMenu = MenuTree.getHtmlTree(MenuAdminData.getList(), userId, path);
//			menu += adminMenu;
		}
		
		logger.debug("time : {} ", sessionData.getLoginTime());
		
		ModelAndView mav = new ModelAndView(VIEW);
		mav.addObject("menu", menu);
		
		return mav;
	}
	
	private SessionData getSessionData(String userId, String devUrl, HttpServletRequest request, HttpSession session) throws Exception {
		SessionData sessionData ;

		String httpForwardedFor = OWASPAPI.decodeForHTML(request.getHeader("HTTP_X_FORWARDED_FOR"));

		if (request.getAttribute(CwConstants.USER_ID) == null) {
			sessionData = mainService.initSessionData(userId);
			String loginIp = ""; 
	        if (StringUtils.isBlank(httpForwardedFor)) {
	        	//edit by Marks 2020/09/09
	        	loginIp = OWASPAPI.decodeForHTML(request.getRemoteAddr());
	        } else {
	        	loginIp = httpForwardedFor;
	        }
	        
	        //fixed by Marks 2020/10/14 trust boundary
        	loginIp = ESAPI.validator().getValidInput("loginIp", loginIp, "CUSTOMValid", 500, true);
        	sessionData.setLoginIp(loginIp);
			session.setAttribute(CwConstants.BO, sessionData);
		}
		else {
			sessionData = (SessionData)session.getAttribute(CwConstants.BO);
		}
		
		if (StringUtils.isNotBlank(devUrl)) {
			logger.info("Entering Development Mode:{}", devUrl);
			sessionData.setDevUrl(devUrl);
		}
		
		return sessionData;
	}
	
	@RequestMapping("/main")
	public ModelAndView main(String msig, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
//		String userId = request.getRemoteUser();
		String userId = (String)session.getAttribute(CwConstants.USER_ID);
		String[] val = new String[2];
logger.info("==================MSIG ============> {}" + msig);
logger.info("==================MSIG ============> {}" + userId);
		try {
			logger.debug("msig:{}", msig);
			val = ssoService.checkAuth(msig, userId);
		}
		catch (Exception e) {
			logger.error("Authentication exception:{}\r{}", e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView();
//		logger.debug("{}:{}", val[0], val[1]);
		mav.setView(new RedirectView("../"+val[1]+"/index.html", true));
		return mav;
	}

	@RequestMapping("/chgPwd")
	public ModelAndView changePassword() {
		return new ModelAndView(CHANGE_PWD_VIEW);
	}
	
	@RequestMapping("/chgPwdData")
	@ResponseBody
	public AjaxForm chagePwd(String oldpwd, String newpwd, HttpSession session, HttpServletRequest request) {
		AjaxForm ajaxForm = new AjaxForm();
		AjaxResultEnum result = AjaxResultEnum.FAIL;
		String message = "";
		try {
			
			SessionData sd = (SessionData)session.getAttribute(CwConstants.SD);
			boolean flag = mainService.executeChangePassword(oldpwd, newpwd, sd.getSysUserId());
			mainService.executeRefreshTbSysUserPwdHistory(sd.getSysUserId(), newpwd);
			if (flag) {
				message = myContext.getMessage("exe.success.label");
				result = AjaxResultEnum.OK;
				request.getSession().invalidate();
			}
			else {
				message = myContext.getMessage("exe.failure.label");
				result = AjaxResultEnum.FAIL;	
			}
		}
		catch (Exception e) {
			message = ExceptionUtils.getErrorMassage(e, myContext.getMessage("exe.failure.label"));
			result = AjaxResultEnum.UPDATE_FAIL;
		}
		
		ajaxForm.setMessage(message);
		ajaxForm.setResult(result);
		
		return ajaxForm;
	}
	
	/**
	 * method validatePwdHistory
	 * description 會員更改密碼時，5次內不得使用相同的密碼。
	 * <pre>
	 * @param pwd
	 * @author Marks
	 * @since 2020/05/07
	 */
	@RequestMapping("/validatePwdHistory")
	@ResponseBody
	public AjaxForm validatePwdHistory(String pwd, HttpSession session, HttpServletRequest request) throws Exception {
		AjaxForm ajaxForm = new AjaxForm();
		AjaxResultEnum result = AjaxResultEnum.FAIL;
		String message = "";
		try {
			SessionData sd = (SessionData)session.getAttribute(CwConstants.SD);
			logger.debug("sd.getUserId"+sd.getSysUserId());
			List<TbSysUserPwdHis> historyList = mainService.getTbSysUserPwdHis(sd.getSysUserId(), pwd);
			if (historyList.size() == 0) {
				message = myContext.getMessage("exe.success.label");
				result = AjaxResultEnum.OK;
			}
			else {
				message = myContext.getMessage("exe.failure.label");
				result = AjaxResultEnum.FAIL;	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			message = ExceptionUtils.getErrorMassage(e, myContext.getMessage("exe.failure.label"));
			result = AjaxResultEnum.UPDATE_FAIL;
		}
		
		ajaxForm.setMessage(message);
		ajaxForm.setResult(result);
		
		return ajaxForm;
	}
}
