package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.ConnectionUtil;
import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people = new ArrayList<Person>();
	private Connection connection;
	
	@Override
	public void addPerson(Person newPerson) {
		if(newPerson == null)
			throw new IllegalArgumentException();
		people.add(newPerson);
	}
	
	@Override
	public Person findPerson(String fName, String lName) {
		String name = fName + " " + lName;
		for(Person p : people) {
			if(name.equals(p.getName())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Person p : people) {
			builder.append(p).append("\n");
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		/* create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/
		final PhoneBook book = new PhoneBookImpl();
		final Person john = new Person(
				"John",
				"Smith",
				"(248) 123-4567",
				"1234 Sand Hill Dr, Royal Oak, MI");
		book.addPerson(john);
		final Person cynthia = new Person(
				"Cynthia",
				"Smith",
				"(824) 128-8758",
				"875 Main St, Ann Arbor, MI");
		book.addPerson(cynthia);
		// print the phone book out to System.out
		System.out.println(book);
		// find Cynthia Smith and print out just her entry
		final Person found = book.findPerson("Cynthia", "Smith");
		if(found == null)
			System.out.println("Cynthia not found");
		else
			System.out.println(found);

		// insert the new person objects into the database
		ConnectionUtil cu = new ConnectionUtil();
		try {
			insertPersonToDB(cu.getConnection(), john);
			insertPersonToDB(cu.getConnection(), cynthia);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			cu.closeConnection();
		}
	}

	private static void insertPersonToDB(Connection connection, Person toInsert) throws SQLException {
		final Statement stmt = connection.createStatement();
		stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) " +
						"VALUES('" + toInsert.getName() + "','" + toInsert.getPhoneNumber() + "','" +
				toInsert.getAddress() + "')");
		connection.commit();
	}
}
