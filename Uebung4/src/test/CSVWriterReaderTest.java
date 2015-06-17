package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileInputOutput.CSVContactsReader;
import fileInputOutput.CSVContactsWriter;
import application.ContactDetails;

public class CSVWriterReaderTest {

	private List<ContactDetails> contacts;
	private List<ContactDetails> contactsRead;
		
	@Before
	public void setUp() throws Exception {
		
		//ArrayList von ContactDetails befüllen
		contacts = new ArrayList<>();
		contacts.add(new ContactDetails("Wurst", "Hans", "Gruenbergstrasse 12", "0349555", "mailWurst@gmx.com"));
		contacts.add(new ContactDetails("Mayer", "Hans", "Hasen 12", "0349555", "mailMayer@gmx.com"));
		contacts.add(new ContactDetails("Zott", "Anna", "Burgstrasse 2", "0178564555", "mailAnna@web.com"));
		
		//Home Ordner als Pfad holen
		String home = System.getProperty("user.home");
		Path testPath = Paths.get(home, "pgrTest.csv");
		
		try {
			CSVContactsWriter.writeEntityList(contacts, testPath, ";");				// in HomeOrdner speichern (ignoriert erste writeEntityList)
//			CSVContactsWriter.writeEntityList(contacts, "test.csv", ";");			// in aktuellem Tree speichern
		} catch (IOException e) {
			e.printStackTrace();
		}
		contactsRead = CSVContactsReader.readEntityList(testPath, ";");				//aus HomeOrdner lesen
//		contactsRead = CSVContactsReader.readEntityList("test.csv", ";");			//aus aktuellem Tree lesen
	}
	
	@Test
	public void testWriterReader() {
		for (int i=0; i<contacts.size(); i++)
			assertEquals(contacts.get(i).toString(), contactsRead.get(i).toString());
	}
	
}
