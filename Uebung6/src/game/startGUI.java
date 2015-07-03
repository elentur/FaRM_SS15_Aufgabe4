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
		
		
		root.getChildren().addAll(unten,trenner,oben);
		Scene scene = new Scene(root, 400,900);
		oben.prefHeightProperty().bind(scene.widthProperty());
		oben.prefWidthProperty().bind(scene.widthProperty());
		unten.prefHeightProperty().bind(scene.widthProperty());
		unten.prefWidthProperty().bind(scene.widthProperty());
		//trenner.minHeightProperty().bind(scene.heightProperty().divide(12));
		trenner.setMinHeight(70);
		trenner.setMaxHeight(70);
		primaryStage.setScene(scene);
		new Control(oben,unten, trenner);
		
		primaryStage.show();
		//primaryStage.setResizable(false);
		//primaryStage.setMaxWidth(400);
		primaryStage.maxHeightProperty().bind(unten.prefWidthProperty().multiply(2.0).add(110.0));
		primaryStage.minHeightProperty().bind(unten.prefWidthProperty().multiply(2.0).add(110.0));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
