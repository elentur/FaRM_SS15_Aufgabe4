package application;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;

public class ObservableContactDetails extends ContactDetails {

	private StringProperty name;
	private StringProperty vorname;
	private StringProperty adresse;
	private StringProperty telefonNummer;
	private StringProperty emailAdresse;

	public ObservableContactDetails(ObservableContactDetails obj)
			throws EmptyNameException, EmptyFirstNameException,
			EmptyNumberException {
		super(
				new ContactDetails(obj.getName(), obj.getVorname(),
						obj.getAdresse(), obj.getTelefonNummer(),
						obj.getEmailAdresse()));
		this.name = obj.nameProperty();
		this.vorname = obj.vornameProperty();
		this.adresse = obj.adresseProperty();
		this.telefonNummer = obj.telefonNummerProperty();
		this.emailAdresse = obj.emailAdresseProperty();
	}

	public ObservableContactDetails(String name, String vorname,
			String adresse, String telefonNummer, String emailAdresse)
			throws EmptyNameException, EmptyFirstNameException,
			EmptyNumberException {
		super(name, vorname, adresse, telefonNummer, emailAdresse);
		this.name = new SimpleStringProperty(super.getName());
		this.vorname = new SimpleStringProperty(super.getVorname());
		this.adresse = new SimpleStringProperty(super.getAdresse());
		this.telefonNummer = new SimpleStringProperty(super.getTelefonNummer());
		this.emailAdresse = new SimpleStringProperty(super.getEmailAdresse());

	}
	public static Callback<ObservableContactDetails, Observable[]> extractor() {
		   return (ObservableContactDetails p) -> new Observable[]{p.name, p.vorname, p.adresse, p.telefonNummer,p.emailAdresse};
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
