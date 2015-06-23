package binaryIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import exceptions.EmptyStringException;
import exceptions.FieldReadingException;
import exceptions.TimeConflictException;
import terminkalender.Appointment;
/**
 * 
 * @author Franziska Huth, Roman Duhr, Marcus Bätz
 *
 */
public class TerminkalenderInputStream extends DataInputStream {
	
	/**
	 * Creates a new DataInputStream
	 * @param in, InputStream Object
	 */
	public TerminkalenderInputStream(InputStream in) {
		super(in);
	}
	/**
	 * Creates a new DataInputStream with "calender.bin" as default filename
	 * @throws FileNotFoundException
	 */
	public TerminkalenderInputStream() throws FileNotFoundException {
		this("calender.bin");
	}
	/**
	 * Creates a new DataInputStream with a FileInputStream form the filename
	 * @param filename, name of the file 
	 * @throws FileNotFoundException
	 */
	public TerminkalenderInputStream(String filename) throws FileNotFoundException {
		super(new FileInputStream(filename));
	}

	/**
	 * Read Binary File
	 * @return Appointment, with Parameter readout (String datum, String
	 *         startUhrzeit, String endUhrzeit, String terminkategorie, String
	 *         terminbezeichnung, String terminbeschreibung)
	 * @throws EmptyStringException
	 * @throws TimeConflictException
	 * @throws IOException
	 *             , to show the user a Confilct if the File could not be read out.
	 */
	public Appointment readCalender() throws EmptyStringException, TimeConflictException, IOException {
		String w1, w2, w3, w4, w5, w6;
		try {
			w1 = readUTF();
			w2 = readUTF();
			w3 = readUTF();
			w4 = readUTF();
			w5 = readUTF();
			w6 = readUTF();
		} catch (IOException iox) {
			throw new IOException("Datei kann nicht ausgelesen werden");
		}
		return new Appointment(w1, w2, w3, w4, w5, w6);
	}

}
