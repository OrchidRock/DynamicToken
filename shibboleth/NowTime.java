package shibboleth;
import java.util.Date;

public class NowTime {
	public NowTime(){}
	
	public static byte[] getNowOffsetTimeSed(long offset,int length){
		Date date=getNowOffsetTimeDate(offset);
		//System.out.println("date:getTime()"+date.getTime());
		long nowOffSettimeMinute=(date.getTime())/(60*1000);
		//System.out.println("nowOffSettimeMinute"+nowOffSettimeMinute);
		
		byte[] mysed=ByteConvert.longToByteArray(nowOffSettimeMinute, length);
		return mysed;
	}
	public static long getNowOffsetMilli(long offset){
		Date date=getNowOffsetTimeDate(offset);
		return date.getTime();
	}
	public static Date getNextMinuteStartDate(){
		Date tempdate=new Date();
		long timelong=tempdate.getTime();
		timelong=(timelong/(60*1000)+1)*(60*1000);
		tempdate.setTime(timelong);
		return tempdate;
	}
	
	private static Date getNowOffsetTimeDate(long offset){
		Date date=new Date();
		long curr_date_milie=date.getTime();
		curr_date_milie+=offset;
		date.setTime(curr_date_milie);
		return date;
	}
}
