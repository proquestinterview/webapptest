package com.proquest.interview.phonebook;

import java.util.Collection;

public interface PhoneBook {
	Person findPerson(String firstName, String lastName);
	void addPerson(Person newPerson);
	Collection<Person> allPeople();
}
