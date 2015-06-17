package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import binaryIO.TerminkalenderInputStream;
import binaryIO.TerminkalenderOutputStream;
import terminkalender.Appointment;
import exceptions.EmptyStringException;
import exceptions.TimeConflictException;

public class TerminkalenderInputOutputStreamTest {
Appointment kalender;
TerminkalenderOutputStream out;
	@Before
	public void setUp() throws Exception {
		out = new TerminkalenderOutputStream();
	}

	/*
	 * -------KonstruktorTest-------------
	 */
	@Test
	public void testOutputStream() throws EmptyStringException, TimeConflictException, IOException {
		kalender = new Appointment ("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		out.writeCalender(kalender);
	}
	

}
