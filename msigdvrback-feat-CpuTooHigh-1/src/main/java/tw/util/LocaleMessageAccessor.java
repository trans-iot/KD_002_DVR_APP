/*
 *   version 1.0
 */
package tw.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 取得 message resource
 * 
 * @author derek_chang
 * @since 2008/2/27 下午 1:06:39
 * 
 */
public class LocaleMessageAccessor {
	//protected final static Logger logger = Logger.getLogger(LocaleMessageAccessor.class);
	//@ComLogger
	//private static Logger logger;
	
	public static MessageSourceAccessor getMessageSourceAccessor() {

		ApplicationContext context = null;
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			ServletContext sc = request.getSession().getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(sc);
		}
		MessageSourceAccessor messageSourceAccessor = null;
		if (context != null) {
			messageSourceAccessor = new MessageSourceAccessor(context);
		} else {
			String[] path = { "/war/WEB-INF/applicationContext.xml", "/war/WEB-INF/newBill-servlet.xml" };
			XmlWebApplicationContext wctx = new XmlWebApplicationContext() {
				/**
				 * Add the ability to read relative FileSystemPaths
				 * 
				 * @see org.springframework.web.context.support.XmlWebApplicationContext#getResourceByPath
				 */
				protected Resource getResourceByPath(String path) {
					if (StringUtils.startsWith(path, "classpath:"))
						return super.getResourceByPath(path);
					// Copied from @see
					// org.springframework.web.context.support.FileSystemApplicationContext#getResourceByPath
					if (path != null && path.startsWith("/")) {
						path = path.substring(1);
					}
					return new FileSystemResource(path);
				}
			};
			wctx.setConfigLocations(path);
			wctx.refresh();
			ResourceBundleMessageSource source = (ResourceBundleMessageSource) wctx.getBean("messageSource");
			messageSourceAccessor = new MessageSourceAccessor(source);
		}
		return messageSourceAccessor;
	}

}
