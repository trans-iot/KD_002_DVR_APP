package tw.msigDvrBack.omom001m;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;

/**
 * 
 * 
 * OMOM004FModForm.java
 * 
 * @since 2018/10/22
 * @author Bob
 */
public class OMOM004FModForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2692852658459217759L;
	/**
	 * 
	 */
	private Integer seqNo;
	/**
	 * 消息類別
	 */
	private String msgClass;
	/**
	 * 預計推播時間
	 */
	private String prePushTime;
	/**
	 * 
	 */
	private String prePushTimeDscr;
	/**
	 * 消息標題
	 */
	private String title;
	/**
	 * 消息內容
	 */
	private String content;
	/**
	 * 連結url
	 */
	private String linkUrl;
	/**
	 * 連結url
	 */
	private String csPhone;
	/**
	 * 推播狀態
	 */
	private String pushStatus;
	/**
	 * 推播狀態描述
	 */
	private String pushStatusDscr;
	/**
	 * 實際推播時間
	 */
	private String pushTime;
	/**
	 * 生效日
	 */
	private String effDate;
	/**
	 * 失效日
	 */
	private String endDate;
	/**
	 * 修改人員
	 */
	private String userstamp;
	/**
	 * 修改日期
	 */
	private Date datestamp;

	/**
	 */
	private String action;

	private String crUser;

	private String crDate;

	public String getPrePushTimeDscr() {
		return prePushTimeDscr;
	}

	public void setPrePushTimeDscr(String prePushTimeDscr) {
		this.prePushTimeDscr = prePushTimeDscr;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getMsgClass() {
		return msgClass;
	}

	public void setMsgClass(String msgClass) {
		this.msgClass = msgClass;
	}

	public String getPrePushTime() {
		return prePushTime;
	}

	public void setPrePushTime(String prePushTime) {
		this.prePushTime = prePushTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPushStatusDscr() {
		return pushStatusDscr;
	}

	public void setPushStatusDscr(String pushStatusDscr) {
		this.pushStatusDscr = pushStatusDscr;
	}

	public String getCsPhone() {
		return csPhone;
	}

	public void setCsPhone(String csPhone) {
		this.csPhone = csPhone;
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
