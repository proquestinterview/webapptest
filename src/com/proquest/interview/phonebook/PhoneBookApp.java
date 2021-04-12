package com.proquest.interview.phonebook;

import java.util.List;

import com.proquest.interview.phonebook.entity.Person;
import com.proquest.interview.phonebook.service.PhoneBook;
import com.proquest.interview.phonebook.serviceImpl.PhoneBookImpl;
import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookApp {
	public static List<Person> people;
	public static PhoneBook phoneBookService;

	public static void main(String[] args) {
		DatabaseUtil.initDB(); // You should not remove this line, it creates the in-memory database

		phoneBookService = new PhoneBookImpl();
		/*
		 * TODO: create person objects and put them in the PhoneBook and database John
		 * Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI Cynthia Smith, (824)
		 * 128-8758, 875 Main St, Ann Arbor, MI
		 */
		Person p1 = new Person();
		p1.name = "John Smith";
		p1.address = "1234 Sand Hill Dr, Royal Oak, MI";
		p1.phoneNumber = "(248) 123-4567";

		Person p2 = new Person();
		p2.name = "Cynthia Smith";
		p2.address = "875 Main St, Ann Arbor, MI";
		p2.phoneNumber = "(824) 128-8758";
		
		// TODO: insert the new person objects into the database
		try {
			phoneBookService.addPerson(p1);
			phoneBookService.addPerson(p2);
		} catch (Exception e) {
			System.out.print("something was wrong while adding person: " + e.getLocalizedMessage());
		}
		// TODO: print the phone book out to System.out
		try {
			people = phoneBookService.findAllPersons();
			if (!people.isEmpty()) {
				for (Person p : people) {
					System.out.print("name: [" + p.name + "], ph#: [" + p.phoneNumber + "], address: [" + p.address + "]\n");
				}
			}
		} catch (Exception e) {
			System.out.print("something was wrong while adding person: " + e.getLocalizedMessage());
		}
		
		// TODO: find Cynthia Smith and print out just her entry
		try {
			List<Person> found = phoneBookService.findPerson("Cynthia", "Smith");
			if (!found.isEmpty()) {
				System.out.print("found Cynthia Smith!");
				for (Person p : found) {
					System.out.print("name: [" + p.name + "], ph#: [" + p.phoneNumber + "], address: [" + p.address + "]");
				}
			}
		} catch (Exception e) {
			System.out.print("something was wrong while trying to find Cynthia Smith: " + e.getLocalizedMessage());
		}
		
	}
}
