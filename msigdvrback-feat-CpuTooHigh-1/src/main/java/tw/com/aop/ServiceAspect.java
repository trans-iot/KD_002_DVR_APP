/**
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.aop;

import java.io.FileWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tw.com.core.SessionData;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.spring.ComLogger;
import tw.util.CwDateUtils;

@Configuration
@Lazy
@Component
@Aspect
public class ServiceAspect {
	
	@ComLogger
	private Logger logger;
	
	@Autowired
	private DBHelper dbHelper;
	
	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;
	
	@Pointcut("target(tw.msigDvrBack.common.BaseService))")
	public void doServiceAspect(){}
	
	@Around("doServiceAspect()")
	public Object calculateTime(ProceedingJoinPoint pjp) throws Throwable {
		long current = System.currentTimeMillis();
		Object ret = new Object();
		try {
			ret = pjp.proceed();
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if(msg.contentEquals("Could not open JDBC Connection for transaction")){
				StringBuffer str = new StringBuffer();
				
				SimpleDateFormat sdf = new SimpleDateFormat(CwDateUtils.DATETIME_PATTERN[0]);
				Date date = new Date();
				String now = sdf.format(date);
				
				str.append(now).append(" # ").append(msg);
				
				FileWriter fw = new FileWriter("/usr/java/monitor/need_boot.log",true);
				fw.write('\r');
			    fw.write('\n');   
			    fw.write(str.toString());
			    fw.close();
			}
			throw new Exception(e);
		}
		long finish = System.currentTimeMillis() - current;
		String component = pjp.getSignature().getDeclaringType().getName();
		String method = pjp.getSignature().getName();
		logger.info("Total execution time for {}.{} is: {} ms", new Object[] { component, method, finish });
		return ret;
	}

	@Before("doServiceAspect()")
	public void doDbLogBefore(JoinPoint jp) throws Throwable {
		logger.info("doDbLogBefore..., set user before execute mapper");
		
		String point = jp.toString();
		if (! isNeedAspect(point)) {
			return;
		}
		Object[] args = jp.getArgs();
		logger.debug("point cut at : {}", point);
		logger.debug("args:{}", args);
		
		SessionData sd = dbHelper.getSessionData();
		
//		logger.debug("pi_user_id "+sd.getUserId());
		HashMap<String, String> paramMap = new HashMap<String, String>();
		if (sd != null) {
			paramMap.put("pi_user_id", sd.getSysUserId());
			paramMap.put("pi_ip_addr", sd.getLoginIp());
			paramMap.put("pi_os_user", System.getProperty("user.name"));
			InetAddress localMachine = InetAddress.getLocalHost();
			paramMap.put("pi_machine", localMachine.getHostName());
		}
		pgSsoWtCheckMapper.setUser(paramMap);
	}
	
	@After("doServiceAspect()")
	public void doDbLogAfter(JoinPoint jp) throws Throwable {
		logger.info("doDbLogAfter..., do things after execute mapper");
	}
	
	// 在 application-datasouce.xml 當中設定不是唯獨的路徑
	final String[] checkPaths = {
			"Service.test", 
			"Service.save", 
			"Service.execute", 
			"Service.delete", 
			"Service.del", 
			"Service.update", 
			"Service.create", 
			"Service.insert", 
			"Service.add", 
			"Service.set" 
		};
	
	/**
	 * 檢查不包含 checkPaths 的字串, 則不需要進一步的執行 setUser
	 * 
	 * @param point
	 * @return  true: 需要進一步設定
	 */
	public boolean isNeedAspect(String point) {
		for (String checkPath : checkPaths) {
			if (point.contains(checkPath)) {
				return true;
			}
		}
		return false;
	}
}
