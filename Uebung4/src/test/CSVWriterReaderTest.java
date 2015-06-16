package test;

import static org.junit.Assert.*;

import java.io.IOException;
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
		
	/*
	 * ArrayList von ContactDetails erstellen
	 */
	@Before
	public void setUp() throws Exception {
		contacts = new ArrayList<>();
		contacts.add(new ContactDetails("Wurst", "Hans", "Gruenbergstraße 12", "0349555", "mailWurst@gmx.com"));
		contacts.add(new ContactDetails("Mayer", "Hans", "Hasen 12", "0349555", "mailMayer@gmx.com"));
		contacts.add(new ContactDetails("Zott", "Anna", "Burgstraße 2", "0178564555", "mailAnna@web.com"));
		try {
			CSVContactsWriter.writeEntityList(contacts, "test.csv", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		contactsRead = CSVContactsReader.readEntityList("test.csv", ";");
	}
	
	@Test
	public void testWriterReader() {
		for (int i=0; i<contacts.size(); i++)
			assertEquals(contacts.get(i), contactsRead.get(i));
	}
	
}
