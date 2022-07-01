/**
 *  @since: 1.0 
 *  @author: alanlin
 **/
package tw.com.core;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SessionData implements Serializable {
	
	private static final long serialVersionUID = 6748784843354576906L;
	
	private String roleId;
	private String userName;
	private String sysUserId;
	private String devUrl;
	private String loginTime;
	private String loginIp;
	
	/**
	 * resetPawdIndic, is not "N" set to true, else false
	 */
	private boolean resetPassword;
	
	private List<String> roleList;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getDevUrl() {
		return devUrl;
	}

	public void setDevUrl(String devUrl) {
		this.devUrl = devUrl;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		if (StringUtils.startsWith(loginIp, "0:0:")) {
			InetAddress address;
			try {
				address = InetAddress.getLocalHost();
				loginIp = address.getHostAddress();
			} catch (UnknownHostException e) {
				loginIp = "127.0.0.1";
			}
		}
		this.loginIp = loginIp;
	}

	public boolean isResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

}
