package shibboleth;

public class AES_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AES aes=new AES();
		try {
			byte[] encrypt=aes.aesEncode(new String("yyyyyyyyyy123456").getBytes(),
					new String("HellooHELLLLLLLL").getBytes());
			System.out.printf("%d\n",encrypt.length);
			System.out.printf(new String(encrypt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
