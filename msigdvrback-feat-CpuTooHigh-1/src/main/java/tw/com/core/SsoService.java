/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 19, 2012
 **/
package tw.com.core;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseDao;
import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.TbSysUserMapper;
import tw.msigDvrBack.persistence.TbSysUser;
import tw.spring.ComLogger;
import tw.util.AESUtil;

@Service
public class SsoService extends BaseService {

	@ComLogger
	private Logger logger;
	
	@Autowired
	private TbSysUserMapper tbSysUserMapper;
	
	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 
	 * @param mixData  sysUserId;modId;pgId 用 AES 加密
	 * @param userId  , request.getRemoteUser(), 確保有登入SSO
	 * @return {modId, pgId}
	 * @throws Exception 如果解析失敗，或查無資料會丟出錯誤
	 */
	public String[] checkAuth(String mixData, String userId) throws Exception {
		
		String rawData = AESUtil.decStr(mixData, userId);
//		System.out.println("rawDatarawDatarawData"+rawData);
		logger.info("-----------------------rawDatarawDatarawData:{}", rawData);
//		System.out.println("mixDatamixDatamixData"+mixData);
//		logger.info("-----------------------mixDatamixDatamixData:{}", mixData);
//		System.out.println("userIduserIduserId"+userId);
//		logger.info("-----------------------userIduserIduserId:{}", userId);
		String[] readData = rawData.split(";");
		String sysUserId = readData[0];
		String modId = readData[1];
		String pgId = readData[2];
		
//		logger.debug("{}:{}", userId, readData);
		String[] val = new String[2];
		val[0] = modId;
//		logger.info("-----------------------VAL0:{}", val[0]);
//		System.out.println("-----------------------"+val[0]);
		val[1] = pgId;
		return val;
	}
	
	public void testUpdatePassword(String userId) {
		TbSysUser userProf = tbSysUserMapper.selectByPrimaryKey(userId);
		String encPassword = pgSsoWtCheckMapper.getEncryptString(userProf.getSysUserId());
//		logger.debug("enc password : {}", encPassword);
		userProf.setPwd(encPassword);
		
		String user = pgSsoWtCheckMapper.getLogonUser();
//		logger.debug("current db logon user : {}", user);
		
		tbSysUserMapper.updateByPrimaryKey(userProf);
	}
}
