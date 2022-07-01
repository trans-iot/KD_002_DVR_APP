package tw.util;

import java.io.Closeable;
import java.io.IOException;

public class Base64Util {
	
	public static void close(Closeable io){
    	if(io != null){
    		try {
				io.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	static public String encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];
		String rtStr;

		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		rtStr = new String(out);
		return rtStr;
	}

	static public byte[] decode(String str) {

		char[] data = str.toCharArray();
		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || codes[data[ix]] < 0) {
				--tempLen; // 去除無效的字符

			}
		}
		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3) {
			len += 2;
		}
		if ((tempLen % 4) == 2) {
			len += 1;
		}
		byte[] out = new byte[len];
		int shift = 0;
		int accum = 0;
		int index = 0;

		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];
			if (value >= 0) {
				accum <<= 6;
				shift += 6;
				accum |= value;
				if (shift >= 8) {
					shift -= 8;
					out[index++] = (byte) ((accum >> shift) & 0xff);
				}
			}
		}

		if (index != out.length) {
			throw new Error("數據長度不一致(輸入了 " + index + "字?，但是系統指示有" + out.length + "字?)");
		}
		return out;
	}

	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	static private byte[] codes = new byte[256];

	static {
		for (int i = 0; i < 256; i++) {
			codes[i] = -1;
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			codes[i] = (byte) (i - 'A');
		}
		for (int i = 'a'; i <= 'z'; i++) {
			codes[i] = (byte) (26 + i - 'a');
		}
		for (int i = '0'; i <= '9'; i++) {
			codes[i] = (byte) (52 + i - '0');
		}
		codes['+'] = 62;
		codes['/'] = 63;
	}

	// public static void main(String[] args) throws Exception {
	// String key = new
	// String("customerid=123321231&mdn=0906310435&APTCMS&201506111556");
	// byte[] a = key.getBytes();
	// String b = Base64Util.encode(a);
	// System.out.println(b);
	//
	// byte[] c =
	// Base64Util.decode("eyJvcmRlcklkIjoiR1BBLjEyMzQtNTY3OC05MDEyLTM0NTY3IiwicGFja2FnZU5hbWUiOiJjb20uZXhhbXBsZS5hcHAiLCJwcm9kdWN0SWQiOiJleGFtcGxlU2t1IiwicHVyY2hhc2VUaW1lIjoxMzQ1Njc4OTAwMDAwLCJwdXJjaGFzZVN0YXRlIjowLCJkZXZlbG9wZXJQYXlsb2FkIjoiYkdvYStWN2cveXFEWHZLUnFxK0pURm40dVFaYlBpUUpvNHBmOVJ6SiIsInB1cmNoYXNlVG9rZW4iOiJvcGFxdWUtdG9rZW4tdXAtdG8tMTAwMC1jaGFyYWN0ZXJzIn0=");
	// System.out.println(new String(c, "UTF-8"));
	//
	// //Base64.Encoder encoder = Base64.getEncoder();
	// //System.out.println("@@@:" + Base64.encode(key.getBytes("utf-8"));
	// //System.out.println("@@@:" +
	// encoder.encodeToString(key.getBytes("utf-8")));
	// System.out.println("Base64Util :" +
	// Base64Util.encode(key.getBytes("utf-8")));
	//
	// }
}
