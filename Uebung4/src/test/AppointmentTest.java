package test;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.EmptyStringException;
import exceptions.TimeConflictException;
import terminkalender.Appointment;

public class AppointmentTest {

	Appointment app;
	
	/*
	 * -------KonstruktorTest-------------
	 */
	@Test
	public void testConstructor_correctData() throws EmptyStringException, TimeConflictException {
		app = new Appointment ("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment (null, "12:00", "13:00", "", "Mittagessen", "");
	}
	
	@Test (expected = EmptyStringException.class)
	public void testConstructor_emptyString() throws EmptyStringException, TimeConflictException {
		app = new Appointment ("", "12:00", "13:00", "", "Mittagessen", "");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testConstructor_endTimeBeforeStartTime() throws EmptyStringException, TimeConflictException {
		app = new Appointment ("01.01.2015", "12:00", "11:00", "", "Mittagessen", "");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testConstructor_wrongTimeFormat() throws EmptyStringException, TimeConflictException {
		app = new Appointment ("01.01.2015", "12:00", "40:00", "", "Mittagessen", "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCopyConstructor_nullObj() {
		app = new Appointment (null);
	}
	
	/*
	 * -------SetterTest-------------
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testSetDatum_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setDatum(null);
	}
	
	@Test (expected = EmptyStringException.class)
	public void testSetDatum_emptyString() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setDatum("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetStartUhrzeit_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setStartUhrzeit(null);
	}

	@Test (expected = EmptyStringException.class)
	public void testSetStartUhrzeit_emptyString() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setStartUhrzeit("");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testSetStartUhrzeit_startTimeAfterEndTime() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setStartUhrzeit("14:00");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testSetStartUhrzeit_wrongTimeForma() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setStartUhrzeit("60:00");
	}

	@Test (expected = IllegalArgumentException.class)
	public void testSetEndUhrzeit_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setEndUhrzeit(null);
	}

	@Test (expected = EmptyStringException.class)
	public void testSetEndUhrzeit_emptyString() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setEndUhrzeit("");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testSetEndUhrzeit_endTimeBeforeStartTime() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setEndUhrzeit("11:00");
	}
	
	@Test (expected = TimeConflictException.class)
	public void testSetEndUhrzeit_wrongTimeForma() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setEndUhrzeit("60:00");
	}
		
	@Test (expected = IllegalArgumentException.class)
	public void testSetTerminkategorie_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setTerminkategorie(null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testSetTerminbezeichnung_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setTerminbezeichnung(null);
	}

	@Test (expected = EmptyStringException.class)
	public void testSetTerminbezeichnung_emptyString() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setTerminbezeichnung("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetTerminbeschreibung_nullObj() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		app.setTerminbeschreibung(null);
	}

	/*
	 * -------dauerTest-------------
	 */
	@Test
	public void testDauerProperty() throws EmptyStringException, TimeConflictException {
		app = new Appointment("01.01.2015", "12:00", "13:00", "", "Mittagessen", "");
		assertTrue(app.getDauer() == 60);
		app.setEndUhrzeit("14:00");
		assertTrue(app.getDauer() == 120);
		app.setStartUhrzeit("13:00");
		assertTrue(app.getDauer() == 60);
	}
}
