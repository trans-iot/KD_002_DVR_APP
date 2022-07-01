package tw.com.core.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import tw.msigDvrBack.common.BaseForm;

public class SYS001FModUserMapDetailForm extends BaseForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SYS001FModUserMapForm>  userMapList = LazyList.decorate(new ArrayList(), FactoryUtils.instantiateFactory(SYS001FModUserMapForm.class));
	
	public List<SYS001FModUserMapForm> getUserMapList() {
		return userMapList;
	}

	public void setUserMapList(List<SYS001FModUserMapForm> userMapList) {
		this.userMapList = userMapList;
	}
}
