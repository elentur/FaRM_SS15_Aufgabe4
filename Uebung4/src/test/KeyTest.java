package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.Key;

public class KeyTest {

	Key key;
	
	//---- Konstruktor ------
	@Test
	public void testConstructor() {
		key = new Key("Max", "Mustermann", "01601234567");
	}
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nullNameObject() {
		key = new Key("Max", null, "01601234567");
	}
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nullFirstNameObject() {
		key = new Key(null, "Mustermann", "01601234567");
	}
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nullNumberObject() {
		key = new Key("Max", "Mustermann", null);
	}
	
	//---- containsStringKey --------
	@Test
	public void testContainsKey_valueIncluded() {
		key = new Key("Max", "Mustermann", "01601234567");
		assertTrue(key.containsStringKey("Max"));
	}
	@Test
	public void testContainsKey_valueNotIncluded() {
		key = new Key("Max", "Mustermann", "01601234567");
		assertFalse(key.containsStringKey("Peter"));
	}
	@Test (expected = IllegalArgumentException.class)
	public void testContainsKey_nullObject() {
		key = new Key("Max", "Mustermann", "01601234567");
		key.containsStringKey(null);
	}
	
	//---- startsWith --------
	@Test
	public void testStartsWith_valueIncluded() {
		key = new Key("Max", "Mustermann", "01601234567");
		assertTrue(key.startsWith("m"));
	}
	@Test
	public void testStartsWith_valueNotIncluded() {
		key = new Key("Max", "Mustermann", "01601234567");
		assertFalse(key.startsWith("q"));
	}
	@Test (expected = IllegalArgumentException.class)
	public void testStartsWith_nullObject() {
		key = new Key("Max", "Mustermann", "01601234567");
		key.startsWith(null);
	}
}
