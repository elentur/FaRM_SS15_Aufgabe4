package fileInputOutput;

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
/**
 * 
 * @author Franziska Huth, Roman Duhr, Marcus Bätz
 *
 */
public class TerminkalenderDao {
	List<Appointment> appointmentList = new ArrayList<Appointment>();
	FileWriter writer;
	File file;
	String sourceOfTerminkalenderFile;

	/**
	 * Constructor
	 * 
	 * @param sourceOfFile, is the path of the file
	 */
	public TerminkalenderDao(String sourceOfFile) {
		this.sourceOfTerminkalenderFile = sourceOfFile;
	}

	/**
	 * load words from txt-file and put them into an ArrayList
	 * 
	 * @return ArrayList, return entries in a ArrayList from type Appointment
	 */
	public List<Appointment> loadFile() throws EmptyStringException,
			TimeConflictException {
		try {
			if (new File(sourceOfTerminkalenderFile).exists()) {
				BufferedReader eingabe = new BufferedReader(new FileReader(
						sourceOfTerminkalenderFile));
				String zeile = null;
				while ((zeile = eingabe.readLine()) != null) {
					String[] stringWord = zeile.trim().split(",");
					Appointment entry = new Appointment(stringWord[0],
							stringWord[1], stringWord[2], stringWord[3],
							stringWord[4],stringWord[5]);

					appointmentList.add(entry);

				}
				eingabe.close();
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return appointmentList;
	}

	/**
	 * Write parameters in a text-file and overwrite old data. If txt-file don't exist create
	 * new one.
	 * @param myAppointmentList, list of Appointment Objects
	 */
	public void saveAppointmentList(List<Appointment> myAppointmentList) {
		Appointment entry;
		file = new File(sourceOfTerminkalenderFile);
		try {
			if (!file.exists()) {

				file.createNewFile();
				System.out.println("New File was created");
			}
			writer = new FileWriter(file, false);
			int listLength = myAppointmentList.size();
			for (int i = 0; i < listLength; i++) {
				entry = myAppointmentList.get(i);
				//String datum, String startUhrzeit, String endUhrzeit, String terminkategorie, String terminbezeichnung, String terminbeschreibung
				writer.write(entry.getDatum() 
						+ ", " + entry.getStartUhrzeit()
						+ ", " + entry.getEndUhrzeit()
						+ ", " + entry.getTerminkategorie() 
						+ ", " + entry.getTerminbezeichnung() 
						+ ", " + entry.getTerminbeschreibung() + "\r\n");

			}

			writer.flush();
			writer.close();
			System.out.println(file.getName() + " was written to: "+file.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
