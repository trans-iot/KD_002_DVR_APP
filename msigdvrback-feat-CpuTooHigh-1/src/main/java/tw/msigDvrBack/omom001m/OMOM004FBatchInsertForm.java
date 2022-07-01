package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM004FBatchInsertForm.java
 * 
 * @since 2018/10/29
 * @author Bob
 */
public class OMOM004FBatchInsertForm extends BaseForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3910105610648021830L;
	/**
	 * 	
	 */
	private String seqNo;
	/**
	 * 	會員編號
	 */
	private String userId;
	/**
	 * 實際推播時間
	 */
	private String pushTime;
	/**
	 * 推播狀態
	 */
	private String pushStatus;
	/**
	 * 
	 */
	private String crUser;
	/**
	 * 
	 */
	private String crDate;
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
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
	
}