package tw.com.core.system;

import java.io.Serializable;
import java.util.Date;

import tw.msigDvrBack.common.BaseForm;

public class SYS002FModForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String action;
	
	private String pgProjId;
	
	private String pgProjDscr;
	
	private String pPgProjId;
	
	private String seqNo;
	
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

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getPgProjId() {
		return pgProjId;
	}

	public void setPgProjId(String pgProjId) {
		this.pgProjId = pgProjId;
	}

	public String getPgProjDscr() {
		return pgProjDscr;
	}

	public void setPgProjDscr(String pgProjDscr) {
		this.pgProjDscr = pgProjDscr;
	}

	public String getpPgProjId() {
		return pPgProjId;
	}

	public void setpPgProjId(String pPgProjId) {
		this.pPgProjId = pPgProjId;
	}
	
	
	
}
