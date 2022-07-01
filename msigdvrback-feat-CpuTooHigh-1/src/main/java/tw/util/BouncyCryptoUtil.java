/**
 *  @since: 1.0 
 *  @author: alanlin
 *  @since: Sep 20, 2012
 **/
package tw.util;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class BouncyCryptoUtil {

	private static byte[] cipherData(PaddedBufferedBlockCipher cipher,byte[] data) throws Exception {
		int minSize = cipher.getOutputSize(data.length);
		byte[] outBuf = new byte[minSize];
		int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
		int length2 = cipher.doFinal(outBuf, length1);
		int actualLength = length1 + length2;
		byte[] result = new byte[actualLength];
		System.arraycopy(outBuf, 0, result, 0, result.length);
		return result;
	}

	public static byte[] decrypt(byte[] cipher, byte[] key) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
		aes.init(false, new KeyParameter(key));
		return cipherData(aes, cipher);
	}

	public static byte[] encrypt(byte[] plain, byte[] key) throws Exception {
		PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
		aes.init(true, new KeyParameter(key));
		return cipherData(aes, plain);
	}
}
