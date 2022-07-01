package tw.com.core.system;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;

public class SYS004FModForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String action;
	
	
	private String pgProjId;
	
	private String pgId;
	
	private String pgName;
	
	private String seqNo;
	
	private String effDate;
	
	private String endDate;
	
	private String crUser;
	
	private String crDate;
	
	private String userstamp;
	
	private Date datestamp;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPgId() {
		return pgId;
	}

	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
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

	public String getPgProjId() {
		return pgProjId;
	}

	public void setPgProjId(String pgProjId) {
		this.pgProjId = pgProjId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
}
