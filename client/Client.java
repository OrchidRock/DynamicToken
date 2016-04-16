package client;


import java.nio.ByteBuffer;

import shibboleth.ByteConvert;
import shibboleth.Hash;
import shibboleth.NowTime;
import shibboleth.PRNG;

public class Client {
	
	private byte[] mysed;
	String myshibboleth;
	PRNG prng;
	//Timer timer;
	public Client(){
		mysed=null;
		prng=new PRNG();
		//timer=new Timer();
	}
	public void start() throws Exception{
		mysed= prng.getOneShibboleth();
	}
	public void reset() throws Exception{
		if(prng==null){
			System.out.printf("UsageError:PRNG is null\n");
			return ;
		}
		//timer.cancel();
		start();
	}
	public void demage(){
		//timer.cancel();
		mysed=null;
		myshibboleth="";
	}
	public String updateMyShibboleth() throws Exception{
		if(mysed==null){
			System.out.printf("UsageError:sed is null\n");
			return null;
		}
		byte[] curr_date=NowTime.getNowOffsetTimeSed(0, mysed.length);
		//System.out.println(curr_date.length+" "+mysed.length);
		
		ByteBuffer byteBuffer=ByteBuffer.allocate(curr_date.length+mysed.length);
		byteBuffer.put(curr_date, 0, curr_date.length);
		byteBuffer.put(mysed, 0, mysed.length);
		byte [] hashResult=Hash.hashConv(byteBuffer.array());
		int [] conv=ByteConvert.byteArrayToIntArray(hashResult);
		myshibboleth="";
		for(int i=0;i<conv.length-2;i++)
			myshibboleth+=conv[i];
		return myshibboleth;
	}
	public String getCurrentSedString(){
		return ByteConvert.hexString(mysed);
	}
	
}
