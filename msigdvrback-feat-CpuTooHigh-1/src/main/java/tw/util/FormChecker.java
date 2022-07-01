/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Mar 12, 2013
 **/
package tw.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FormChecker {

	/**
	 * If form is empty, return <b>false</b>; if form is set any value, return
	 * <b>true</b>
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean isFormContainValue(Object obj) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {
				Object value = (Object) m.invoke(obj);
				if (value != null) {
					return true;
				}
			}
		}
		return false;
	}
}
