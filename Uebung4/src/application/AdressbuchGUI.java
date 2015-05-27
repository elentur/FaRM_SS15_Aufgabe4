package application;

import application.AdressBook;
import application.ContactDetails;
import application.Key;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdressbuchGUI extends Application {
	AdressBook phonebook;


	@Override
	public void start(Stage primaryStage) {
		TabPane root = new TabPane();
		Tab listview = new Tab("Liste");
		Tab tableview = new Tab("Tabelle");
		VBox vboxListview = new VBox(10);
		VBox vboxTableview = new VBox(10);
		listview.setContent(vboxListview);
		tableview.setContent(vboxTableview);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

		root.getTabs().addAll(listview,tableview);

		init();
	}



	public void init() {
		phonebook = new AdressBook();

	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
