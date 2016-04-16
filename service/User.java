package service;
import java.util.Arrays;

import shibboleth.ByteConvert;
import shibboleth.Hash;
class User {
	private static byte[] salt={-12,12,34,56};
	String username;
	String passwordHex;
	byte[] mysed;
	//int myid;
	public User(){
		mysed=null;
		passwordHex="";
		username="";
		//myid=0;
	}
	public User(String name,String pass){
		setMyName(name);
		setMyPasswordByUser(pass);
		mysed=null;
		//myid=0;
	}
	public void setMyPasswordByUser(String pass){
		try {
			String passAndSalt=pass+new String(salt);
			byte[] password=Hash.hashConv(passAndSalt.getBytes());
			passwordHex=ByteConvert.hexString(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setMyPasswordByLocal(String pass){
		passwordHex=pass;
	}
	public void setMyName(String n){
		username=n;
	}
	public void setMySed(byte[] sed){
		mysed=sed;
	}
	public byte[] getMysed() {
		return mysed;
	}
	public String getUserName(){
		return username;
	}
	public String getUserPassword(){
		return passwordHex;
	}
	/*public boolean setId(int id){
		if(id<=0){
			System.out.println("id is not allowed");
			return false;
		}
		myid=id;
		return true;
	}
	public int getMyId() {
		return myid;
	}*/
	@Override
	public boolean equals(Object otherObject){
		if(this==otherObject)
			return true;
		if(otherObject==null)
			return false;
		if(getClass()!=otherObject.getClass())
			return false;
		if(!(otherObject instanceof User))
			return false;
		User other=(User)otherObject;
		
		return username.equals(other.username) && passwordHex.equals(other.passwordHex);
	}
}
