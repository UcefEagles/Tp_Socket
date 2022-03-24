package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ServerMt extends Thread {
	
	
	private boolean isActive=true;
	private int nombreClient=0;
	public static void main(String[] args) {
             new ServerMt().start();
		
	}
	@Override
	public void run() {
		try {
			ServerSocket  serverSocket = new ServerSocket(1234);
			System.out.println("Demarage du Server");
			while(true) {
				Socket socket=serverSocket.accept();
				++nombreClient;   
				new Conversation(socket,nombreClient).start();
				
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
