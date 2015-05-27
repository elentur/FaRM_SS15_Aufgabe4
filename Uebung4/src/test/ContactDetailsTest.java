package test;

import static org.junit.Assert.*;

import org.junit.Test;

import application.ObservableContactDetails;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
import exceptions.KeyInUseException;

public class ContactDetailsTest {
	ObservableContactDetails cd;
	//---CreateObject--------
	@Test
	public void createObject_NoError() throws  EmptyNameException, EmptyFirstNameException, EmptyNumberException {
		cd = new ObservableContactDetails("Huth", "Franzi","fff", "ff", "ff");
	}
	@Test (expected = EmptyNameException.class)
	public void createObject_NoNameInserted() throws  EmptyNameException, EmptyFirstNameException, EmptyNumberException {
			cd = new ObservableContactDetails("", "Franzi","fff", "ff", "ff");
		}
		@Test (expected = EmptyFirstNameException.class)
		public void createObject_NoFirstNameInserted() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException {
			cd = new ObservableContactDetails(new ObservableContactDetails("Huth", "","fff", "ff", "ff"));
		}
		@Test (expected = EmptyNumberException.class)
		public void createObject_NoNumberInserted() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException {
			cd = new ObservableContactDetails("Huth", "Franzi","ff", "", "ff");
		}
		@Test (expected = IllegalArgumentException.class)
		public void createObject_NullObjektInserted() throws KeyInUseException{
			cd = new ObservableContactDetails(null);
		}
		@Test (expected = IllegalArgumentException.class)
		public void createObject_NullObjektInserted2() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
			cd = new ObservableContactDetails(null, "Franzi","ff", "", "ff");
		}
}
