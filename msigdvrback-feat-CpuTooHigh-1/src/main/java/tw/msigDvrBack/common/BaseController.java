package tw.msigDvrBack.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import tw.com.core.SessionData;
import tw.spring.ComLogger;
import tw.spring.MyContext;


@Configuration
@Lazy
@Controller
public class BaseController {
	
	public final static String ERROR_VIEW = "system/error2";
	
	@ComLogger
	private Logger logger;
	
	@Value("#{propertyConfigurer['cw.enviroment']}")
	private String enviroment;
	
	@Value("#{propertyConfigurer['cw.localhost.debug']}")
	private String localhostDebug;
	
	@Autowired
	private MyContext myContext;
	
	@Autowired
	private CommonService commonService;
	
	public CommonService getCommonService() {
		return commonService;
	}

	/**
	 * 檢查 SessionData 當中的 Role 是否有 checkRole 這個群組
	 * 
	 * 若存在，則可以使該 Controller
	 * 若否，則應該轉到其他頁面 (NOT_ACCESSIBLE)
	 * 
	 * @param sessionData
	 * @param checkRole
	 * @return
	 */
	public boolean checkRole(SessionData sessionData, String checkRole) {
		String user = sessionData.getSysUserId();
		logger.debug("User:{} check role:{}", user, checkRole);
		List<String> roleList = sessionData.getRoleList();
		boolean flag = roleList.contains(checkRole);
		logger.debug("check result : {}" , flag);
		return flag;
	}
	
	public final static String DATE_YYYY_MM_DD = "yyyy/MM/dd";
	
	public String checkPage(String pages, String perPage, long totalCount) {
		int ip = 1;
		int ipp = 10;
		if (StringUtils.isNotBlank(pages)) {
			ip = Integer.parseInt(pages);
		}
		if (StringUtils.isNotBlank(perPage)) {
			ipp = Integer.parseInt(perPage);
		}
		
		int rp = ip -1;
		if ( rp * ipp + 1 > totalCount) {
			int cp = ip == 1 ? 1 : rp;
			return String.valueOf(cp);
		}
		else {
			return pages;
		}
	}
	
	/**
	 * 查無資料(點選明細、修改、複製)時，導向錯誤訊息顯示頁面
	 * @param url 返回查詢頁之url
	 * @return ModelAndView 錯誤訊息顯示頁面
	 */
	public ModelAndView noDataView(String url) {
		ModelAndView mav = new ModelAndView(CwConstants.QUERY_ERR_PAGE);
		mav.addObject(CwConstants.GO_BACK_URL, url);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * Method Name : exceptionHandler
	 * Description : 
	 * </pre>
	 * @since 2013/2/25
	 * @author gary 
	 *
	 * @param e
	 * @return String
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView exceptionHandler(Throwable e) {
		String errMsg = ExceptionUtils.getFullStackTrace(e);
		logger.debug("exceptionHandler: {}", errMsg);
		
		ModelAndView mav = new ModelAndView(ERROR_VIEW);
		mav.addObject("message", this.enviroment.equals("development") ? errMsg : myContext.getMessage("production.error"));
		return mav;
	}
	
	
	public boolean isDebugMode() {
		if (StringUtils.equals(localhostDebug, "true")) {
			return true;
		}
		return false;
	}
	
}
