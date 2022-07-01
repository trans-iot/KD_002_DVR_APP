package tw.msigDvrBack.smdd001m;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SMDD003FMilageQueryForm implements Serializable {

	private String userId;
	
	private String userName;
	
	private String email;
	
	/**
	 * 車號
	 */
	private String carNo;
	/**
	 * 日期起始日
	 */
	private String dateStart;
	/**
	 * 日期結束日
	 */
	private String dateEnd;
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
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
