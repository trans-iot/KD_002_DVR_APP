package tw.com.core.system;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FModForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String action;
	
	private String userId;
	
	private String userName;
	
	private String email;
	
	//password
	private String keyPass;
	
	private String resetPawdIndic;
	
	private String effDate;
	
	private String endDate;
	
	private Short loginFailCnt;
	
	private String status;
	
	private String crUser;
	
	private String crDate;
	
	private String userstamp;
	
	private Date datestamp;
	//部門
	private String deptNo;
	private Map<String, String> deptNoList;
	

	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKeyPass() {
		return keyPass;
	}

	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}

	public String getResetPawdIndic() {
		return resetPawdIndic;
	}

	public void setResetPawdIndic(String resetPawdIndic) {
		this.resetPawdIndic = resetPawdIndic;
	}

	public Short getLoginFailCnt() {
		return loginFailCnt;
	}

	public void setLoginFailCnt(Short loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public Map<String, String> getDeptNoList() {
		return deptNoList;
	}

	public void setDeptNoList(Map<String, String> deptNoList) {
		this.deptNoList = deptNoList;
	}

	
}
