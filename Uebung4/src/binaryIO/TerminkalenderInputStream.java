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
import exceptions.TimeConflictException;
import terminkalender.Appointment;

public class TerminkalenderInputStream extends DataInputStream{

	public TerminkalenderInputStream(InputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}
	public TerminkalenderInputStream() throws FileNotFoundException{
		this("calender.bin");
	}
	public TerminkalenderInputStream(String filename) throws FileNotFoundException{
		super(new FileInputStream(filename));
	}
	
	
	public Appointment readCalender()throws IOException, EmptyStringException, TimeConflictException{
	return new Appointment(readUTF(), readUTF(), readUTF(), readUTF(), readUTF(), readUTF());
		
		
		
	}
}
