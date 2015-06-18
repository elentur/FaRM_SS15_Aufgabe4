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

public class TerminkalenderInputStream extends DataInputStream {

	public TerminkalenderInputStream(InputStream out) {
		super(out);
	}

	public TerminkalenderInputStream() throws FileNotFoundException {
		this("calender.bin");
	}

	public TerminkalenderInputStream(String filename) throws FileNotFoundException {
		super(new FileInputStream(filename));
	}

	/**
	 * Lese Binary File
	 * @return Appointment, mit ausgelesenen Parametern (String datum, String
	 *         startUhrzeit, String endUhrzeit, String terminkategorie, String
	 *         terminbezeichnung, String terminbeschreibung)
	 * @throws EmptyStringException
	 * @throws TimeConflictException
	 * @throws FieldReadingException
	 *             , damit User Nachricht bekommt, dass es Probleme beim
	 *             Fileauslesen gab.
	 */
	public Appointment readCalender() throws EmptyStringException, TimeConflictException, FieldReadingException {
		String w1, w2, w3, w4, w5, w6;
		try {
			w1 = readUTF();
			w2 = readUTF();
			w3 = readUTF();
			w4 = readUTF();
			w5 = readUTF();
			w6 = readUTF();
		} catch (IOException iox) {
			throw new FieldReadingException("Datei kann nicht ausgelesen werden");
		}
		return new Appointment(w1, w2, w3, w4, w5, w6);
	}

}
