package tw.msigDvrBack.omom001m;

import tw.msigDvrBack.common.BaseForm;
import tw.mybatis.Page;

import java.io.Serializable;

/**
 * 
 * 
 * OMOM007FQueryForm.java
 * 
 * @since 2020/06/11
 * @author mingkun
 */
public class OMOM007FQueryForm extends BaseForm implements Serializable {

	
	private static final long serialVersionUID = 4329501834527212729L;
	/**
	 * 未使用天數
	 */
	private String unusedDays;

	private Page page;

	private String day;

	public String getUnusedDays() {
		return unusedDays;
	}

	public void setUnusedDays(String unusedDays) {
		this.unusedDays = unusedDays;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}