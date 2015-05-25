package terminkalender;

import exceptions.EmptyStringException;
import exceptions.TimeConflictException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {
	
	//wir gehen davon aus, dass Termine nur innerhalb eines Tages gemacht werden & die Uhrzeit im 24h-Format angegeben wird
	//StringProperties
	private StringProperty datum; 				//wert ist pflicht
	private StringProperty startUhrzeit; 		//wert ist pflicht
	private StringProperty endUhrzeit; 			//wert ist pflicht
	private StringProperty terminkategorie;
	private StringProperty terminbezeichnung;	//wert ist pflicht
	private StringProperty terminbeschreibung;

	//IntergerProperties
	private IntegerProperty dauer;
	private IntegerProperty startUhrzeitInt;
	private IntegerProperty endUhrzeitInt;
	
	/*
	 * -------------------Konstruktor-------------------
	 */
	public Appointment(String datum, String startUhrzeit, String endUhrzeit, String terminkategorie, String terminbezeichnung, String terminbeschreibung) 
			throws EmptyStringException, TimeConflictException {
		handleException(datum, startUhrzeit, endUhrzeit, terminkategorie, terminbezeichnung, terminbeschreibung);
		this.datum = new SimpleStringProperty();
		this.startUhrzeit = new SimpleStringProperty();
		this.endUhrzeit = new SimpleStringProperty();
		this.terminkategorie = new SimpleStringProperty();
		this.terminbezeichnung = new SimpleStringProperty();
		this.terminbeschreibung = new SimpleStringProperty();
		
		this.datum.set(datum);
		this.startUhrzeit.set(startUhrzeit);
		this.endUhrzeit.set(endUhrzeit);
		this.terminkategorie.set(terminkategorie);
		this.terminbezeichnung.set(terminbezeichnung);
		this.terminbeschreibung.set(terminbeschreibung);
		
		startUhrzeitInt = new SimpleIntegerProperty();
		startUhrzeitInt.set(stringToMin(startUhrzeit));
		endUhrzeitInt = new SimpleIntegerProperty();
		endUhrzeitInt.set(stringToMin(endUhrzeit));
		dauer = new SimpleIntegerProperty();
		dauer.bind(endUhrzeitInt.subtract(startUhrzeitInt));
		//Ohne Binding:
//		dauer.set(stringToMin(endUhrzeit)-stringToMin(startUhrzeit));
		
	}

	public Appointment(Appointment obj) {
		if (obj == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		this.datum.set(obj.getDatum());
		this.startUhrzeit.set(obj.getStartUhrzeit());
		this.endUhrzeit.set(obj.getEndUhrzeit());
		this.terminkategorie.set(obj.getTerminkategorie());
		this.terminbezeichnung.set(obj.getTerminbezeichnung());
		this.terminbeschreibung.set(obj.getTerminbeschreibung());
	}
	
	/*
	 * -------------------Getter & Setter-------------------
	 */
	public String getDatum() {
		return datum.get();
	}

	public void setDatum(String datum) throws EmptyStringException {
		if (datum == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else if (datum.isEmpty())
			throw new EmptyStringException("Datum darf nicht leer sein");
		else
			this.datum.set(datum);
	}

	public StringProperty datumProperty () {
		return datum;
	}
		
	public String getStartUhrzeit() {
		return startUhrzeit.get();
	}

	/**
	 * Setzt die StartZeit und prüft, ob Zeit plausibel
	 * @param startUhrzeit
	 * @throws IllegalArgumentException wenn startUhrzeit == null
	 * @throws EmptyStringException wenn startUhrzeit == ""
	 * @throws TimeConflictException wenn startUhrzeit != 1 Tag oder Endzeit vor Startzeit
	 */
	public void setStartUhrzeit(String startUhrzeit) throws TimeConflictException, EmptyStringException {	
		if (startUhrzeit == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else if (startUhrzeit.isEmpty())
			throw new EmptyStringException("Startzeit darf nicht leer sein");
		else if (stringToMin(startUhrzeit) >= stringToMin(getEndUhrzeit()) || stringToMin(startUhrzeit) > 1440)
			throw new TimeConflictException("Bitte Zeit überprüfen");
		else {
			this.startUhrzeit.set(startUhrzeit);
			startUhrzeitInt.set(stringToMin(startUhrzeit));
		}	
	}

	public StringProperty startUhrzeitProperty() {
		return startUhrzeit;
	}
	
	public String getEndUhrzeit() {
		return endUhrzeit.get();
	}

	/**
	 * Setzt die EndZeit und prüft, ob Zeit plausibel
	 * @param endUhrzeit
	 * @throws IllegalArgumentException wenn endUhrzeit == null
	 * @throws TimeConflictException wenn endUhrzeit != 1 Tag oder Endzeit vor Startzeit
	 */
	public void setEndUhrzeit(String endUhrzeit) throws TimeConflictException, EmptyStringException {	
		if (endUhrzeit == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else if (endUhrzeit.isEmpty())
			throw new EmptyStringException("Endzeit darf nicht leer sein");
		else if (stringToMin(endUhrzeit) <= stringToMin(getStartUhrzeit()) || stringToMin(endUhrzeit) > 1440)
			throw new TimeConflictException("Bitte Zeit überprüfen");
		else {
			this.endUhrzeit.set(endUhrzeit);
			endUhrzeitInt.set(stringToMin(endUhrzeit));
		}
		System.out.println(dauer.get());
	}
	
	public StringProperty endUhrzeitProperty() {
		return endUhrzeit;
	}
	
	public String getTerminkategorie() {
		return terminkategorie.get();
	}

	public void setTerminkategorie(String terminkategorie) {
		if (terminkategorie == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else
			this.terminkategorie.set(terminkategorie);
	}
	
	public StringProperty terminkategorieProperty() {
		return terminkategorie;
	}

	public String getTerminbezeichnung() {
		return terminbezeichnung.get();
	}

	public void setTerminbezeichnung(String terminbezeichnung) throws EmptyStringException {
		if (terminbezeichnung == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else if (terminbezeichnung.isEmpty())
			throw new EmptyStringException("Terminbezeichnung darf nicht leer sein");
		else
			this.terminbezeichnung.set(terminbezeichnung);
	}

	public StringProperty terminbezeichnungProperty() {
		return terminbezeichnung;
	}
	
	public String getTerminbeschreibung() {
		return terminbeschreibung.get();
	}

	public void setTerminbeschreibung(String terminbeschreibung) {
		if (terminbeschreibung == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		else
			this.terminbeschreibung.set(terminbeschreibung);
	}

	public StringProperty terminbeschreibungProperty() {
		return terminbeschreibung;
	}
	
	public int getDauer() {
		return dauer.get();
	}
	
	public IntegerProperty dauerProperty() {
		return dauer;
	}
	
	/*
	 * -------------------weitere Meth.-------------------
	 */
	/**
	 * Methode, die String in int umwandelt (Zeit in Min)
	 * @param String zeit
	 * @return int Zeit in min
	 */
	private int stringToMin(String zeit) {
		String[] zeitAr = zeit.split(":");
		return Integer.parseInt(zeitAr[0])*60 + Integer.parseInt(zeitAr[1]);
	}

	private void handleException (String datum, String startUhrzeit, String endUhrzeit, String terminkategorie, String terminbezeichnung, String terminbeschreibung) 
			throws EmptyStringException, TimeConflictException {
		if (datum == null || startUhrzeit == null || endUhrzeit == null || terminkategorie == null || terminbezeichnung == null || terminbeschreibung == null)
			throw new IllegalArgumentException("Ein Fehler ist aufgetreten");
		if (datum.isEmpty() || startUhrzeit.isEmpty() || endUhrzeit.isEmpty() || terminbezeichnung.isEmpty())
			throw new EmptyStringException("Feld darf nicht leer sein");
		if (stringToMin(endUhrzeit) <= stringToMin(startUhrzeit) || stringToMin(endUhrzeit) > 1440 || stringToMin(startUhrzeit) > 1440)
			throw new TimeConflictException("Bitte Zeit überprüfen");
	}
}
