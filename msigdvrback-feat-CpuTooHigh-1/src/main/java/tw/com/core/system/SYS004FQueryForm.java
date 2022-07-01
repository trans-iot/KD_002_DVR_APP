package tw.com.core.system;

import java.io.Serializable;

import org.slf4j.Logger;

import tw.msigDvrBack.common.BaseForm;
import tw.spring.ComLogger;

public class SYS004FQueryForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ComLogger
	private Logger logger;
	
	private String pgProjId;
	
	private String pgId;


	public String getPgId() {
		return pgId;
	}

	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	public String getPgProjId() {
		return pgProjId;
	}

	public void setPgProjId(String pgProjId) {
		this.pgProjId = pgProjId;
	}
	
}
