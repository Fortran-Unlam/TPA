package Seguridad;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
public abstract class Seguridad 
{
	private static final String KEYBASE = "Bar12345Bar12345";
	private static final SecretKey KEY = new SecretKeySpec(KEYBASE.getBytes(), "AES");

	public static String encrypt(String str)
	{
		byte[] enc = null;
		try
		{
			Cipher ecipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, KEY);
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
		
			// Encrypt
			enc = ecipher.doFinal(utf8);
		}
		catch(Exception e)
		{
			System.out.println("Error to encrypt");
		}
		
		// Encode bytes to base64 to get a string
		return new sun.misc.BASE64Encoder().encode(enc);
	}
	
	public static String decrypt(String str)
	{
		String decrypt = null;
		try
		{
			Cipher dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, KEY);
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] utf8 = dcipher.doFinal(dec);
			decrypt = new String(utf8, "UTF8");
		}
		catch(Exception e)
		{
			System.out.println("Error to decrypt");
		}
		// Decode using utf-8
		return decrypt;
	}
}