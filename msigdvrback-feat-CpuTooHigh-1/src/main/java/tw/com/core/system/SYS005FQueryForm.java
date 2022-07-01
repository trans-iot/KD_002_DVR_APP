package tw.com.core.system;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

public class SYS005FQueryForm  extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
