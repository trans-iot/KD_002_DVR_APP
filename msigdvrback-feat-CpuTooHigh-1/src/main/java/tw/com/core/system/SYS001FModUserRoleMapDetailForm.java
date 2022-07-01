package tw.com.core.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FModUserRoleMapDetailForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SYS001FModUserRoleMapForm>  userRoleMapList = LazyList.decorate(new ArrayList(), FactoryUtils.instantiateFactory(SYS001FModUserRoleMapForm.class));

	public List<SYS001FModUserRoleMapForm> getUserRoleMapList() {
		return userRoleMapList;
	}

	public void setUserRoleMapList(List<SYS001FModUserRoleMapForm> userRoleMapList) {
		this.userRoleMapList = userRoleMapList;
	}

}
