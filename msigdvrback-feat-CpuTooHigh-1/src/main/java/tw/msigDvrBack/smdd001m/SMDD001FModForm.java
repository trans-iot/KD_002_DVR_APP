package tw.msigDvrBack.smdd001m;

import java.io.*;

import tw.msigDvrBack.common.*;

/**
 * 
 * 
 * SMDD001FModForm.java
 * 
 * @author gary
 * @since 2013/1/7
 */
public class SMDD001FModForm extends BaseForm implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moduleCdeDscr;
	private String moduleCde;
	private String sysIndic;
	private String lookupType;
	private String dscr;
	private String valueDscr;
	private String type1Dscr;
	private String type2Dscr;
	private String type3Dscr;
	private String crUser;
	private String crDate;
	private String userstamp;
	private String datestamp;
	private String action;

	public String getType3Dscr() {
		return type3Dscr;
	}

	public void setType3Dscr(String type3Dscr) {
		this.type3Dscr = type3Dscr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModuleCdeDscr() {
		return moduleCdeDscr;
	}

	public void setModuleCdeDscr(String moduleCdeDscr) {
		this.moduleCdeDscr = moduleCdeDscr;
	}

	public String getModuleCde() {
		return moduleCde;
	}

	public void setModuleCde(String moduleCde) {
		this.moduleCde = moduleCde;
	}

	public String getSysIndic() {
		return sysIndic;
	}

	public void setSysIndic(String sysIndic) {
		this.sysIndic = sysIndic;
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	public String getDscr() {
		return dscr;
	}

	public void setDscr(String dscr) {
		this.dscr = dscr;
	}

	public String getValueDscr() {
		return valueDscr;
	}

	public void setValueDscr(String valueDscr) {
		this.valueDscr = valueDscr;
	}

	public String getType1Dscr() {
		return type1Dscr;
	}

	public void setType1Dscr(String type1Dscr) {
		this.type1Dscr = type1Dscr;
	}

	public String getType2Dscr() {
		return type2Dscr;
	}

	public void setType2Dscr(String type2Dscr) {
		this.type2Dscr = type2Dscr;
	}

	public String getUserstamp() {
		return userstamp;
	}

	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp;
	}

	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	public String getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}
}
