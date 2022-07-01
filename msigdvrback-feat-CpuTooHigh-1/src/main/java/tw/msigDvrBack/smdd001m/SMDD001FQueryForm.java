package tw.msigDvrBack.smdd001m;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

public class SMDD001FQueryForm extends BaseForm implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 425404733606258085L;
	
	/**
	 * 參考資料種類
	 */
	private String lookupType;
	/**
	 * 名稱
	 */
	private String dscr;
	
	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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
}