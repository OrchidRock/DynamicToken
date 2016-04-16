package service;

import java.nio.ByteBuffer;
import java.util.HashSet;

import shibboleth.*;

public class Service {
	
	PRNG prng;
	//ashSet<User> users=new HashSet<>();/*The user table*/
	User currUser;
	LocalStore localStore;
	//signal instance
	private static Service instance=null;
	private Service(){
		prng=new PRNG();
		localStore=new LocalStore();
		currUser=null;
	}
	public static Service getInstance(){
		if(instance==null)
			instance=new Service();
		return instance;
	}
	/*public void writeback(){
		if(localStore==null){
			System.out.println("localStore is null");
			return;
		}
		localStore.writeBack();
	}*/
	public boolean registerNewUser(String name,String password) throws Exception{
		if(name.length()<1 || password.length()<1)
			return false;
		User tempUser=new User(name, password);
		
		byte [] sed=prng.getOneShibboleth();
		tempUser.setMySed(sed);
		
		boolean storeResult=localStore.storeNewUser(tempUser);
		if(storeResult){
			currUser=tempUser;
		}
		return storeResult;
	}
	public boolean login(String name,String password){
		User tempUser;
				//new User(name, password.getBytes());
		if((tempUser=localStore.findAndGetOneUser(name, password))!=null){
			currUser=tempUser;
			return true;
		}else
			return false;
	}
	
	/**
	 * distribute dynamic key
	 * @throws Exception 
	 */
	public boolean distribute() throws Exception{
		/*if(currUser==null){
			System.out.println("UsgError:current User is null");
			return false;
		}
		if(currUser.mysed==null){
			byte [] sed=prng.getOneShibboleth();
			currUser.setMySed(sed);
			return true;
		}
		else
			return false;*/
		return true;
	}
	public boolean verification(String shi) throws Exception{
		if(currUser==null ){
			System.out.println("UsgError:current User is null");
			return false;
		}
		if(currUser.getMysed()==null){
			System.out.println("UsgError:current User's sed is null");
			return false;
		}
		
		byte[] curr_date=NowTime.getNowOffsetTimeSed(0, currUser.getMysed().length);
		byte[] mysed=currUser.getMysed();
		System.err.println(ByteConvert.hexString(curr_date));
		System.err.println(ByteConvert.hexString(mysed));
		ByteBuffer byteBuffer=ByteBuffer.allocate(curr_date.length+mysed.length);
		byteBuffer.put(curr_date, 0, curr_date.length);
		byteBuffer.put(mysed,0, mysed.length);
		
		//System.out.println("time_value:"+ByteConvert.hexString(curr_date));
		//System.out.println("curr_date_milles: "+NowTime.getNowOffsetMilli(0));
	
		
		byte [] hashResult=Hash.hashConv(byteBuffer.array());
		int [] hashConv=ByteConvert.byteArrayToIntArray(hashResult);
		/*System.out.print("My shi: ");
		for(int i=0;i<hashConv.length-2;i++)
			System.out.print(Integer.toString(hashConv[i]));
		System.out.println();*/
		
		System.out.println("SHI:"+shi);
		for(int i=0;i<hashConv.length-2;i++){
			System.out.printf("shi.chatAt(%d): %d",i,(int)shi.charAt(i)-48);
			System.out.printf("  hashConv[%d]: %d\n",i,hashConv[i]);
			
			if(hashConv[i]!=((int)shi.charAt(i)-48)){
				return false;
			}
			
		}
		return true;
	}
	public boolean resetSed(){
		if(currUser==null)
			return false;
		try {	
			byte [] sed = prng.getOneShibboleth();
			currUser.setMySed(sed);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("here");
		boolean result=localStore.resetUserSed(currUser);
		return result;
		//writeback();
		//return result;
	}
	public void exit(){
		currUser=null;
		//writeback();
		localStore.clearDom();
	}
	public String getCurrUserName(){
		if(currUser==null){
			System.out.println("UsgError:current User is null");
			return null;
		}
		return currUser.getUserName();
	}
	public boolean getCurrUserKeyState(){
		return !(currUser.getMysed()==null);
	}
	public String getCurrUserSed(){
		return ByteConvert.hexString(currUser.getMysed());
	}
}
