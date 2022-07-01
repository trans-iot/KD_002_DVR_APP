package tw.spring;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tw.com.core.SessionData;
import tw.msigDvrBack.common.BillObject;
import tw.msigDvrBack.common.CwConstants;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@ComLogger
	private Logger logger;
	
	protected static SecureRandom random = new SecureRandom();

	private synchronized String generateToken() throws IOException {
		long longToken = Math.abs(random.nextLong());
		String random = Long.toString(longToken, 16);
		return random;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, ExecutionException, IOException, IntrusionException, ValidationException {
		logger.debug("Handler: {}, {}", handler, request.getRequestURL());
		
		if (StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "login.html")) {
			request.getSession().setAttribute("csrfPreventionSalt", generateToken());
			return true;
		}
		if (StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "logout.html")) {
			return true;
		}
		if (StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "mainFrame.html")) {
			return true;
		}

		// json
		if (StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/login.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobCareCreate.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobPromoCreate.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobEndUBICreate.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobSyncDevice.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobEmailDriveReport.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobEmailDeviceUnused.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobPush.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/JobGfbDeviceGroup.html")
				|| StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(), "json/getVersion.html")
		// || StringUtils.endsWithIgnoreCase(request.getRequestURL().toString(),
		// "sso/JobCareCreate.html")
		) {
			return true;
		}
		if (request.getMethod().equals("POST")) {
			String token = (String)request.getSession().getAttribute("csrfPreventionSalt");
			String sendToken = request.getParameter("csrfPreventionSalt");
			if(token == null || sendToken == null){
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "../index.jsp");
				return false;
			}
			if(sendToken.equals(token)){
				
			}else{
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "../index.jsp");
				return false;
			}
		}
		
		BillObject bo = (BillObject) request.getSession().getAttribute(CwConstants.BO);
		boolean flag = bo != null ? true : false;
		
		if (!flag) {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "../index.jsp");
			return false;
		}
		request.setAttribute("csrfPreventionSalt", (String)request.getSession().getAttribute("csrfPreventionSalt"));
		HttpSession session = request.getSession();
		SessionData sd = (SessionData) session.getAttribute(CwConstants.SD);
		
		if(sd != null && request != null) {
			//fixed by Marks 2020/10/14 trust boundary
			String sysUserId = ESAPI.validator().getValidInput("userId", sd.getSysUserId(), "CUSTOMValid", 500, true);
			String sysLoginTime = ESAPI.validator().getValidInput("time", sd.getLoginTime(), "CUSTOMValid", 500, true);
			request.setAttribute("userId" , sysUserId);
			request.setAttribute("loginTime", sysLoginTime);
		} 
		return flag;
	}
	
}
