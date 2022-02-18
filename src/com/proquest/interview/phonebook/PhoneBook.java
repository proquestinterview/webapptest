package com.proquest.interview.phonebook;

import java.util.List;

/**
 * @author 
 *
 */
public interface PhoneBook {
	
	
	public List<Person> findPerson(String firstName, String lastName);
	public List<Person> findPerson();
	public void addPerson(Person newPerson);
	
	
	
}
