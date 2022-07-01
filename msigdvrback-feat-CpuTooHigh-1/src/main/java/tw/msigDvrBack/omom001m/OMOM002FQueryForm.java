package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM002FQueryForm.java
 * 
 * @since 2018/10/31
 * @author Bob
 */
public class OMOM002FQueryForm extends BaseForm implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465427135795991887L;
	/**
	 * 會員編號
	 */
	private String userId;
	/**
	 * 電子信箱
	 */
	private String email;
	/**
	 * 車號
	 */
	private String carNo;
	/**
	 * 使用者姓名
	 */
	private String userName;
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
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	


}