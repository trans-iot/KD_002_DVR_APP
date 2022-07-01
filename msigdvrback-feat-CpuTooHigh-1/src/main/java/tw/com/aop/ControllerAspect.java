/**
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tw.spring.ComLogger;

@Configuration
@Lazy
@Component
@Aspect
public class ControllerAspect {

	@ComLogger
	private Logger logger;
	
	@Pointcut("target(tw.msigDvrBack.common.BaseController))")
	public void doControllerAspect(){}
	
	@Around("doControllerAspect()")
	public Object calculateTime(ProceedingJoinPoint pjp) throws Throwable {
		long current = System.currentTimeMillis();
		Object ret = pjp.proceed();
		long finish = System.currentTimeMillis() - current;
		String component = pjp.getSignature().getDeclaringType().getName();
		String method = pjp.getSignature().getName();
		logger.info("Total execution time for {}.{} is: {} ms", new Object[] { component, method, finish });
		
		return ret;
	}
	
	@Before("doControllerAspect()")
	public void doDbLogBefore(JoinPoint jp) throws Throwable {
		logger.debug("sign:{}", jp.getSignature());
	}

}
