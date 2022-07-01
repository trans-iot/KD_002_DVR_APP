package tw.com.core;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import tw.com.exception.CustomValidationException;
import tw.msigDvrBack.common.BaseController;
import tw.msigDvrBack.common.BillObject;
import tw.msigDvrBack.common.CwConstants;
import tw.msigDvrBack.persistence.TbSysUser;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.ExceptionUtils;
import tw.util.OWASPAPI;

/**
 * 模擬登入用
 * 
 * @author Joey Hsu
 * @since 2012/08/16
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

	private final String VIEW = "system/mainFrame";

	@ComLogger
	private Logger logger;

	@Autowired
	private MyContext myContext;

	@Autowired
	private SystemService systemService;

	@Autowired
	private MainService mainService;

	@RequestMapping("/login")
	public ModelAndView login(String username, String password,  HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// 取得登入資訊
		ModelAndView mav = null;
		System.err.println("LOGIN!!");
		try {
			TbSysUser userProf = null;
			
			if(StringUtils.isBlank(username)) {
				throw new Exception(myContext.getMessage("login.user.error.label"));
			}
			//fixed by Marks 2020/10/14 trust boundary
			username = ESAPI.validator().getValidInput("Login-Name", username, "CUSTOMValid", 500, true);
			password = ESAPI.validator().getValidInput("Login-Pd", password, "CUSTOMValid", 500, true);
        	
			userProf = systemService.setUserProfAtLogin(username, password);
			System.err.println("LOGIN2!!");
			if (userProf == null) {
				request.getSession().invalidate();
				logout(request, response);
				return null;
			} else {
				BillObject bo = new BillObject();
				if(userProf != null) {
					bo.setUserProf(userProf);
				}
				if(request != null && bo != null) {
					request.getSession().setAttribute(CwConstants.BO, bo);
				}

				SessionData sessionData = new SessionData();
				//fixed Marks 2020/09/02
				if(request != null) {
					sessionData = getSessionData(username, null, request, session);
				}

				if (sessionData.getRoleId() == null || StringUtils.isBlank(sessionData.getRoleId())) {
					mav = new ModelAndView(new RedirectView("../index.jsp"));
					mav.addObject("errorMessage", myContext.getMessage("login.no.role.id.label"));
					return mav;
				}

				if (super.isDebugMode()) {
					String path = getLocaleUrl(request);
					//fixed by Marks 2020/10/14 Trust Boundary Violation
					path = ESAPI.validator().getValidInput("path", path, "HTTPURL", 500, true);
					sessionData.setDevUrl(path);
				}
				
				//帳號已停權不可登入
				if("S".equals(userProf.getStatus())) {
					mav = new ModelAndView(new RedirectView("../index.jsp"));
					mav.addObject("errorMessage", myContext.getMessage("login.fail.suspension"));
					return mav;
				}
				
				// 帳號已被LOCK
				if("L".equals(userProf.getStatus())) {
					mav = new ModelAndView(new RedirectView("../index.jsp"));
					mav.addObject("errorMessage", myContext.getMessage("login.fail.over.third"));
					return mav;
				}
				
				TbSysUser userProfData = systemService.querySysUser(username);
				//檢核密碼
				if (systemService.updateLoginErr(username,  password, userProf)==null) {
					//帳號登入錯誤3次以上鎖定帳號
					if(userProfData.getLoginFailCnt()>=3) {
						if(!"L".equals(userProfData.getStatus())) {
							userProfData.setStatus("L");
							systemService.updateLoginStatus(userProfData);
						}
						
						mav = new ModelAndView(new RedirectView("../index.jsp"));
						mav.addObject("errorMessage", myContext.getMessage("login.fail.over.third"));
						return mav;
					}
					
					mav = new ModelAndView(new RedirectView("../index.jsp"));
					mav.addObject("errorMessage", myContext.getMessage("login.code.error.label"));
					return mav;
				} else {
					userProfData.setLoginFailCnt((short)0);
					systemService.updateLoginStatus(userProfData);
				}

				if (sessionData.isResetPassword()) {
					logger.debug("redirect to chgPwd.jsp");
					mav = new ModelAndView(new RedirectView("../sso/chgPwd.jsp"));
					return mav;
				}
				
				//fixed by Marks 2020/09/09
				if(request != null) {
					if(sessionData != null) {
						request.getSession().setAttribute(CwConstants.SD, sessionData);
					}
					//fixed by Marks 2020/09/10
					request.getSession().setAttribute(CwConstants.USER_ID, username);
				}

				System.err.println("!!!!sessionData!!!!!!!!" + sessionData.getSysUserId());
				String menu = mainService.getMenuTreeBySessionData(sessionData);

				boolean isAdmin = checkRole(sessionData, CwConstants.ROLE_ADMIN);
				isAdmin = true;
				if (isAdmin) {
					// String path = getLocaleUrl(request);
					// String adminMenu =  MenuTree.getHtmlTree(MenuAdminData.getList(), username,  path);
					// menu += adminMenu;
				}
				
				//edit by Marks 2020/09/14 重導頁防止F5重複登入
				mav = new ModelAndView(new RedirectView("../system/mainFrame.html"));
				return mav;
			}
		} catch (Exception e) {
			mav = new ModelAndView(new RedirectView("../index.jsp"));

			mav.addObject("errorMessage", ExceptionUtils.getErrorMassage(e, myContext.getMessage("sys.message.error")));
			logger.error("[{}] login data error.", username);
			logger.error("[{}] login error.", e.getMessage());
		}
		return mav;
	}
	
	/**
	 * description 登入導頁 防止F5重複登入行為
	 * method redIndex
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mainFrame")
	public ModelAndView redIndex(HttpServletRequest request) {
		ModelAndView mav = null;
		try {
			logger.info("========in mainFrame===========");
			SessionData sessionData = (SessionData) request.getSession().getAttribute(CwConstants.SD);
			String username = (String) request.getSession().getAttribute(CwConstants.USER_ID);
			BillObject bo = (BillObject) request.getSession().getAttribute(CwConstants.BO);
			String menu = mainService.getMenuTreeBySessionData(sessionData);
			mav = new ModelAndView(VIEW);
			mav.addObject("userId", username);
			mav.addObject("userName", bo.getUserProf().getUserName());
			mav.addObject("menu", menu);
		} catch (Exception e) {
			logger.error("========LOGIN MAINFRAME TIMEOUT============");
			mav = new ModelAndView(new RedirectView("../index.jsp"));
			mav.addObject("errorMessage", myContext.getMessage("logout.reason.timeout"));
			logger.error("[{}] login redirect error.", e.getMessage());
		}
		return mav;
	}

	private String getLocaleUrl(HttpServletRequest request) throws Exception {
		if(Objects.isNull(request)) {
			throw new CustomValidationException("empty request");
		}
		
		String requestUrl = request.getRequestURL() != null ? OWASPAPI.decodeForHTML(request.getRequestURL().toString()) : "";
		int idx = StringUtils.indexOf(requestUrl, "/", requestUrl.indexOf(request.getContextPath()) + 1);
		String path = requestUrl.substring(0, idx) + "/sso/main.html";
		logger.debug("path:{}", path);
		return path;
	}

	private SessionData getSessionData(String userId, String devUrl, HttpServletRequest request, HttpSession session) throws Exception {
		SessionData sessionData;
		
		String httpForwardedFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (request.getAttribute(CwConstants.USER_ID) == null) {
			sessionData = mainService.initSessionData(userId);
			String loginIp = "";
			if (StringUtils.isBlank(httpForwardedFor)) {
				loginIp = request.getRemoteAddr();
			} else {
				loginIp = httpForwardedFor;
			}
			
			if(sessionData != null) {
				//fixed by Marks 2020/10/14 trust boundary
	        	loginIp = ESAPI.validator().getValidInput("loginIp", loginIp, "CUSTOMValid", 500, true);
	        	sessionData.setLoginIp(loginIp);
				
				session.setAttribute(CwConstants.SD, sessionData);
			}
		} else {
			sessionData = (SessionData) session.getAttribute(CwConstants.SD);
		}

		if (StringUtils.isNotBlank(devUrl)) {
			logger.info("Entering Development Mode:{}", devUrl);
			//fixed by Marks 2020/10/14 trust boundary
			devUrl = ESAPI.validator().getValidInput("devUrl", devUrl, "HTTPURL", 500, true);
			sessionData.setDevUrl(devUrl);
		}

		return sessionData;
	}

	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().invalidate();

		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "../index.jsp");
	}
}
