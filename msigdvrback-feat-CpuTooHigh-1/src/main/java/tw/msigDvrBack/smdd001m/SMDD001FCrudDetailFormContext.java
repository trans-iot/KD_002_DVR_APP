package tw.msigDvrBack.smdd001m;

import java.io.*;
import java.util.*;

import org.apache.commons.collections.*;
import org.apache.commons.collections.list.*;

import tw.msigDvrBack.common.*;

/**
 * 
 * 
 * SMDD001FCrudDetailForm.java
 * @author gary
 * @since 2013/1/9
 */
public class SMDD001FCrudDetailFormContext extends BaseForm implements Serializable
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SMDD001FModLookupCdeForm> crudDetailForm = LazyList.decorate(new ArrayList(), FactoryUtils.instantiateFactory(SMDD001FModLookupCdeForm.class));

	public List<SMDD001FModLookupCdeForm> getCrudDetailForm() {
		return crudDetailForm;
	}

	public void setCrudDetailForm(List<SMDD001FModLookupCdeForm> crudDetailForm) {
		this.crudDetailForm = crudDetailForm;
	}
}
