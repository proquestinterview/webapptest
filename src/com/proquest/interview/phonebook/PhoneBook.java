package com.proquest.interview.phonebook;

public interface PhoneBook {
	public Person findPerson(String firstName, String lastName);
	public void addPerson(Person newPerson);
}
