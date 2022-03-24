package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Random;

public class ServerJeu extends Thread {
	
	
	private boolean isActive=true;
	private int nombreClient=0;
	private int nbreSecret;
	private String gagnant;
	private boolean fin;
	public static void main(String[] args) {
             new ServerJeu().start();
		
	}
	@Override
	public void run() {
		try {
			ServerSocket  serverSocket = new ServerSocket(1234);
			nbreSecret=new Random().nextInt(1000);
			System.out.println("Demarage du Server");
			System.out.println("Le nombre "+nbreSecret);

			while(true) {
				Socket socket=serverSocket.accept();
				++nombreClient;   
				new Conversations(socket,nombreClient).start();
				
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class Conversations extends Thread{
		private Socket socketClient;
		private int numero;
		public Conversations(Socket socketClient, int numero) {
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
				pw.println("Devinez le nombre secret ....");

				
				while(true) {
					String req=br.readLine();
					int nombre = Integer.parseInt(req);
					System.out.println("Client"+ipClient+"Tentative avec le nombre  "+nombre);

					if(fin==false) {
						if(nombre<nbreSecret) {
							pw.println("Votre Nombre est Inferieur au nombre secret");
						}
						else if(nombre>nbreSecret) {
							pw.println("Votre Nombre est Superieur au nombre secret");
							
						}
						else { 
							pw.println("BRAVO , Vous Avez gange");
							gagnant=ipClient;
							System.out.println("Bravo au gagnant , IP Client :"+ipClient);
							fin =true;
						}
						
					}
					else {
						pw.println("JEU Termine, Le gagnant est "+gagnant);
					}
					
					String reponse="Length= "+req.length();
					pw.println(reponse);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	
}
