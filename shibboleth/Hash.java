package shibboleth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private final static String KEY_SHA1="SHA-1";
	private final static String KEY_MD5="MD5";
	public Hash(){}
	
	public static  byte[] hashConv(byte[] data) throws Exception{
		if(data==null){
			System.out.printf("UsageError:data is null");
			return null;
		}
		MessageDigest sha=MessageDigest.getInstance(KEY_MD5);
		sha.update(data);
		return sha.digest();
	}
}
