package tw.com.core.menu;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MenuData {

	private String apId;
	private String sysUserId;
//	private String systemId;
	private String sysUrl;
	private int sysOrder;
	private String sysName;
	private String modId;
	private String modName;
	private String pmId;
	private String parentModName;
	private int parentModOrder;
	private int modOrder;
	private String pgId;
	private String pgName;
	private int pgOrder;

	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

//	public String getSystemId() {
//		return systemId;
//	}

//	public void setSystemId(String systemId) {
//		this.systemId = systemId;
//	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public int getSysOrder() {
		return sysOrder;
	}

	public void setSysOrder(int sysOrder) {
		this.sysOrder = sysOrder;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	public String getParentModName() {
		return parentModName;
	}

	public void setParentModName(String parentModName) {
		this.parentModName = parentModName;
	}

	public int getModOrder() {
		return modOrder;
	}

	public void setModOrder(int modOrder) {
		this.modOrder = modOrder;
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

	public int getPgOrder() {
		return pgOrder;
	}

	public void setPgOrder(int pgOrder) {
		this.pgOrder = pgOrder;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int getParentModOrder() {
		return parentModOrder;
	}

	public void setParentModOrder(int parentModOrder) {
		this.parentModOrder = parentModOrder;
	}
}
