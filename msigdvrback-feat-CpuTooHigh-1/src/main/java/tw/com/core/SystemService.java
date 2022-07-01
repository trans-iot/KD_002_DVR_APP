package tw.com.core;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.msigDvrBack.common.BaseService;
import tw.msigDvrBack.manual.PgSsoWtCheckMapper;
import tw.msigDvrBack.manual.TbSysUserMapper;
import tw.msigDvrBack.persistence.TbSysUser;
import tw.spring.ComLogger;
import tw.spring.MyContext;
import tw.util.OWASPAPI;

@Service
public class SystemService extends BaseService {

	@ComLogger
	private Logger logger;
	
	@Autowired
	private PgSsoWtCheckMapper pgSsoWtCheckMapper;
	
	@Autowired
	private TbSysUserMapper tbSysUserMapper;
	
	@Autowired
	private MyContext myContext;
	
	public TbSysUser setUserProfAtLogin(String sysUserId, String password) throws Exception {
		TbSysUser userProf = null;
		if(StringUtils.isNotBlank(sysUserId)) {
			userProf = tbSysUserMapper.selectByPrimaryKey(sysUserId);
		}
		
		if (userProf==null) {
			throw new Exception(myContext.getMessage("login.user.error.label"));
		} 
		else {
			// this is only for new user;
			if (StringUtils.isBlank(password)) {
				return userProf;
			}
			return userProf;
		}
	}
	
	public TbSysUser updateLoginErr(String userId, String password, TbSysUser userProf) {
		String encPwd = pgSsoWtCheckMapper.getEncryptString(password);
		if (StringUtils.equals(userProf.getPwd(), encPwd)) {
			return userProf;
		}
		else {
			pgSsoWtCheckMapper.updateLoginErr(userId);
			return null;
		}
	}
	
	public TbSysUser querySysUser(String userId) {
		return tbSysUserMapper.selectByPrimaryKey(userId);
	}
	
	public int updateLoginStatus(TbSysUser userProf) {
		int updatecnt = tbSysUserMapper.updateByPrimaryKey(userProf);
		return updatecnt;
	}
}
