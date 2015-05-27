package application;


public class Key implements Comparable {

	private String name;
	private String vorname;
	private String telefonNummer;


	public Key(String name, String vorname, String telefonNummer) {
		if ( name == null ||  vorname == null ||  telefonNummer == null)
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		else {
			this.name = name.trim().toLowerCase();
			this.vorname = vorname.trim().toLowerCase();
			this.telefonNummer = telefonNummer.trim().toLowerCase();
		}
	}

	public Key(Key obj) {
		if (obj == null ){
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		}
		this.name = obj.getName();
		this.vorname = obj.getVorname();
		this.telefonNummer = obj.getTelefonNummer();
	}
	
	public String getName() {
		return name;
	}

	public String getVorname() {
		return vorname;
	}

	public String getTelefonNummer() {
		return telefonNummer;
	}

	public boolean containsStringKey(String key) {
		if (key == null)
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		else
			return this.toString().contains(":"+key.trim().toLowerCase()+":");
	}
	
	public boolean containsKey(Key key) {
			return (containsStringKey(key.getName()) || containsStringKey(key.getVorname()) || containsStringKey(key.getTelefonNummer()));
	}
	
	public boolean startsWith(String key)  {
		if (key == null)
			throw new IllegalArgumentException("Fehler im System. Bitte Beenden sie die Anwendung.");
		else {
			String k = key.trim().toLowerCase();
			return (this.getName().startsWith(k) || this.getVorname().startsWith(k) || this.getTelefonNummer().startsWith(k));
		}
	}
	
	public String toString() {
		return ":"+name + ":" + vorname + ":" + telefonNummer+":";
	}

	/*
	 * keys are sorted by their names
	 * if name and vorname and telefonNumemr are the same return 0
	 * 
	 */
	@Override
	public int compareTo(Object o) {
		if (o instanceof Key) {
			Key other = (Key) o;
			if (this.equals(other)) {
				return 0;
			}
			
			if (this.toString().compareTo(other.toString()) != 0) {
				return this.toString().compareTo(other.toString());
			}
		}
		return -1;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (telefonNummer == null) {
			if (other.telefonNummer != null)
				return false;
		} else if (!telefonNummer.equals(other.telefonNummer))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}
}