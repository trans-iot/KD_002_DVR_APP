/**
 *  Comware Copyright 2017
 *  @since: 1.0 
 *  @author: Kevin Lin
 *  @since: 2017/04/13
 **/
package tw.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

import tw.msigDvrBack.common.Constants;

public class AESUtilDmm {
		
//	public final static int KEY_LEN = 16;  //available choice: 16, 24, 32
	
	/**
	 * AES加密
	 * 
	 * @author Nick 
	 * @since 2018/11/29
	 * @param String
	 * @return String
	 */
    public static String encrypt(String str, String aes, String iv) throws Exception {  
    	
    	byte[] raw = aes.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, Constants.STR_AES);
        Cipher cipher = Cipher.getInstance(Constants.PKCS5PADDING);		//"算法/模式/補碼方式"
        IvParameterSpec nIv = new IvParameterSpec(iv.getBytes());	//使用CBC模式，需要一個向量iv，可增加加密算法的強度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, nIv);
        byte[] encrypted = cipher.doFinal(str.getBytes());
    	
    	return new String(Base64.encode(encrypted));
    }
	
    /**
	 * AES解密
	 * 
	 * @author Nick 
	 * @since 2018/11/29
	 * @param String
	 * @return String
	 */
    public static String decrypt(String str, String aes, String iv) throws Exception {
    		
		byte[] raw = aes.getBytes(Constants.STR_ASCII);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, Constants.STR_AES);
		Cipher cipher = Cipher.getInstance(Constants.PKCS5PADDING);
		IvParameterSpec nIv = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, nIv);
		byte[] encrypted = Base64.decode(str);		//先用base64解密
		
	    byte[] original = cipher.doFinal(encrypted);
	    String originalString = new String(original);
	    return originalString;
    }
   
}
