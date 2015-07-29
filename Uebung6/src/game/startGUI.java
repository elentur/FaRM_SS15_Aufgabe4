package game;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class startGUI extends Application {
	VBox root = new VBox();
	//VBox oben= new VBox(), unten = new VBox();
	GridPane oben= new GridPane(), unten = new GridPane();
	VBox trenner = new VBox();
	VBox root2 = new VBox(50);
	Scene scene = new Scene(root, 400,1010);
	Scene startScreen = new Scene(root2,400,600);
	Stage primaryStage; 
	TextField txtEingabe = new TextField();
	TextArea txtAusgabe = new TextArea();
	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		
		
		
		
		Button btnSinglePlayer = new Button("Singleplayer");
		btnSinglePlayer.setPrefSize(250,80);
		btnSinglePlayer.setOnAction(e->{startGame(true);});
		Button btnHost = new Button("Spiel Hosten");
		btnHost.setPrefSize(250,80);
		btnHost.setOnAction(e->{ConnectScreen(true);});
		Button btnClient = new Button("mit Spiel verbinden");
		btnClient.setPrefSize(250,80);
		btnClient.setOnAction(e->{ConnectScreen(false);});
		Button btnLoad = new Button("altes Spiel laden");
		btnLoad.setPrefSize(250,80);
		btnLoad.setOnAction(e->{startGame(false);});
		
		root2.getChildren().addAll(btnSinglePlayer,btnHost,btnClient,btnLoad);
		root2.setAlignment(Pos.CENTER);
		
		primaryStage.setScene(startScreen);
				
		primaryStage.show();
		
	}


	@SuppressWarnings({ "deprecation", "resource" })
	private void ConnectScreen(boolean i) {
		if(i){
			try {
				ServerSocket serverSocket = new ServerSocket(8080);
				Socket socketClient =  serverSocket.accept();
				new Control(oben,unten, trenner,txtAusgabe,txtEingabe,true,false,socketClient, name());
				loadScene();
			} catch (IOException e) {
				Dialogs.create()
				.owner(primaryStage)
		        .title("Fehler")
		        .masthead("Verbindungsfehler")
		        .message("Es konnte keine Verbindung hergestellt werden!")
		        .showException(e);
			}
		}else{
			
				Optional<String> response = Dialogs.create()
				        .owner(primaryStage)
				        .title("Ziel-IP")
				        .masthead("")
				        .message("Tragen sie bitte die Ziel IP_Adresse ein.")
				        .showTextInput("127.0.0.1");

				response.ifPresent(name -> {
				
				try {
				Socket socket = new Socket(name,8080);
				new Control(oben,unten, trenner,txtAusgabe,txtEingabe,false,false,socket,name());
				loadScene();
				} catch (IOException e) {
					Dialogs.create()
							.owner(primaryStage)
					        .title("Fehler")
					        .masthead("Verbindungsfehler")
					        .message("Es konnte keine Verbindung hergestellt werden!")
					        .showException(e);
				}
				});	
			
		}
		
	}

	private String name() {
		Optional<String> name = Dialogs.create()
		        .owner(primaryStage)
		        .title("Spielername")
		        .masthead("")
		        .message("Tragen sie bitte ihren namen ein.")
		        .showTextInput("Spieler");

		if (name.isPresent()) {
		   return name.get();
		}
		return "Spieler";
	}


	private void startGame(boolean i) {
		if(i){
			new Control(oben,unten, trenner,txtAusgabe,txtEingabe,true,false,null,name());	
		}else{
			new Control(oben,unten, trenner,txtAusgabe,txtEingabe,true,true,null,null);	
		}
		loadScene();
		
	}

	private void loadScene() {
		primaryStage.setScene(scene);
		oben.prefHeightProperty().bind(scene.widthProperty());
		oben.prefWidthProperty().bind(scene.widthProperty());
		unten.prefHeightProperty().bind(scene.widthProperty());
		unten.prefWidthProperty().bind(scene.widthProperty());
		trenner.setMinHeight(180);
		trenner.setMaxHeight(180);
		txtAusgabe.setMinHeight(80);
		txtAusgabe.setMaxHeight(80);
		trenner.getChildren().addAll(txtAusgabe,txtEingabe);
		txtAusgabe.setEditable(false);
		root.getChildren().addAll(unten,trenner,oben);
		//primaryStage.maxHeightProperty().bind(unten.prefWidthProperty().multiply(2.0).add(220.0));
		//primaryStage.minHeightProperty().bind(unten.prefWidthProperty().multiply(2.0).add(220.0));
		primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(2.0).add(220.0));
		primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(2.0).add(220.0));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
