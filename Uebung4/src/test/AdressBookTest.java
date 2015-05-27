package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
import exceptions.KeyInUseException;
import application.AdressBook;
import application.ObservableContactDetails;
import application.Key;

public class AdressBookTest {

AdressBook mAdressBook;
	@Before
	public void setUp() throws Exception {
		mAdressBook = new  AdressBook();
	
	}

	//--addDetails---------------------
	@Test (expected = EmptyNameException.class)
	public void addDetails_NoNameInserted() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException {
		mAdressBook.addDetails(new ObservableContactDetails("", "Franzi","fff", "ff", "ff"));
	}
	@Test (expected = EmptyFirstNameException.class)
	public void addDetails_NoFirstNameInserted() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException {
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "","fff", "ff", "ff"));
	}
	@Test (expected = EmptyNumberException.class)
	public void addDetails_NoNumberInserted() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException {
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Franzi","ff", "", "ff"));
	}
	@Test (expected = IllegalArgumentException.class)
		public void addDetail_NullObjektInserted() throws KeyInUseException{
		mAdressBook.addDetails(null);
	}

	@Test (expected = KeyInUseException.class)
	public void addDetails_withKeyInUse() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.addDetails(new ObservableContactDetails("Müller", "Franzi","charlesStreet", "123", "ff"));
		mAdressBook.addDetails(new ObservableContactDetails("Müller", "Franzi","TomsStreet", "123", "ff"));
	}
	@Test
	public void addDetails() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.addDetails(new ObservableContactDetails("Müller", "Franzi","charlesStreet", "123", "ff"));
		mAdressBook.addDetails(new ObservableContactDetails("Müller", "Franzi","TomsStreet", "124", "ff"));
		assertEquals(2, mAdressBook.getNumberOfEntries());
	}
	
	
	//-----getKey------------------
	@Test 
	public void getKey() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		Key key = new Key("Huth","Franzi","123");
		assertEquals(key, mAdressBook.getKey(new ObservableContactDetails("Huth", "Franzi","fff", "123", "ff")));
	}
	@Test (expected = IllegalArgumentException.class)
	public void getKey_FromNullObject() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.getKey(new ObservableContactDetails(null));
		
	}
	@Test (expected = EmptyNameException.class)
	public void getKey_FromEmptyParameter() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.getKey(new ObservableContactDetails("","","","",""));
		
	}
	
	//---changeDetails--------
	@Test (expected = KeyInUseException.class)
	public void changeDetails_shouldNotChangeDetail() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Franzi","fff", "123", ""));
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Fra","fff", "123", ""));
		mAdressBook.changeDetails(new Key("Huth","Fra","123"), new ObservableContactDetails("Huth", "Franzi","fff", "123", ""));
	}
	@Test (expected = IllegalArgumentException.class)
	public void changeDetails_emptyKey() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Fra","fff", "123", ""));
		mAdressBook.changeDetails(null, new ObservableContactDetails("Huth", "Franzi","fff", "123", ""));
	}
	@Test
	public void changeDetails_shouldChangeDetail() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		Key k = new Key("Blub","Hannes","01789");
		mAdressBook.addDetails(new ObservableContactDetails("Blub", "Hannes","fff", "01789", ""));
		mAdressBook.changeDetails(k, new ObservableContactDetails("Blub", "Hannesche","fff", "123", ""));
		assertEquals("Hannesche", (mAdressBook.search("Blub")[0]).getVorname());
	}
	
	//--searchDetails-------------
	@Test (expected = IllegalArgumentException.class)
	public void searchDetails_withNullKeyObject() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Fra","fff", "123", ""));
		mAdressBook.search(null);
	}
	@Test 
	public void searchDetails_shouldGive_emptyResult() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		assertEquals(0, mAdressBook.search("Hu").length);
	}
	@Test 
	public void searchDetails_give1Result() throws KeyInUseException, EmptyNameException, EmptyFirstNameException, EmptyNumberException{
		mAdressBook.addDetails(new ObservableContactDetails("Huth", "Fra","fff", "123", ""));
		mAdressBook.addDetails(new ObservableContactDetails("Meier", "Bob","Charles Street", "01766654", ""));
		
		assertEquals("Fra", (mAdressBook.search("Hu")[0]).getVorname());
	}

	
}
