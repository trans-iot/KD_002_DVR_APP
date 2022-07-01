package tw.com.core.system;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

public class SYS006FQueryForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleId;

	private String sourceId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
