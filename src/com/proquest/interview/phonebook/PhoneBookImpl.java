package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	@Override
	public void addPerson(Person newPerson) {
		try {
			Connection cn = DatabaseUtil.getConnection();
			DatabaseUtil.addPerson(cn, newPerson);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Person findPerson(String firstName, String lastName) {
		try {
			Connection cn = DatabaseUtil.getConnection();
			List<Person> people = DatabaseUtil.getPeople (cn, firstName + " " + lastName);  // Ewww.
			return people.size() == 1 ? people.get(0) : null;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
		
		PhoneBook phoneBook = new PhoneBookImpl();
		
		Person jSmith = new Person ("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");
		phoneBook.addPerson(jSmith);
		Person cSmith = new Person ("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");
		phoneBook.addPerson(cSmith);
		
		Connection connection = DatabaseUtil.getConnection();
		
		for (Person person: DatabaseUtil.getPeople(connection, "")) {
			System.out.println (person);
		}
		connection.close();
		
		System.out.println();
		
		System.out.println (phoneBook.findPerson("Cynthia", "Smith"));
		

		/*
		 * The goal of this exercise is to demonstrate your ability to do simple object-oriented
		 * programming in Java.  You will create 'Person' objects for a phone book, and then
		 * insert and retrieve them into a relational (SQL) database.
		 * 
		 * We have provided an in-memory "HSQLDB" database that simulates a real SQL database.
		 * (See the class DatabaseUtil.)  You can use standard JDBC calls to access this database.
		 * If you need an introduction to JDBC, we recommend this link:
		 *    http://infolab.stanford.edu/~ullman/fcdb/oracle/or-jdbc.html#0.1_executeQuery
		 *    
		 * The 'main' method should do three things:
		 * 
		 * 1. Create two new Person objects (with the data below) and insert them in the database.
		 *       John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 *       Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		 *       
		 * 2. Print the entire phone book to System.out.
		 * 
		 * 3. Find just the Person for Cynthia Smith, and print her entry to System.out.
		 *    
		 *    
		 * The main goal is simply get this to work.  But we are also very interested in
		 * any improvements or refactorings that you choose to do, that still retain
		 * the same functionality.
		 */
	}
}
