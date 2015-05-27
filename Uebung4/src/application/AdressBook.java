package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.KeyInUseException;

public class AdressBook implements AdressBookInterface {

	private Map<Key, ObservableContactDetails> contactMap;

	public AdressBook() {
		contactMap = new TreeMap<Key, ObservableContactDetails>();
	}

	public AdressBook(AdressBook obj) {
		contactMap = obj.contactMap;
	}

	/**
	 * Returns the Key-Object build from the ContactDetails-Object
	 * 
	 * @param searchKey
	 * @return the Key-Object containing build from the ContactDetails-Object
	 * @throws IllegalArgumentException
	 *             if searchKey == null
	 */
	public Key getKey(ObservableContactDetails details) {
		return new Key(details.getName(), details.getVorname(),
				details.getTelefonNummer());
	}


	/**
	 * Shows if the Key-Object is in use or not
	 * @param key
	 * @return returns true if the Key-Object is in use else false
	 */
	private boolean keyInUse(Key key) {
		try {
			for (Key tempKey : contactMap.keySet()) {
				if (tempKey.equals(key))
					return true;
			}
		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	@Override
	public void addDetails(ObservableContactDetails details) throws KeyInUseException {
		if (details == null)
			throw new IllegalArgumentException(
					"Fehler im System. Bitte Beenden sie die Anwendung.");
		Key key = getKey(details);
		if (keyInUse(key))
			throw new KeyInUseException(
					"Ein Nutzer mit diesen Daten existiert bereits.");
		contactMap.put(key, details);
	}
	
	/**
	 * changes ContactDetail 
	 * 
	 * @param oldKey, key from old ContactDetail
	 * @param newDetail, the changed Contact
	 * @throws KeyInUseException, if changed Contact already exists
	 */
	@Override
	public void changeDetails(Key oldKey, ObservableContactDetails newDetail)
			throws KeyInUseException {
		if (newDetail == null || oldKey == null)
			throw new IllegalArgumentException();
		if (keyInUse(getKey(newDetail)))
			throw new KeyInUseException(
					"Ein Nutzer mit diesen Daten existiert bereits.");
		removeDetails(oldKey);
		addDetails(newDetail);

	}

	@Override
	public ObservableContactDetails[] search(String searchKey) {
		
		List<ObservableContactDetails> cd = new ArrayList<ObservableContactDetails>();
		for (Key key : contactMap.keySet()) {
			if (key.startsWith(searchKey)) {

				cd.add(contactMap.get(key));
			}
			
		}

		ObservableContactDetails[] cdArray = new ObservableContactDetails[cd.size()];
		cd.toArray(cdArray);

		return cdArray;
	}

	@Override
	public int getNumberOfEntries() {
		// method for testing, not used in adressbook
		return contactMap.size();
	}

	@Override
	public void removeDetails(Key key) {
		contactMap.remove(key);
	}

	/**
	 * Returns a String containing all entries in the Adressbook
	 * @return Returns a String containing all entries in the Adressbook
	 */
	public String toString() {
		String allContacts = "";
		allContacts = allContacts + "####################################\n";
		for (Key key : contactMap.keySet()) {
			allContacts = allContacts + contactMap.get(key).toString();
			allContacts = allContacts
					+ "####################################\n";
		}
		return allContacts;
	}

	@Override
	public boolean keyInUse(String key) {
		// unused method
		return false;
	}

	@Override
	public ObservableContactDetails getDetails(Key key) {
		// unused method
		return null;
	}

}
