package shibboleth;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.Init;


class AES {
	//private static final String KEY_ALGORITHM="AES";
	private static SecretKey secretKey;
	private static byte[] iv=new byte[]{-12,35,-25,65,45,-87,95,-22
			,-15,45,55,-66,32,-4,85,55};
	
	private static Cipher cipher;
	private static AlgorithmParameterSpec parameterSpec;
	
	
	public AES(){}
	public byte[] aesEncode(byte[] key,byte[] mstr) throws Exception{
		
		getSecreKeyAndAps(key);
		
		
		cipher=Cipher.getInstance("AES/CBC/NoPadding");
		//secretKey=KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		//secretKey=new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
		//cipher.init
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,parameterSpec);
		byte [] encrypt=cipher.doFinal(mstr);
		
		return encrypt;
	}
	private void getSecreKeyAndAps(byte[] mykey) throws NoSuchAlgorithmException{	
		//KeyGenerator kgen;
		//kgen=KeyGenerator.getInstance("AES");
		//kgen.init(128, new SecureRandom(mykey));
		//kgen.init(128);
		secretKey=new SecretKeySpec(mykey, "AES");
		//kgen.ini
		parameterSpec=new IvParameterSpec(iv);
	}
}
