package com.proquest.interview.phonebook;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PhoneBookImplTest {
	private PhoneBook phoneBook;
	
	@Before
	public void setUp() {
		phoneBook = new PhoneBookImpl();
		phoneBook.addPerson(new Person("David Conrad", "(313) 437-3173",
				"17540 Mack Ave #4, Grosse Pointe, MI"));
	}
	
	@Test
	public void emptyPhoneBookTest() {
		phoneBook = new PhoneBookImpl();
		assertEquals(phoneBook.allPeople().size(), 0);
	}
	
	@Test
	public void addPersonTest() {
		int formerSize = phoneBook.allPeople().size();
		phoneBook.addPerson(new Person("Jane Doe", "(313) 555-1212",
				"123 Elm Ct, Anytown, MI"));
		assertEquals(phoneBook.allPeople().size(), formerSize + 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addNullPersonTest() {
		phoneBook.addPerson(null);
	}
	
	@Test
	public void findPersonTest() {
		assertNotNull(phoneBook.findPerson("David", "Conrad"));
	}
	
	@Test
	public void noSuchPersonTest() {
		assertNull(phoneBook.findPerson("Joe", "Schmoe"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void findNullPersonTest() {
		phoneBook.findPerson(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void findNullFirstNameTest() {
		phoneBook.findPerson(null, "Jones");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void findNullLastNameTest() {
		phoneBook.findPerson("Dev", null);
	}
}
