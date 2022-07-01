package tw.msigDvrBack.omom001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

/**
 * 
 * 
 * OMOM004FQueryForm.java
 * 
 * @since 2018/10/26
 * @author Bob
 */
public class OMOM004FQueryForm extends BaseForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6916849883273883755L;
	/**
	 * 	消息類別
	 */
	private String msgClass;
	/**
	 * 推播狀態
	 */
	private String pushStatus;
	/**
	 * 消息標題
	 */
	private String title;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMsgClass() {
		return msgClass;
	}

	public void setMsgClass(String msgClass) {
		this.msgClass = msgClass;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}