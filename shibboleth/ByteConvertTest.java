package shibboleth;

public class ByteConvertTest {

	public static void main(String[] args) {
		System.out.println("Integer.praseInt:"+Integer.parseInt("12234"));
		byte[] test={0x11,0x23,0x7b,0x7d,0x5c};
		String hex=ByteConvert.hexString(test);
		System.out.println("0x"+hex);
		byte[] t=ByteConvert.getByteArrayByHexString(hex);
		System.out.print("byte:");
		for(int i=0;i<t.length;i++)
			System.out.print((int)t[i]+" ");
		System.out.println();
	}

}
