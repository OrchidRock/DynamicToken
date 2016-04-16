package shibboleth;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.sun.org.apache.bcel.internal.util.ByteSequence;
import com.sun.xml.internal.ws.encoding.MtomCodec.ByteArrayBuffer;

public class ByteConvert {
	public static String hexString(byte[] bs){
		String result="";
		for(int i=0;i<bs.length;i++){
			result+=Integer.toHexString(bs[i]&0xFF);
		}
		System.err.println("result.length:"+result.length());
		System.err.println("result"+result);
		return result;
	}
	public static byte[] getByteArrayByHexString(String nm){
		System.err.println("nm:length"+nm.length());
		System.err.println("nm:"+nm);
		if(nm.length()>32){
			System.err.println("nm over bytes");
			return null;
		}
		byte [] result=new byte[nm.length()/2];
		//System.out.println(nm.charAt(0));
		//int i=16-(nm.length()/2);
		for(int i=0;i<nm.length()/2;i++){
			int pos=i*2;
			/*char temp;
			if((pos+1)>=nm.length())
				temp='0';
			else
				temp=nm.charAt(pos+1);*/
			result[i]=(byte) ((charToByte(nm.charAt(pos))<<4)|(charToByte(nm.charAt(pos+1))));
		}
		//for(int i=nm.length()/2;i<16;i++){
		//	result[i]=0x00;
		//}
		return result;
	}
	public static byte[] longToByteArray(long value,int length){
		ByteBuffer byteBuffer=ByteBuffer.allocate(length);
		byteBuffer.putLong(0, value);
		return byteBuffer.array();
	}
	public static byte[] convertIntArrayToByteArray(int [] v){
		byte [] r=new byte[16];
		for(int i=0;i<4;i++){
			r[i*4]=(byte) (v[i]& 0x000000ff);
			r[i*4+1]=(byte)((v[i] & 0x0000ff00)>>8);
			r[i*4+2]=(byte)((v[i] & 0x00ff0000)>>16);
			r[i*4+3]=(byte)((v[i] & 0xff000000)>>24);
		}
		return r;
	}
	public static int[] byteArrayToIntArray(byte[] bs){
		int[] result=new int[bs.length/2];
		for(int i=0;i<bs.length/2;i++){
			result[i]&=0x00000000;
			result[i]=((int)bs[i*2+1] & 0x000000ff);
			result[i]<<=8;
			result[i]|=((int)bs[i*2]& 0x000000ff);
			result[i]%=10;
		}
		return result;
	}
	private static byte charToByte(char c){
		return (byte)"0123456789abcdef".indexOf(c);
	}
}
