package fileInputOutput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import application.ContactDetails;
/**
 * 
 * @author Franziska Huth, Roman Duhr, Marcus Bätz
 *
 */
public class CSVContactsWriter {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	/**
	 * Creates a Path File from the filename String and 
	 * call then the same function with this pathfile, the contacts and the splitter file
	 * @param contacts, a List of ContactDetail Objects that have to be written
	 * @param filename, String with the filename
	 * @param splitter, String with the spiltter symbol
	 * @throws IOException if the filename is not found in the rootpath
	 */
	public static void writeEntityList(List<ContactDetails> contacts, String filename, String splitter) 
			throws IOException {
		writeEntityList(contacts, Paths.get(filename), splitter);
	}
	/**
	 * Writes all Contactdetails in the given File. Each Contactdetail Object in one Line. Seperates the Parameters of this object by the seprator String
	 * @param contacts, a List of ContactDetail Objects that have to be written
	 * @param path, pathfile containing the filename and the path. Without changes, the path ist the rootpath of the Programm
	 * @param splitter, String with the spiltter symbol
	 * @throws IOException if the filename and/or the path is not found
	 */
	public static void writeEntityList(List<ContactDetails> contacts, Path path, String splitter) 
			throws IOException {
		List<String> lines = new ArrayList<>(); // Erstelle eine Leere StringListe
		for (ContactDetails contact : contacts) { 
			lines.add(contactAsCSVLine(contact, splitter));// Füge der Liste nach einander alle ContactDetails Objekte als CSV Codierten String hinzu
		}
		System.out.println(lines.toString());
		Files.write(path, lines, ENCODING); // Schreibe die Liste in die Datei 
	}
	/**
	 * 
	 * @param c, ContactDetails Object
	 * @param splitter,  String with the spiltter symbol
	 * @return String with all Parameters from c seperated by the splitter String
	 */
	private static String contactAsCSVLine(ContactDetails c, String splitter) {
		return c.getName()+splitter+c.getVorname()+splitter+c.getAdresse()+splitter+c.getTelefonNummer()+splitter+c.getEmailAdresse();
	}
	
}
