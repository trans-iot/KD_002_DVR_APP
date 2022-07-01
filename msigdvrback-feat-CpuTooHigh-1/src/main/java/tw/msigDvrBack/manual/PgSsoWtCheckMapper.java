package tw.msigDvrBack.manual;

import java.util.HashMap;
import java.util.Map;


public interface PgSsoWtCheckMapper {

	/**
	 * getEncryptString call ASE(password, key)
	 */
	public String getEncryptString(String password);
	
	/**
	 * in : 
	 * pi_user_id
	 * pi_ip_addr
	 * pi_os_user
	 * pi_machine
	 * 
	 * no out
	 * 
	 * @param map
	 */
	
	public void setUser(HashMap<String, String> map);
	
	/**
	 * get current db user
	 * @return
	 */
	public String getLogonUser();

	/**
	 * if login error, login error count + 1
	 * @param userId
	 */
	public void updateLoginErr(String userId);
	
	public Map<String,Object> executeGetLogonUser();
	
}
