package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM002FRemoveDeviceQueryForm.java
 * 
 * @since 2018/10/31
 * @author Bob
 */
public class OMOM002FLovQueryForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196348402750610897L;
	/**
	 * 會員編號
	 */
	private String userId;
	/**
	 * 電子信箱
	 */
	private String email;
	/**
	 * 中文姓名
	 */
	private String userName;
	/**
	 * 手機
	 */
	private String mobilePhone;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}