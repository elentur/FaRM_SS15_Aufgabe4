package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.EmptyStringException;
import exceptions.TimeConflictException;
import terminkalender.*;
public class CsvDao {
	List<Appointment> appointmentList = new ArrayList<Appointment>();
	FileWriter writer;
	File file;
	String sourceOfTerminkalenderFile;

	/*
	 * Konstruktor
	 * @param sourceOfTelephoneFile, is the path of the file
	 */
	public CsvDao(String sourceOfFile) {
		this.sourceOfTerminkalenderFile = sourceOfFile;
	}

	/*
	 * load words from txt-file and put them into an ArrayList
	 * 
	 * @return ArrayList, return entries in a ArrayList from type TelephoneEntry
	 */
	public List<Appointment> loadFile() throws EmptyStringException, TimeConflictException {
		try {
			if (new File(sourceOfTerminkalenderFile).exists() ){
			BufferedReader eingabe = new BufferedReader(new FileReader(
					sourceOfTerminkalenderFile));
			String zeile = null;
			while ((zeile = eingabe.readLine()) != null) {
				String[] stringWord = zeile.trim().split(",");
				Appointment entry = new Appointment(stringWord[0],stringWord[1],stringWord[2],stringWord[3],stringWord[4],stringWord[5]);
				
				appointmentList.add(entry);

			}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return appointmentList;
	}
}
