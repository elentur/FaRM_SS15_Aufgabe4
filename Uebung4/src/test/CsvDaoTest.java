package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.EmptyStringException;
import exceptions.TimeConflictException;
import fileInputOutput.TerminkalenderDao;
import terminkalender.Appointment;

public class CsvDaoTest {
	private TerminkalenderDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new TerminkalenderDao("test.csv");
	}

	@Test
	public void testSaveAppointmentList() {
		try {
			List<Appointment> list = new ArrayList<Appointment>();
			Appointment appointment = new Appointment("10.06.2015", "12:00",
					"13:00", "Schule", "ESA5",
					"ESA 5 soll fertig gestellt werden");
			list.add(appointment);
			dao.saveAppointmentList(list);
		} catch (EmptyStringException | TimeConflictException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	@Test
	public void loadFile() {

		List<Appointment> list = new ArrayList<Appointment>();
		try {
			list = dao.loadFile();
			System.out.println("Termin mit: "+ list.get(0).getTerminbezeichnung()+ " wurde erstellt");
			
		} catch (EmptyStringException | TimeConflictException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
			assertTrue(list.size()==1);
	}

}
