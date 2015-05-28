package application;



import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
import exceptions.KeyInUseException;

public class AdressbuchGUI extends Application {
	AdressBook phonebook;
	ListView<ObservableContactDetails> listView = new ListView<ObservableContactDetails>();
	ObservableList<ObservableContactDetails> mObservableList;

	@Override
	public void start(Stage primaryStage) {
		TabPane root = new TabPane();
		Tab listviewTab = new Tab("Liste");
		Tab tableviewTab = new Tab("Tabelle");
		VBox vboxListview = new VBox(10);
		VBox vboxTableview = new VBox(10);
		listviewTab.setContent(vboxListview);
		tableviewTab.setContent(vboxTableview);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		vboxListview.getChildren().add(listView);
		root.getTabs().addAll(listviewTab,tableviewTab);

		init();
	}



	public void init() {
		phonebook = new AdressBook();
		fillAdressbook();
		createListView();
		

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void fillAdressbook() {

		try {
			ObservableContactDetails person1 = new ObservableContactDetails("Wurst", "Hans",
					"Gruenbergstraße 12", "0349555", "mailWurst@gmx.com");

			ObservableContactDetails person2 = new ObservableContactDetails("Mayer", "Hans",
					"Hasen 12", "078955", "mailMayer@gmx.com");
			
			ObservableContactDetails person3 = new ObservableContactDetails("Zott", "Anna",
					"Burgstraße 2", "0178564555", "mailAnna@web.com");
			
			phonebook.addDetails(person1);
			phonebook.addDetails(person2);
			phonebook.addDetails(person3);
			
		} catch (EmptyNameException | EmptyFirstNameException
				| EmptyNumberException | KeyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void createListView() {	
	       
		//Create dummy list of MyObject   
		ObservableContactDetails[] obj = phonebook.search("");
		mObservableList = FXCollections.observableArrayList(Arrays.asList(obj));
//		for (ObservableContactDetails detail : obj) {
//			mObservableList.add(detail);
//		}
		listView.setItems(mObservableList);
		listView.setEditable(true);
		
	    listView.setCellFactory(new Callback<ListView<ObservableContactDetails>, ListCell<ObservableContactDetails>>(){
	    	 
            @Override
            public ListCell<ObservableContactDetails> call(ListView<ObservableContactDetails> p) {
                 
                ListCell<ObservableContactDetails> cell = new ListCell<ObservableContactDetails>(){
 
                    @Override
                    protected void updateItem(ObservableContactDetails detail, boolean bln) {
                        super.updateItem(detail, bln);
                        if (detail != null) {
                            setText(detail.getName() + detail.getVorname());
                        }else{
                        	setText("");
                        }
                    }
 
                };
                 
                return cell;
            }
        });
	    
		
	}
}
