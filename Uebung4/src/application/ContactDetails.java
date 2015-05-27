package application;

import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;

public class ContactDetails {

	private String name;
	private String vorname;
	private String adresse;
	private String telefonNummer;
	private String emailAdresse;

	public ContactDetails(String name, String vorname, String adresse,
			String telefonNummer, String emailAdresse) throws EmptyNameException, EmptyFirstNameException, EmptyNumberException{
			handleException(name, vorname, telefonNummer);
			this.name = name.trim();
			this.vorname = vorname.trim();
			this.adresse = adresse.trim();
			this.telefonNummer = telefonNummer.trim();
			this.emailAdresse = emailAdresse.trim();

	}

	public ContactDetails(ContactDetails obj) {
		if (obj == null ){
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		}
		this.name = obj.getName();
		this.vorname = obj.getVorname();
		this.adresse = obj.getAdresse();
		this.telefonNummer = obj.getTelefonNummer();
		this.emailAdresse = obj.getEmailAdresse();
	}

	public String getName() {
		return name;
	}


	public String getVorname() {
		return vorname;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getTelefonNummer() {
		return telefonNummer;
	}


	public String getEmailAdresse() {
		return emailAdresse;
	}


	public String toString() {
		String allContacts = "";
		allContacts = allContacts + "Name: " + getName() + "\n";
		allContacts = allContacts + "Vorname: " + getVorname() + "\n";
		allContacts = allContacts + "Telefonnummer: " + getTelefonNummer()
				+ "\n";
		allContacts = allContacts + "Adresse: " + getAdresse() + "\n";
		allContacts = allContacts + "E-Mail: " + getEmailAdresse() + "\n";
		return allContacts;
	}
	private void handleException(String name2, String vorname2, String telefonNummer2) throws EmptyNameException, EmptyFirstNameException, EmptyNumberException {
		if(name2 == null || vorname2 == null || telefonNummer2 == null){
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		} else if (name2.isEmpty()) {
			throw new EmptyNameException("Du musst einen Namen eintragen.");
		} else if (vorname2.isEmpty()) {
			throw new EmptyFirstNameException("Du musst einen Vornamen eintragen.");
		} else if (telefonNummer2.isEmpty()) {
			throw new EmptyNumberException("Du musst eine Telefonnummer eintragen");
		}
	}
}
