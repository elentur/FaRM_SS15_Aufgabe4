package application;


import exceptions.KeyInUseException;


public interface AdressBookInterface {
	/**
	 * Returns the ContactDetails-Object wich is bind to
	 * the Key-Object key
	 * @param key
	 * @return returns ContactDetails-Object if key exists
	 * else returns null
	 * @throws NoKeyException if key is null or one or more attributs are null 
	 */
	public ObservableContactDetails getDetails(Key key);
	
	/**
	 * Shows if the searchKey is in use or not
	 * @param key
	 * @return returns true if the searchKey is in use else false
	 */
	public boolean keyInUse(String key);
	
	/**
	 * Adds a ContactDetails-Object to the Adressbook
	 * Creates out of the values of name, firstname and phonenumber
	 * from the ContactDetails-Object a Key-Object 
	 * @param details
	 * @throws KeyInUseException if the Key created out of the Data is allready in use
	 */
	public void addDetails(ObservableContactDetails details) throws KeyInUseException;
	
	/**
	 * Changes the Details of an existing ContactDetails-Object
	 * Need the Key-Object and a ContactDetails-Object
	 * If the Key values in ContactDetails change then a new Key-Object
	 * will be created
	 * @param oldKey
	 * @param details
	 * @throws KeyInUseException if the Key created out of the Data is allready in use
	 */
	public void changeDetails(Key oldKey, ObservableContactDetails details)throws KeyInUseException;
	
	/**
	 * This function uses the searchKey to find all Keys which contains the searchKey
	 * and Return all refered ContactDetail-Objects
	 * @param keyPrefix
	 * @return	returns an Array of ContactDetail-Objects
	 */
	public ObservableContactDetails[] search(String keyPrefix);
	
	/**
	 * Returns the number of entries in the Adressbook
	 * @return Returns a int value equal tho the number of entries in the
	 * Adressbook
	 */
	public int getNumberOfEntries();
	
	/**
	 * Remove a ContactDeatils-Object and its Key-Object form the Adressbook
	 * if the Key-Object is in the Adressbook
	 * @param key
	 */
	public void removeDetails(Key key);
	
}