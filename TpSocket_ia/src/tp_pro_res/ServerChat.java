package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerChat extends Thread {
	
	
	private boolean isActive=true;
	private int nombreClient=0;
	private List<Conversations> clts=new ArrayList<Conversations>();
	public static void main(String[] args) {
             new ServerChat().start();
		
	}
	@Override
	public void run() {
		try {
			ServerSocket  serverSocket = new ServerSocket(1234);
			System.out.println("Demarage du Server");
			while(true) {
				Socket socket=serverSocket.accept();
				++nombreClient;   
				Conversations conversations=new Conversations(socket,nombreClient);
				clts.add(conversations);
				conversations.start(); 
				
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	class Conversations extends Thread{
		protected Socket socketClient;
		protected int numero;
		public Conversations(Socket socketClient, int numero) {
			this.socketClient = socketClient;
			this.numero = numero;
		}
		public void broadcastMessage(String message,Socket socket,int numeroClient) {
			
			try {
				for(Conversations client:clts) {
					if(client.socketClient!=socket) {
					  if(client.numero==numeroClient || numeroClient==-1) {
						PrintWriter printWriter=new PrintWriter(client.socketClient.getOutputStream(),true);
						printWriter.println(message);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
		    try {
				InputStream inputStream=socketClient.getInputStream();
				InputStreamReader isr =new InputStreamReader(inputStream);
				BufferedReader br =new BufferedReader(isr);
				
				PrintWriter pw=new PrintWriter(socketClient.getOutputStream(),true);
				String ipClient=socketClient.getRemoteSocketAddress().toString();
				pw.println("Bienvenu ,Vous etes le client Numero"+numero);
				System.out.println("Connexion du client numero "+numero+", IP :"+ipClient);
				
				while(true) {
					String req=br.readLine();
					if(req.contains("=>")) {
					String[] requestParams=req.split("=>");
					    if(requestParams.length==2);
						String message=requestParams[1];
						int numeroClient=Integer.parseInt(requestParams[0]);
						broadcastMessage(message,socketClient,numeroClient);
						
					    
					}
					else {
					broadcastMessage(req,socketClient,-1);
					}
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	
}
