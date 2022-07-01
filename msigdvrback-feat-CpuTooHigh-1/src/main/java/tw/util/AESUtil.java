/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 19, 2012
 **/
package tw.util;

import org.bouncycastle.util.encoders.Base64;

public class AESUtil {

    public static void main(String[] args) throws Exception {  
    	test2();
    }  
    
    public static void test2() throws Exception {
    	String text = "123This is a very very very long sentence.AES.This is a very very very long sentence.AES.This is a very very very long sentence.";
    	String skey = "alanlin";
    	String encStr = encStr(text, skey);
    	
    	String decStr = decStr(encStr, skey);
    	
    }
    
    public final static int KEY_LEN = 24;  //available choice: 16, 24, 32
    private static byte[] fillBlock(byte[] bs) {
    	byte[] newbs = null;
    	for (int i=0; i<KEY_LEN; i++) {
    		if (bs.length>KEY_LEN) {
    			continue;
    		}
    		if (bs.length<KEY_LEN) {
	    		newbs = new byte[KEY_LEN];
	    		for (int j=0; j<newbs.length; j++) {
	    			newbs[j] = j < bs.length ? bs[i] : 0 ;
	    		}
	    		if (newbs.length==KEY_LEN) {
	    			break;
	    		}
    		}
    	}
    	return newbs;
    }
    
    public static String encStr(String plainText, String key) throws Exception {
    	byte[] bb = fillBlock(key.getBytes());
    	byte[] data=BouncyCryptoUtil.encrypt(plainText.getBytes(), bb);
    	return new String(Base64.encode(data));
    }
    
    public static String decStr(String encryptText, String key) throws Exception {
    	byte[] bb = fillBlock(key.getBytes());
    	byte[] data=BouncyCryptoUtil.decrypt(Base64.decode(encryptText), bb);
    	return new String(data);
    }
    
}  