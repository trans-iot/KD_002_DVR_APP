package tw.com.core.system;

import java.io.Serializable;

import tw.msigDvrBack.common.BaseForm;

/**
 * SYS001M</br>
 * SYS001FMainForm
 * 
 * @author Olson
 */
public class SYS001FMainForm extends BaseForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SYS001FModForm sys001fModForm;
	
	private SYS001FModUserRoleMapDetailForm sys001fModUserRoleMapDetailForm;
	
	public SYS001FModForm getSys001fModForm() {
		return sys001fModForm;
	}

	public void setSys001fModForm(SYS001FModForm sys001fModForm) {
		this.sys001fModForm = sys001fModForm;
	}

	public SYS001FModUserRoleMapDetailForm getSys001fModUserRoleMapDetailForm() {
		return sys001fModUserRoleMapDetailForm;
	}

	public void setSys001fModUserRoleMapDetailForm(
			SYS001FModUserRoleMapDetailForm sys001fModUserRoleMapDetailForm) {
		this.sys001fModUserRoleMapDetailForm = sys001fModUserRoleMapDetailForm;
	}

}
