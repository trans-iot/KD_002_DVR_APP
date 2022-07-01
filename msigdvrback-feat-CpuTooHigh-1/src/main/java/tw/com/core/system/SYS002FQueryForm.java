package tw.com.core.system;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

public class SYS002FQueryForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projPgId;

	private String pProjPgId;

	public String getProjPgId() {
		return projPgId;
	}

	public void setProjPgId(String projPgId) {
		this.projPgId = projPgId;
	}

	public String getpProjPgId() {
		return pProjPgId;
	}

	public void setpProjPgId(String pProjPgId) {
		this.pProjPgId = pProjPgId;
	}
	
	
}
