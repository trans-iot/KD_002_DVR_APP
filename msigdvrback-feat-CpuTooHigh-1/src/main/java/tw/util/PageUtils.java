package tw.util;

import org.apache.commons.lang.StringUtils;

public class PageUtils {

	public static int getPageInt(String str) {
		if (StringUtils.isBlank(str)) {
			return -1;
		}
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) {
			return -1;
		}
	}
}
