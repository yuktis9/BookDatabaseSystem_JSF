package com.bookdata.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionDecryptionAES {
	 
	 static Cipher cipher;
	 static private SecretKey secretKey;

	 static{
		 
		 try {
				KeyGenerator keyGenerator = null;
				keyGenerator = KeyGenerator.getInstance("AES");

				keyGenerator.init(128);
				secretKey = keyGenerator.generateKey();
				cipher = Cipher.getInstance("AES");
			} catch (Exception e) {
				System.out.println(e);
			}
	 }

	public String encrypt(String plainText){
		
		String encryptedText = "";
		try {
			byte[] plainTextByte = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedText = encoder.encodeToString(encryptedByte);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return encryptedText;
	}

	public String decrypt(String encryptedText){
		
		String decryptedText = "";
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] encryptedTextByte = decoder.decode(encryptedText);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			decryptedText = new String(decryptedByte);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return decryptedText;
	}
}