package fileInputOutput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
import application.ContactDetails;

public class CSVContactsWriter {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public static void writeEntityList(List<ContactDetails> contacts, String filename, String splitter) 
			throws IOException {
		Path path = Paths.get(filename);
		writeEntityList(contacts, path, splitter);
	}
	
	public static void writeEntityList(List<ContactDetails> contacts, Path path, String splitter) 
			throws IOException {
		List<String> lines = new ArrayList<>();
		for (ContactDetails contact : contacts) {
			lines.add(contactAsCSVLine(contact, splitter));
		}
		System.out.println(lines.toString());
		Files.write(path, lines, ENCODING);
	}

	private static String contactAsCSVLine(ContactDetails c, String splitter) {
		return c.getName()+splitter+c.getVorname()+splitter+c.getAdresse()+splitter+c.getTelefonNummer()+splitter+c.getEmailAdresse();
	}

//	//------------------------- Test --------------------------
//	/*
//	 * Main-Methode zum testen: schreibt Test-Datei in aktuelles Verzeichnis und befüllt diese mit ContactDetails
//	 */
//	public static void Main(String[] args) throws IOException {
//		
//		new CSVContactsWriter();
//		
//		//ArrayList von ContactDetails-Kontakten erstellen
//		List<ContactDetails> contacts = new ArrayList<>();
//		try {
//			contacts.add(new ContactDetails("Wurst", "Hans", "Gruenbergstraße 12", "0349555", "mailWurst@gmx.com"));
//			contacts.add(new ContactDetails("Mayer", "Hans", "Hasen 12", "0349555", "mailMayer@gmx.com"));
//			contacts.add(new ContactDetails("Zott", "Anna", "Burgstraße 2", "0178564555", "mailAnna@web.com"));
//		} catch (EmptyNameException | EmptyFirstNameException | EmptyNumberException e) {
//			e.printStackTrace();
//		}
//		
//		//aktuelles Verzeichnis holen und Pfad an Meth. (zweite) übergeben
//		String current = System.getProperty("user.dir");
//		Path testPath = Paths.get(current, "pgrTest.csv");
//		CSVContactsWriter.writeEntityList(contacts, testPath, ";");
//	}
	
}
