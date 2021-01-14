package com.proquest.interview.phonebook;

import com.proquest.interview.util.DatabaseUtil;
import com.proquest.interview.util.ErrorLogger;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookImpl implements PhoneBook {

	static Logger logger = Logger.getLogger(PhoneBookImpl.class);
	
	private List<Person> people = new ArrayList<Person>();
	
	// NOTE: All of this DB stuff should probably be moved into a DAO layer
	
	@Override
	public void addPerson(Person newPerson) {
		
		people.add(newPerson);
		
		Connection conn = null;
		PreparedStatement ps = null;
		// NOTE: getSqlValue will escape values properly, so we can find names like D'Angelo, etc...
		// Adding single quotes manually is for sissies.  That's what computers are for.
		// Should probably put the column names into an enum or at least private final static Strings
		String query = "INSERT INTO phonebook (name, phoneNumber, address) VALUES (?, ?, ?)";

		// NOTE: we should use a real profiling lib like log4jdbc
		// Allow for trace-/debug-level logging of queries
		logger.debug("Executing query: " + query);

		try {

			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, newPerson.getName());
			ps.setString(2, newPerson.getPhoneNumber());
			ps.setString(3, newPerson.getAddress());

			ps.executeUpdate();

		} catch (Exception ex) {
			logger.error("Problem finding person in DB. Query:" + query);
			ErrorLogger.filterStackTrace(ex); // NOTE: I don't need to see this entire exception every time.  Just enough to trace it in our domain code.
		
		} finally {
			DatabaseUtil.closeAll(ps, conn, query);
		}

	}
	
	/**
	 * NOTE: This method assumes that only the first person found will be returned.
	 */
	@Override
	public Person findPerson(String firstName, String lastName) {
		//TODO: write this method
		
		Person person = new Person();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "SELECT name, phonenumber, address FROM phonebook WHERE name LIKE " + 
				DatabaseUtil.getSqlValue(firstName + " " + lastName) +
				" LIMIT 1";

		logger.debug("Executing query: " + query);

		try {
			conn = DatabaseUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				// NOTE: small efficiency gains by using column indices instead of names are usually trumped by readability/maintainability
				// Optimize when you need to.
				person.setName(rs.getString("name"));
				person.setPhoneNumber(rs.getString("phonenumber"));
				person.setAddress(rs.getString("address"));
				logger.debug("Finding person: " + person);
			}
			
		} catch (Exception ex) {
			
			logger.error("Problem finding person in DB. Query:" + query);
			ErrorLogger.filterStackTrace(ex);
		
		} finally {
			DatabaseUtil.closeAll(rs, stmt, conn, query);
		}

		return person;
		
	}
	
	/**
	 * getter + setter = better (than leaving _people_ publically accessible)
	 * Also 
	 */
	@Override
	public List<Person> getPeople() {
		return people;
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 

		PhoneBook phoneBook = new PhoneBookImpl();
		Person p1 = new Person(), p2 = new Person();
		
		p1.setName("John Smith");
		p1.setAddress("1234 Sand Hill Dr, Royal Oak, MI");
		p1.setPhoneNumber("(248) 123-4567");
		
		p2.setName("Cynthia Smith");
		p2.setAddress("875 Main St, Ann Arbor, MI");
		p2.setPhoneNumber("(824) 128-8758");
		
		System.out.println("\n1. Adding people...");
		phoneBook.addPerson(p1);
		phoneBook.addPerson(p2);

		System.out.println("\n2. Printing phone book...");
		// TODO: print the phone book out to System.out
		for (Person person : phoneBook.getPeople()) {
			System.out.println(person);
		}
		
		// TODO: find Cynthia Smith and print out just her entry
		
		System.out.println("\n3. Finding a person...");
		System.out.println(phoneBook.findPerson("Cynthia", "Smith"));
		
		// TODO: insert the new person objects into the database
		// done above
	}
}
