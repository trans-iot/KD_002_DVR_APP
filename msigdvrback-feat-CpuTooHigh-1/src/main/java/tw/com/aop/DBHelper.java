/**
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.aop;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tw.com.core.SessionData;
import tw.msigDvrBack.common.CwConstants;

@Component
public class DBHelper {

	public SessionData getSessionData() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		SessionData sd = (SessionData)attr.getRequest().getSession().getAttribute(CwConstants.SD);
		return sd;
	}
}
