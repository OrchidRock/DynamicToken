package shibboleth;

public class PRNG {
	//final private static int sedUpdateLim=10;//When output sedUpdateLim shibboleths
										//this class will change the sed.
	private static int KEY_BYTE_LENGTH=16;
	private byte[] key;//AES key link with current date
	AES aes;
	private int[] V;
	//private int currSedUpdateCount=0;
	public PRNG(){
		//getNewKeySed();
		V=new int[4];
		V[0]=0xba27f5a4;
		V[1]=0xed1e2ea8;
		V[2]=0x6176b728;
		V[3]=0x4c89af49;
		aes=new AES();
	}
	private void getNewKeySed(){
		key=NowTime.getNowOffsetTimeSed(0, KEY_BYTE_LENGTH);
	}
	public byte[] getOneShibboleth() throws Exception{
		//System.out.printf(new String(key)+"\n");
		getNewKeySed();
		System.out.println("key:"+ByteConvert.hexString(key));
		vIncrement();
		byte[] out_block=aes.aesEncode(key,ByteConvert.convertIntArrayToByteArray(V));
		return out_block;
	}
	private void vIncrement(){
		V[0]++;
		if(V[0]==0)
			V[1]++;
		else return;
		if(V[1]==0)
			V[2]++;
		else return;
		if(V[2]==0)
			V[3]++;
	}
}
