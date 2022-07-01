package tw.util;

import org.owasp.esapi.ESAPI;

/**
 * OWASP-解碼/轉碼
 */
public class OWASPAPI {
	private final static char[] CHECK_CHARACTER = { '>', '<', ')', '(', '"', '\'', '%', ';', '#', '&', ';', '+' };

	/**
	 * 解碼
	 * @param input 參數
	 * @return 解碼字串
	 */
	public static String decodeForHTML(String input) {
		return ESAPI.encoder().decodeForHTML(input);
	}

	/**
	 * 轉碼
	 * @param input 參數
	 * @return 轉碼字串
	 */
	public static String encodeForHTML(String input) {
		if (input == null || "".equals(input)) {
			return input;
		}

		final StringBuffer stringBuffer = new StringBuffer();
		final char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (canConvert(chars[i])) {
				stringBuffer.append(ESAPI.encoder().encodeForHTML(String.valueOf(chars[i])));
			} else {
				stringBuffer.append(chars[i]);
			}
		}

		return ESAPI.encoder().encodeForHTML(stringBuffer.toString());
	}

	private static boolean canConvert(char input) {
		for (char checkCharacter : CHECK_CHARACTER) {
			if (input == checkCharacter) {
				return true;
			}
		}
		return false;
	}
}