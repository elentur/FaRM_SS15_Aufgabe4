package binaryIO;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import exceptions.EmptyStringException;
import exceptions.TimeConflictException;
import terminkalender.Appointment;
/**
 * 
 * @author Franziska Huth, Roman Duhr, Marcus Bätz
 *
 */
public class TerminkalenderOutputStream extends DataOutputStream{
	/**
	 * Creates a new DataOutputStream
	 * @param out, OutputStream Object
	 */
	public TerminkalenderOutputStream(OutputStream out) {
		super(out);
	}
	/**
	 * Creates a new DataOutputStream with "calender.bin" as default filename
	 * @throws FileNotFoundException
	 */
	public TerminkalenderOutputStream() throws FileNotFoundException{
		this("calender.bin");
	}
	/**
	 * Creates a new DataOutputStream with a FileOutputStream form the filename
	 * @param filename, name of the file 
	 * @throws FileNotFoundException
	 */
	public TerminkalenderOutputStream(String filename) throws FileNotFoundException{
		super(new FileOutputStream(filename));
	}
	
	/**
	 * write Binary File
	 * @param  Appointment Object
	 * @throws IOException
	 *             , to show the user a Confilct if the File could not be written.
	 */
	public void writeCalender(Appointment kalender)throws IOException{
		try{
			writeUTF(kalender.getDatum());
			writeUTF(kalender.getStartUhrzeit());
			writeUTF(kalender.getEndUhrzeit());
			writeUTF(kalender.getTerminkategorie());
			writeUTF(kalender.getTerminbezeichnung());
			writeUTF(kalender.getTerminbeschreibung());
		} catch (IOException iox) {
			throw new IOException("Datei kann nicht geschrieben werden");
		}
	}
}
