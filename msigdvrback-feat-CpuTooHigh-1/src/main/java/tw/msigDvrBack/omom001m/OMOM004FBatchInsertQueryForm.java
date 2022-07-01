package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM004FBatchInsertQueryForm.java
 * 
 * @since 2018/10/29
 * @author Bob
 */
public class OMOM004FBatchInsertQueryForm extends BaseForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2667896536597004595L;
	/**
	 * 	會員編號
	 */
	private String userId;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 電子信箱
	 */
	private String email;
	/**
	 * 車號
	 */
	private String carNo;
	
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
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	

}