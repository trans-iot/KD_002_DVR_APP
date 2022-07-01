package tw.com.core.system;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FModUserMapForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String apId;
	
	private String sysUserId;
	
	private String crUser;
	
	private String crDate;
	
	private String userstamp;
	
	private Date datestamp;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
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
