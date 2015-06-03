package application;



import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AdressbuchGUI extends Application {
	public ListView<ObservableContactDetails> listView = new ListView<ObservableContactDetails>() ;
	public TableView<ObservableContactDetails> tableView = new TableView<ObservableContactDetails>();
	public Label errorText = new Label();
	public Button btnlstViewPrint = new Button("Drucken");
	public Button btnAddTableContactDetail = new Button("+");


	@Override
	public void start(Stage primaryStage) {
		TabPane root = new TabPane();
		root.setMinWidth(800);
		Tab listviewTab = new Tab("Liste");
		Tab tableviewTab = new Tab("Tabelle");
		Group group = new Group();
		VBox vboxListview = new VBox(10);
		VBox vboxTableview = new VBox(10);
		listviewTab.setContent(vboxListview);
		tableviewTab.setContent(vboxTableview);
		group.getChildren().addAll(new VBox(btnlstViewPrint, root));
		group.setManaged(false);
		Scene scene = new Scene(group, 800, 500);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		vboxListview.getChildren().addAll(listView,errorText);
		vboxTableview.getChildren().addAll(tableView, btnAddTableContactDetail);
		
		root.getTabs().addAll(listviewTab,tableviewTab);
		
		this.listView.setEditable(true);
		this.errorText.setTextFill(Color.RED);
		this.errorText.setWrapText(true);
		new Controll(listView,tableView,errorText,btnlstViewPrint, btnAddTableContactDetail);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
