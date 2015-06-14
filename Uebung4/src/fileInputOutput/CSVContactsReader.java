package fileInputOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import application.ContactDetails;
import exceptions.ContactFormatException;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;

public class CSVContactsReader {

	public static List<ContactDetails> readEntityList(String dateiname, String splitter) {
		Path source = Paths.get(dateiname);
		return readEntityList(source, splitter);
	}
	
	public static List<ContactDetails> readEntityList(Path source, String splitter) {
		List<ContactDetails> target = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(source);
			for (String line : lines) {
				String [] detailsAr = line.trim().split(splitter);
				try {
					target.add(new ContactDetails(detailsAr[0], detailsAr[1], detailsAr[2], detailsAr[3], detailsAr[4]));
				} catch (EmptyNameException | EmptyFirstNameException | EmptyNumberException ex) { 	//catch Ex from ContactDetails
					ex.printStackTrace(System.err);
					target.add(null);								//Empty Contact to indicate problem
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
			target.addAll(null);										//null addition to target to indicate problem
		}
		return target;
	}
}
