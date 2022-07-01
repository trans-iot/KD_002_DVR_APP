package tw.com.core.system;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FModUserRoleMapForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String sysUserId;
	
	private String roleId;
	
	private String crUser;
	
	private String crDate;
	
	private String userstamp;
	
	private Date datestamp;

	public String getsysUserId() {
		return sysUserId;
	}

	public void setsysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	public String getUserstamp() {
		return userstamp;
	}

	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}
	
}
