package test;

import static org.junit.Assert.*;


import java.io.IOException;
import org.junit.Test;
import binaryIO.TerminkalenderInputStream;
import binaryIO.TerminkalenderOutputStream;
import terminkalender.Appointment;
import exceptions.EmptyStringException;
import exceptions.FieldReadingException;
import exceptions.TimeConflictException;

public class TerminkalenderInputOutputStreamTest {


	@Test
	public void testInputOutputStreamFromDefaultFilePath() throws EmptyStringException, TimeConflictException, IOException, FieldReadingException {
		TerminkalenderOutputStream out = new TerminkalenderOutputStream();
		TerminkalenderInputStream in = new TerminkalenderInputStream();
		Appointment kalenderToWriteInFile = new Appointment ("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		out.writeCalender(kalenderToWriteInFile);
		out.close();
		Appointment kalenderReadedFromFile = in.readCalender();
		in.close();
		assertEquals(kalenderToWriteInFile.getDatum(),kalenderReadedFromFile.getDatum());
		assertEquals(kalenderToWriteInFile.getStartUhrzeit(),kalenderReadedFromFile.getStartUhrzeit());
		assertEquals(kalenderToWriteInFile.getEndUhrzeit(),kalenderReadedFromFile.getEndUhrzeit());
		assertEquals(kalenderToWriteInFile.getTerminbezeichnung(),kalenderReadedFromFile.getTerminbezeichnung());


	}

	@Test
	public void testInputOutputStreamWithFilePath() throws EmptyStringException, TimeConflictException, IOException, FieldReadingException {
		TerminkalenderOutputStream out = new TerminkalenderOutputStream("kalender2.bin");
		TerminkalenderInputStream in = new TerminkalenderInputStream("kalender2.bin");
		Appointment kalenderToWriteInFile = new Appointment ("01.01.2018", "18:00", "19:00", "", "Essen", "");
		out.writeCalender(kalenderToWriteInFile);
		out.close();
		Appointment kalenderReadedFromFile = in.readCalender();
		in.close();
		assertEquals(kalenderToWriteInFile.getDatum(),kalenderReadedFromFile.getDatum());
		assertEquals(kalenderToWriteInFile.getStartUhrzeit(),kalenderReadedFromFile.getStartUhrzeit());
		assertEquals(kalenderToWriteInFile.getEndUhrzeit(),kalenderReadedFromFile.getEndUhrzeit());
		assertEquals(kalenderToWriteInFile.getTerminbezeichnung(),kalenderReadedFromFile.getTerminbezeichnung());

	}
	
	
	@Test(expected=IOException.class)
	public void testIOException() throws EmptyStringException, TimeConflictException, FieldReadingException, IOException {
		
			TerminkalenderOutputStream out = new TerminkalenderOutputStream("kalender3.bin");	
			out.close();
			TerminkalenderInputStream in = new TerminkalenderInputStream("kalender3.bin");
			Appointment kalenderReadedFromFile = in.readCalender();
			in.close();
		
	}
	

}
