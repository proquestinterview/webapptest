package com.proquest.interview.phonebook.service;

import java.util.List;

import com.proquest.interview.phonebook.entity.Person;

public interface PhoneBook {
	public List<Person> findAllPersons() throws Exception;
	public List<Person> findPerson(String firstName, String lastName) throws Exception;
	public void addPerson(Person newPerson) throws Exception;
}
