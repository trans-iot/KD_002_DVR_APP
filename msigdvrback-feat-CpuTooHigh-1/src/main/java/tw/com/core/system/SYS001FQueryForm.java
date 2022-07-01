package tw.com.core.system;

import java.io.Serializable;
import java.util.Map;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FQueryForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 營運公司
	 */
	private String corpId;
	/**
	 * 使用者帳號
	 */
	private String sysuserId;
	/**
	 * 使用者名稱
	 */
	private String sysUserName;
	/**
	 * 部門
	 */
	private String sysDept;
	/**
	 * 營運公司DDL
	 */
	private Map<String, String> corpIdMap;
	
	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}


	public String getSysuserId() {
		return sysuserId;
	}

	public void setSysuserId(String sysuserId) {
		this.sysuserId = sysuserId;
	}

	public Map<String, String> getCorpIdMap() {
		return corpIdMap;
	}

	public void setCorpIdMap(Map<String, String> corpIdMap) {
		this.corpIdMap = corpIdMap;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getSysDept() {
		return sysDept;
	}

	public void setSysDept(String sysDept) {
		this.sysDept = sysDept;
	}

}
