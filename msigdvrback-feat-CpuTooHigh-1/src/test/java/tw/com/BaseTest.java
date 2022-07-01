package tw.com;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tw.spring.MyContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application-context.xml", "classpath:test-datasource.xml" })
public class BaseTest {

//	@ComLogger
//	public Logger logger;
//	
	@Autowired
	private static MyContext myContext;
	
	@Value("#{propertyConfigurer['mail.text1']}")
	private String text1;
	@Value("#{propertyConfigurer['mail.text2']}")
	private String text2;
	@Value("#{propertyConfigurer['mail.text3']}")
	private String text3;
	@Value("#{propertyConfigurer['mail.text4']}")
	private String text4;
	@Value("#{propertyConfigurer['mail.text5']}")
	private String text5;
	@Value("#{propertyConfigurer['mail.text6']}")
	private String text6;
	@Value("#{propertyConfigurer['mail.text7']}")
	private String text7;
	@Value("#{propertyConfigurer['mail.text8']}")
	private String text8;
	@Value("#{propertyConfigurer['mail.text9']}")
	private String text9;

	//	@Autowired
//	private CwSsoAccountMapper cwSsoAccountMapper;
//
//	@Test
//	public void _test1() {
//		 String host = "172.19.72.160";
//		 String sharedSecret = "testing123";
//		 
//		 RadiusCheck.STATUS status = RadiusCheck.get(host, sharedSecret).check("DYLAN01", "12345678");
//		 logger.debug("status:{}", status);
//	}
//	
//	
//	@Test
//	public void testDB() {
//		String dbTime = cwSsoAccountMapper.selectDbTime();
//		logger.debug("dbTime : {}", dbTime);
//		Assert.assertNotNull(dbTime);
//	}
//	
//	public SessionData getSD() {
//		SessionData sd = new SessionData();
//		//sd.setUserId("test");
//		//sd.setLoginIp("localhost");
//		return sd;
//	}
	public static void main(String[] args){
		
	}
	
}
