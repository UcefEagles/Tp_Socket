package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Conversation extends Thread{
	private Socket socketClient;
	private int numero;
	public Conversation(Socket socketClient, int numero) {
		this.socketClient = socketClient;
		this.numero = numero;
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
				String reponse="Length= "+req.length();
				pw.println(reponse);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
