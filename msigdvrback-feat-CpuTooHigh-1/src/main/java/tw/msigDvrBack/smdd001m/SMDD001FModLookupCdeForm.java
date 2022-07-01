package tw.msigDvrBack.smdd001m;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * SMDD001FModLookupCdeForm.java
 * @author gary
 * @since 2013/1/9
 */
public class SMDD001FModLookupCdeForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3018347115401934284L;
	private String lookupType;
	private String lookupCde;
	private String dscr;
	private String value;
	private String type1;
	private String type2;
	private String type3;
	private String crUser;
	private Date crDate;
	private String userstamp;
	private String datestamp;
	private String isNew;
	private String detailModifyCheck;

	public String getDetailModifyCheck() {
		return detailModifyCheck;
	}
	public void setDetailModifyCheck(String detailModifyCheck) {
		this.detailModifyCheck = detailModifyCheck;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getLookupType() {
		return lookupType;
	}
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}
	public String getLookupCde() {
		return lookupCde;
	}
	public void setLookupCde(String lookupCde) {
		this.lookupCde = lookupCde;
	}
	public String getDscr() {
		return dscr;
	}
	public void setDscr(String dscr) {
		this.dscr = dscr;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	public String getCrUser() {
		return crUser;
	}
	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}
	public Date getCrDate() {
		return crDate;
	}
	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}
	public String getUserstamp() {
		return userstamp;
	}
	public void setUserstamp(String userstamp) {
		this.userstamp = userstamp;
	}
	public String getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}
}
