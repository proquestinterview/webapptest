package com.proquest.interview.phonebook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PhoneBookImplTest {

	PhoneBook objectUnderTest;

	@Before
	public void setUp() {
		objectUnderTest = new PhoneBookImpl();
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAddNullPerson() {
		objectUnderTest.addPerson(null);
	}

	@Test
	public void shouldAddFindPerson() {
		String nf = "Test";
		String nl = "Name";
		String phoneNumber = "123";
		String address = "1 Some St";
		Person p = new Person(nf, nl, phoneNumber, address);
		objectUnderTest.addPerson(p);
		final Person found = objectUnderTest.findPerson("Test", "Name");
		Assert.assertNotNull(found);
	}
}
