package com.proquest.interview.phonebook;

import java.util.List;

public interface PhoneBook {
	public Person findPerson(String firstName, String lastName);
	public void addPerson(Person newPerson);
	public List<Person> getPeople();
}
