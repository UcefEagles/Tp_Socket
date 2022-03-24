package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application{
	PrintWriter pw;
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Client Chat");
		BorderPane borderpane=new BorderPane();
		
		Label labelhost =new Label("Host:");
		TextField textFieldHost=new TextField("Localhost");
		Label labelPort=new Label("Port");
		TextField textFieldPort=new TextField("1234");
		Button	buttonConnecter =new Button("Connecter");
		
		
		HBox hbox=new HBox();hbox.setSpacing(10);hbox.setPadding(new Insets(10));
		hbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,null,null)));
		hbox.getChildren().addAll(labelhost,textFieldHost,labelPort,textFieldPort,buttonConnecter);
		
		borderpane.setTop(hbox);
		VBox vbox2=new VBox();vbox2.setSpacing(10);vbox2.setPadding(new Insets(10));
		ObservableList<String> listModel=FXCollections.observableArrayList();
		
		ListView<String> listView=new ListView<String>(listModel); 
		vbox2.getChildren().add(listView);
		borderpane.setCenter(vbox2);
		
		Label labelMessage=new Label("Message");
		TextField textFieldMessage=new TextField();textFieldMessage.setPrefSize(300, 35);
		Button buttonenvoyer = new Button("Envoyer");
		HBox hbox2= new HBox();hbox2.setSpacing(10);hbox2.setPadding(new Insets(15));
		hbox2.getChildren().addAll(labelMessage,textFieldMessage,buttonenvoyer);
		borderpane.setBottom(hbox2);
		
		Scene scene=new Scene(borderpane,800,590);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		buttonConnecter.setOnAction((evt->{
			String host=textFieldHost.getText();
			int port=Integer.parseInt(textFieldPort.getText());
			
			try {
				Socket socket = new Socket(host,port);
				InputStream inputStream=socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(inputStream);
				BufferedReader bufferedReader=new BufferedReader(isr);
			 	pw=new PrintWriter(socket.getOutputStream(),true);
				new Thread(()->{
					
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							try {
						    	String response=bufferedReader.readLine();
								listModel.add(response);
						    	} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								
							}
							// TODO Auto-generated method stub  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
							
						}
						// do your GUI stuff here
						});
					
					
						/*
							while(true) {
						    Platform.runLater(()->{
						    	try {
						    	String response=bufferedReader.readLine();
								listModel.add(response);
						    	} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								
							}
						    });
							
							}
						*/
					
				}).start();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));
		buttonenvoyer.setOnAction((evt)->{
			String message=textFieldMessage.getText();
			pw.println(message);
			System.out.println("Working");
		});
		
	}

}
