package service;

import java.io.Reader;
import java.util.Scanner;

import shibboleth.ByteConvert;

public class ServerTest {

	public static void main(String[] args) {
		Service service=Service.getInstance();
		try {
			//service.registerNewUser("Rock", "123456");
		/*	if(!service.registerNewUser("rock", "123456")){
				System.out.println("register failed");
				return;
			}*/
			System.out.println("Server is running");
			Scanner scanner=new Scanner(System.in);
			while(true){
				String order=scanner.nextLine().trim();
				if(order.length()==0)
					continue;
				if(order.contains("login")){
					String[] strings=order.split(":");
					if(!service.login(strings[1], strings[2])){
						System.out.println("login failed");
						//break;
					}else{
						System.out.println("login success!");
						System.out.print("User:"+service.getCurrUserName()+" keystate:");
						System.out.printf(service.getCurrUserKeyState()?"true":"false"+"\n");
						System.out.println(" sed:"+service.getCurrUserSed());
						System.out.println("main page:");
					}
				}else if(order.contains("reg")){
					String[] strings=order.split(":");
					if(!service.registerNewUser(strings[1], strings[2])){
						System.out.println("register failed");
						//break;
					}else{
						System.out.println("register success.");
					}
				}
				else if(order.equals("exit")){
					System.out.println("service exit");
					service.exit();
				}
				else if(order.contains("ver")){
					//order.substring(4);
					if(service.verification(order.substring(4))){
						System.out.println("ver success");
					}else{
						System.out.println("ver failed");
					}
				}else if(order.equals("resetSed")){
					if(!service.resetSed())
						System.out.println("reset sed failed.may be you have not login or register");
					else{
						System.out.println("user:"+service.getCurrUserName()+" reset sed success.");
					}
				}
				else if(order.equals("quit")){
					service.exit();
					break;
				}
				else{
					System.out.println("order is invailed");
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
