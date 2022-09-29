package com.bin.cal.aes;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_AES {
	
	public String transformation = "AES/CBC/PKCS5Padding";
	private String key ="01234567890123456789012345678901";
	private String iv = key.substring(0,16);
	
	
	public String encrypt(String txt) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeySpec keySec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParameter = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySec, ivParameter);
		byte[] encrypted = cipher.doFinal(txt.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public String decrypt(String cryptedTxt) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeySpec keySec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParameter = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySec, ivParameter);		
		byte[] decrypted =  Base64.getDecoder().decode(cryptedTxt);
		byte[] decrypteded = cipher.doFinal(decrypted);
		return new String(decrypteded,"UTF-8");
	}

}
