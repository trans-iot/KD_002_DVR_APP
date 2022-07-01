package tw.msigDvrBack.smdd001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

public class SMDD003FQueryForm extends BaseForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * user id
	 */
	private String user_id;

	/**
	 * 中文姓名
	 */
	private String user_name;

	/**
	 * 電子信箱
	 */
	private String email;

	/**
	 * 身分證號
	 */
	private String cuid;

	/**
	 * 會員狀態
	 */
	private String cust_status;
	/**
	 * 註冊日期起日
	 */
	private String registerTimeBegin;
	/**
	 * 註冊日期迄日
	 */
	private String registerTimeEnd;

	private Page page;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getCust_status() {
		return cust_status;
	}

	public void setCust_status(String cust_status) {
		this.cust_status = cust_status;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getRegisterTimeBegin() {
		return registerTimeBegin;
	}

	public void setRegisterTimeBegin(String registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}

	public String getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(String registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

}