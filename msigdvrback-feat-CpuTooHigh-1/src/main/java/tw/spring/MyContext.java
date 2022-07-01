package tw.spring;

import java.util.Locale;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service
public class MyContext implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@ComLogger
	private Logger logger;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
	
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public String getMessage(String code) {
		return getMessage(code, null, null);
	}
	
	public String getMessage(String code, String[] args) {
		return getMessage(code, args, null);
	}
	
	public String getMessage(String code, String[] args, Locale locale) {
		String s = code;
		try {
		  s = applicationContext.getMessage(code, args, locale);
		}
		catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("can't find code[{}]:{}", code, e.getMessage());
			}
		}
		return s;
	}
}
