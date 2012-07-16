package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people;

	public PhoneBookImpl() {
		people = new ArrayList<Person>();
	}

	// @Override
	public void addPerson(Person newPerson) {
		people.add(newPerson); // Done: write this method
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.proquest.interview.phonebook.PhoneBook#findPerson(java.lang.String,
	 * java.lang.String)
	 * 
	 * Search the current in memory list of people
	 */
	@Override
	public Person findPerson(String firstName, String lastName) {
		String target = firstName + " " + lastName;

		for (Person p : people) {
			if (p.name.equals(target))
				return p;
		}
		return null;
	}

	/**
	 * loadPeople
	 * 
	 * Read from the database all of the people, flag each as in database
	 */
	private void loadPeople() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM PHONEBOOK");

			rs.next();
			while (!rs.isAfterLast()) {
				Person p = new Person();
				p.name = rs.getString("NAME");
				p.phonenumber = rs.getString("PHONENUMBER");
				p.address = rs.getString("ADDRESS");
				p.inDB = true;
				people.add(p);

				rs.next();
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Error: Unable to find SQL DB");
		} catch (SQLException e) {
			System.out.println("Error: SQL Exception");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Save any people in the 'people' list to the database
	 */
	private void savePeople() {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DatabaseUtil.getConnection();
			stmt = conn.createStatement();

			for (Person p : people) {
				if (!p.inDB) {
					String iS;
					iS = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES("
							+ "'" + p.name 	+ "',"
							+ "'" + p.phonenumber + "',"
							+ "'" + p.address + "'" + 
							")";
					stmt.execute(iS);
					p.inDB = true;
				}
			}
			conn.commit();

		} catch (ClassNotFoundException e) {
			System.out.println("Error: Unable to find SQL DB");
		} catch (SQLException e) {
			System.out.println("Error: SQL Exception");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public void printDatabase() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		System.out.println("----------------- Dump Database -----------------");

		try {
			conn = DatabaseUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM PHONEBOOK");

			rs.next();
			while (!rs.isAfterLast()) {
				System.out.println(rs.getString("NAME") + " "
						+ rs.getString("ADDRESS") + " "
						+ rs.getString("PHONENUMBER"));
				rs.next();
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Error: Unable to find SQL DB");
		} catch (SQLException e) {
			System.out.println("Error: SQL Exception");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public void printPeople() {
		System.out
				.println("----------------- Dump of People -----------------");
		for (Person p : people) {
			System.out.println(p);
		}
	}

	public static void main(String[] args) {
		Person workPerson;
		PhoneBookImpl pbi = new PhoneBookImpl();

		DatabaseUtil.initDB(); // You should not remove this line, it creates
		pbi.printDatabase();
		// the in-memory database

		pbi.loadPeople();

		workPerson = new Person();
		workPerson.name = "John Smith";
		workPerson.address = "1234 Sand Hill Dr, Royal Oak, MI";
		workPerson.phonenumber = "(248) 123-4567";

		pbi.addPerson(workPerson);

		//
		workPerson = new Person();
		workPerson.name = "Cynthia Smith";
		workPerson.address = "875 Main St, Ann Arbor, MI";
		workPerson.phonenumber = "(824) 128-8758";

		pbi.addPerson(workPerson);

		// Done: print the phone book out to System.out
		pbi.printPeople();

		// DONE: find Cynthia Smith and print out just her entry

		String firstName, lastName;
		firstName = "Cynthia";
		lastName = "Smith";
		workPerson = new Person();
		workPerson = pbi.findPerson(firstName, lastName);

		System.out.println("Searching for:" + firstName + " " + lastName);

		if (workPerson != null)
			System.out.println("Found: " + workPerson);
		else
			System.out.println("Person not found");

		pbi.savePeople(); // Done: insert the new person objects into the
							// database
		pbi.printDatabase();

		DatabaseUtil.deinitDB();
	}

}
