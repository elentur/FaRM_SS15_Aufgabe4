package fileInputOutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import application.ContactDetails;

public class CSVContactsWriterBuffered {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public static void writeEntityList(List<ContactDetails> contacts, String filename, String splitter) 
			throws IOException {
		Path path = Paths.get(filename);
		writeEntityList(contacts, path, splitter);
	}
	
	public static void writeEntityList(List<ContactDetails> contacts, Path path, String splitter) 
			throws IOException {
		BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
		for (ContactDetails contact : contacts) {
			bw.write(contactAsCSVLine(contact, splitter) + "\n");
		}
	}

	private static String contactAsCSVLine(ContactDetails c, String splitter) {
		return c.getName()+splitter+c.getVorname()+splitter+c.getAdresse()+splitter+c.getTelefonNummer()+splitter+c.getEmailAdresse();
	}
	
}
