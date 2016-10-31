package com.bocweb.core.util.encrypt;



import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密与解密
 * String encrypt(String data)对data进行加密,返回String密文 
 * String decrypt(String data)对data进行解密,返回String明文
 */
public class EncryptUtil {
	private static final String defaultKey = "java@des$crypt";
	private final static String DES = "DES";

	public static String md5Hex(String source) {
		return getMD5(source.getBytes());
	}

	private static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static byte[] encrypt(byte[] src, byte[] key) throws RuntimeException {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] decrypt(byte[] src, byte[] key) throws RuntimeException {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 加密
	 * @param 	data	明文
	 * @param 	key		加密密钥
	 * @return	密文
	 */
	public final static String encrypt(String data, String key) {
		if (data != null)
			try {
				return byte2hex(encrypt(data.getBytes(), key.getBytes()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return null;
	}
	/**
	 * 加密
	 * @param 	data	明文
	 * @return	密文
	 */
	public final static String encrypt(String data) {
		if (data != null)
			try {
				return byte2hex(encrypt(data.getBytes(), defaultKey.getBytes()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return null;
	}
	
	/**
	 * 解密
	 * @param 	data	密文
	 * @param 	key		密钥(必须与加密时的密钥一致才能解密)
	 * @return	明文
	 */
	public final static String decrypt(String data, String key) {
		return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
	}
	/**
	 * 解密
	 * @param 	data	密文
	 * @return	明文
	 */
	public final static String decrypt(String data) {
		return new String(decrypt(hex2byte(data.getBytes()), defaultKey.getBytes()));
	}


	public static void main(String[] args) {
		String key = "123423123423";
		String src = "123456";
		
		String s1 = encrypt(src);
		System.out.println(s1);
		s1 = decrypt(s1);
		System.out.println(s1);
		
		src = "test";
		String s2 = encrypt(src);
		System.out.println(s2);
		s2 = decrypt(s2);
		System.out.println(s2);
		
		System.out.println(md5Hex("123456"));
	}
}
