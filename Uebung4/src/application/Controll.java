package application;

import java.util.Arrays;

import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
import exceptions.KeyInUseException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controll {
	ObservableList<ObservableContactDetails> mObservableList;
	private  AdressBook phonebook = new AdressBook();
	private  ListView<ObservableContactDetails> listView;
	private  TableView<ObservableContactDetails> tableView;//Noch unbenutzt
	private  Label errorText;
	private  Button btnlstViewPrint;
	
	public Controll(ListView<ObservableContactDetails> listView,TableView<ObservableContactDetails> tableView,Label errorText,Button btnlstViewPrint){
		/*
		 * Übergebne GUI-Objekte speichern
		 */
		this.listView = listView;
		this.tableView = tableView;
		this.errorText = errorText;
		this.btnlstViewPrint = btnlstViewPrint;
		
		/*
		 * Adressbuch füllen
		 * Den Objekten Inhalt und Controlls zu Ordnern
		 */
		fillAdressbook();
		ObservableContactDetails[] obj = this.phonebook.search("");
		mObservableList = FXCollections.observableArrayList(Arrays.asList(obj));
		this.btnlstViewPrint.setOnAction(e -> System.out.println(this.phonebook));// Weißt dem Printbutton die Funktion zu das Adressbuch in die Console zu schreiben
		this.listView.setItems(this.mObservableList);
		this.listView.setCellFactory(p -> new MyListCell()); //Weist der Listview eine Benutzerdefinierte ListCell welche die eigenschaften der einzelnen Spalten der Listview definiert
		
		
		
	}
	/*
	 * Benutzerdefinierte ListCell klasse als Innere Klasse
	 * Muss erzeug werden, weil die standart Vorgaben mit unserem Objekttyp nicht umgehen können(also was das bearbeiten der Einträge angeht)
	 */
	 class MyListCell extends ListCell<ObservableContactDetails> {
		private TextField textField = new TextField(); //Das Textfeld das an der Stelle des Eintrags der editiert werden soll angezeigt wird
		private StringProperty name = new SimpleStringProperty(); // eine StringProperty die sich aus beiden Namensteilen zusamensetzt
		
		public MyListCell(){
			this.textField.visibleProperty().bind(this.editingProperty());//Bindet die Sichtbarkeit des EditTextfeldes an die editing Property der Zelle, also wenn die Zelle im bearbeiten Modus ist, wird das Textfeld gezeigt sonst nicht
			this.textField.setOnAction(a -> commitOnAction());
			this.setGraphic(this.textField);// Eine Zelle besteht aus seinem Text und einer Graphic, die jede Form von Node sein kann, in unserem Fall eben das EditTextfeld
			this.setContentDisplay(ContentDisplay.RIGHT);// Legt die Ausrichtung der Grafik fest
		}
		
		
		/*
		 * (non-Javadoc)
		 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
		 * Diese Methode wird immer dann aufgerufen wenn sich der Inhalt der Zelle ändert
		 * auch beim erstmaligen beschreiben
		 */
		@Override
		protected void updateItem(ObservableContactDetails t, boolean bln) {
			super.updateItem(t, bln);// führt die Standartaufgaben der Methode durch
			if (this.getItem() != null) {// Wenn die Zelle nicht null ist, wobei get Item das ObservableContactDetails-Object aus unserer Liste darstellt 
				this.name.bind(this.getItem().nameProperty().concat(", ").concat(this.getItem().vornameProperty())); //Konstruiert eine StringProperty, aus vornamen, nachnamen getrent von einem Komma
				this.textField.setText(name.get());//legt den Text fürs EditTextfeld fest
				setText(name.get());//legt den Anzeigetext fest(ohne edit)
			}
		}
		
		/*
		 * eigene Methode die bei benutzung von Enter im EditTextfeld aufgerufen wird
		 */
		private void commitOnAction(){
			ObservableContactDetails obj;
			try {
				String[] newV = textField.getText().split(",");//Zerlegt den Text im Edittextfeld in zwei tele getrent am Komma
				if (newV.length != 2)//Sollte der Text leer sein, kein Komma enthalten oder zuvile Kommas entahlten gibt es eine Fehlermeldung und einen Abbruch
					throw new IllegalArgumentException(	"Es müssen 2 Namen getrennt durch ein Komma sein.");
				obj = new ObservableContactDetails(
					newV[0],
					newV[1], 
					this.getItem().getAdresse(), 
					this.getItem().getTelefonNummer(), 
					this.getItem().getEmailAdresse()
					);//Ansonnste nwir ein neues OContactDetails-Objekt erstellt, mit den neuen Daten
				phonebook.changeDetails(phonebook.getKey(this.getItem()), obj);// dann wird versucht es dem Adressbuch hinzuzufügen
			} catch (Exception e) {
				errorText.setText(e.getMessage());//FehlerMeldung wird ausgegeben
				obj = this.getItem();//das Objekt wird wieder auf seinen ursprung gesetzt
			}
			commitEdit(obj);// dann wird das neue oder alte Objekt in dieBearbeitung geschickt
		}
		
		/*
		 * (non-Javadoc)
		 * @see javafx.scene.control.ListCell#startEdit()
		 * Wird einmalig aufgerufen, sobald eine Zelle in den Editmodus wechselt
		 */
		@Override
		public void startEdit() {
			super.startEdit();
			setText("");//Leert den Angezeigten Text, Sonst würde der Name auch noch weiter vor dem textfeld stehen
			
		}
		/*
		 * (non-Javadoc)
		 * @see javafx.scene.control.ListCell#cancelEdit()
		 * Wird bei einem Abruch des Editierens mit ESC aufgerufen
		 */
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			this.textField.setText(name.get());// setzt den EditTExtfeld Text zurück
			setText(name.get()); //setzt den AnzeigeText wieder
		}

		
	}
	
	/*
	 * Füllt das Adressbuch
	 */
	private void fillAdressbook() {

		try {
			ObservableContactDetails person1 = new ObservableContactDetails("Wurst", "Hans",
					"Gruenbergstraße 12", "0349555", "mailWurst@gmx.com");

			ObservableContactDetails person2 = new ObservableContactDetails("Mayer", "Hans",
					"Hasen 12", "0349555", "mailMayer@gmx.com");
			
			ObservableContactDetails person3 = new ObservableContactDetails("Zott", "Anna",
					"Burgstraße 2", "0178564555", "mailAnna@web.com");
			
			phonebook.addDetails(person1);
			phonebook.addDetails(person2);
			phonebook.addDetails(person3);
			
		} catch (EmptyNameException | EmptyFirstNameException
				| EmptyNumberException | KeyInUseException e) {
			e.printStackTrace();
		}

	}
}
