package com.proquest.interview.phonebook;

import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List people;
	
	@Override
	public void addPerson(Person newPerson) {
		//TODO: write this method
	}
	
	@Override
	public Person findPerson() {
		//TODO: write this method
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
		// TODO: print the phone book out to System.out
		// TODO: find Cynthia Smith and print out just her entry
		// TODO: insert the new person objects into the database
	}
}
