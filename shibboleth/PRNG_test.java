package shibboleth;

public class PRNG_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PRNG prng=new PRNG();
		try {
			for(int i=0;i<10;i++){
				byte [] sed=prng.getOneShibboleth();
				System.out.printf("the lenth of sed "+i+":"+sed.length);
				System.out.printf("The sed:"+ByteConvert.hexString(sed));
				System.out.printf("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
