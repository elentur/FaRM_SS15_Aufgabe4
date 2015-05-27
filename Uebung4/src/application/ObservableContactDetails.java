package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;

public class ObservableContactDetails extends ContactDetails {
	
	private StringProperty name;
	private StringProperty vorname;
	private StringProperty adresse;
	private StringProperty telefonNummer;
	private StringProperty emailAdresse;

	public ObservableContactDetails(ObservableContactDetails obj) {
		super(obj);
		this.name = obj.nameProperty();
		this.vorname = obj.vornameProperty();
		this.adresse = obj.adresseProperty();
		this.telefonNummer = obj.telefonNummerProperty();
		this.emailAdresse = obj.emailAdresseProperty();
	}
	public ObservableContactDetails(String name, String vorname, String adresse,
			String telefonNummer, String emailAdresse) throws EmptyNameException, EmptyFirstNameException, EmptyNumberException {
			super(name,vorname,adresse,telefonNummer, emailAdresse);
			System.out.println(super.getName());
		this.name = new SimpleStringProperty(super.getName());
		this.vorname = new SimpleStringProperty(super.getVorname());
		this.adresse = new SimpleStringProperty(super.getAdresse());
		this.telefonNummer = new SimpleStringProperty(super.getTelefonNummer());
		this.emailAdresse = new SimpleStringProperty(super.getEmailAdresse());
		
	}



	public String getName() {
		return name.get();
	}


	public String getVorname() {
		return vorname.get();
	}

	public String getAdresse() {
		return adresse.get();
	}

	public String getTelefonNummer() {
		return telefonNummer.get();
	}


	public String getEmailAdresse() {
		return emailAdresse.get();
	}
	public StringProperty nameProperty() {
		return name;
	}


	public StringProperty vornameProperty() {
		return vorname;
	}

	public StringProperty adresseProperty() {
		return adresse;
	}

	public StringProperty telefonNummerProperty() {
		return telefonNummer;
	}


	public StringProperty emailAdresseProperty() {
		return emailAdresse;
	}
	
}
