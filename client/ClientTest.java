package client;

import java.util.Timer;
import java.util.TimerTask;


import shibboleth.ByteConvert;
import shibboleth.NowTime;

public class ClientTest {

	public static void main(String[] args) {
		Client client=new Client();
		Timer timer=new Timer();
		try {
			client.start();
			//updateMyShibboleth(bs);
			System.out.println("Key Has Startup:");
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						String rString=client.updateMyShibboleth();
						System.out.println(rString);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, NowTime.getNextMinuteStartDate(), 60*1000);
			System.out.printf("Sed:"+client.getCurrentSedString()+"\n");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
