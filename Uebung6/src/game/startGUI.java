package game;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class startGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		VBox oben= new VBox(), unten = new VBox();
		VBox trenner = new VBox();
		trenner.setMinHeight(70);
		
		root.getChildren().addAll(unten,trenner,oben);
		Scene scene = new Scene(root, 400,860);
		primaryStage.setScene(scene);
		new Control(oben,unten, trenner);
		
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setMaxWidth(400);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
