package binaryIO;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import terminkalender.Appointment;

public class TerminkalenderOutputStream extends DataOutputStream{

	public TerminkalenderOutputStream(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}
	public TerminkalenderOutputStream() throws FileNotFoundException{
		this("calender.bin");
	}
	public TerminkalenderOutputStream(String filename) throws FileNotFoundException{
		super(new FileOutputStream(filename));
	}
	
	
	public void writeCalender(Appointment kalender)throws IOException{
		writeUTF(kalender.getDatum());
		writeUTF(kalender.getStartUhrzeit());
		writeUTF(kalender.getEndUhrzeit());
		writeUTF(kalender.getTerminkategorie());
		writeUTF(kalender.getTerminbezeichnung());
		writeUTF(kalender.getTerminbeschreibung());
	}
}
