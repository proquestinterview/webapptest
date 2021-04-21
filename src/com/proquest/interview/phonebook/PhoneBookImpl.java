package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people = new ArrayList<Person>();

	@Override
	public void addPerson(Person newPerson) {
		if (newPerson == null) throw new IllegalArgumentException("null");
		people.add(newPerson);
	}

	@Override
	public Person findPerson(String firstName, String lastName) {
		if (firstName == null || lastName == null) {
			throw new IllegalArgumentException("null");
			// the other option here is to return null, indicating not found
		}
		final String nameSought = firstName + " " + lastName;
		for (Person person : people) {
			if (nameSought.equals(person.getName())) return person;
		}
		return null;
	}

	public Collection<Person> allPeople() {
		return Collections.unmodifiableList(people);
	}
	
	@Override public String toString() {
		return people.toString();
	}

	public static void main(String[] args) {
		DatabaseUtil.initDB(); // You should not remove this line, it creates the in-memory database

		PhoneBook phoneBook = new PhoneBookImpl();
		
		// create person objects and put them in the PhoneBook and database

		phoneBook.addPerson(new Person("John Smith", "(248) 123-4567",
				"1234 Sand Hill Dr, Royal Oak, MI"));
		phoneBook.addPerson(new Person("Cynthia Smith", "(824) 128-8758",
				"875 Main St, Ann Arbor, MI"));

		// print the phone book out to System.out
		
		// It isn't clear to me which of these you want:
		
		System.out.println(phoneBook);
		
		for (Person person : phoneBook.allPeople()) {
			System.out.println(person);
		}
		
		// find Cynthia Smith and print out just her entry

		System.out.println("Cynthia:");
		Person cynthiaSmith = phoneBook.findPerson("Cynthia", "Smith");
		System.out.println(cynthiaSmith);

		// It was a little unclear when you wanted me to add the objects
		// to the database. At the top, you said to add people to the
		// phone book and the database, but at the end, you said to add
		// the new people to the database.
		
		// insert the new person objects into the database

		insertPeople(phoneBook.allPeople());
	}
	
	public static ResultSetHandler<List<Person>> rowsToPeople() {
		return new ResultSetHandler<List<Person>>() {
			public List<Person> handle(ResultSet rs) throws SQLException {
				List<Person> people = new ArrayList<Person>();
				while (rs.next()) {
					people.add(new Person(rs.getString(1), rs.getString(2), rs.getString(3)));
				}
				return people;
			}
		};
	}
	
	public static List<Person> byCriteria(String sql, Object... criteria) {
		Connection con = null;
		try {
			con = getConnection();
			return new QueryRunner().query(con, sql, rowsToPeople(), criteria);
		} catch (SQLException e) {
			e.printStackTrace();
			// questionable; maybe we should throw some application-specific exception to indicate failure
			return new ArrayList<Person>();
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public static List<Person> everyone() {
		return byCriteria("SELECT * FROM PHONEBOOK", new Object[] {});
	}
	
	public static List<Person> byName(String name) {
		return byCriteria("SELECT * FROM PHONEBOOK WHERE NAME = ?", name);
	}
	
	public static List<Person> byPhoneNumber(String phoneNumber) {
		return byCriteria("SELECT * FROM PHONEBOOK WHERE PHONENUMBER = ?", phoneNumber);
	}
	
	public static List<Person> byAddress(String address) {
		return byCriteria("SELECT * FROM PHONEBOOK WHERE ADDRESS = ?", address);
	}
	
	public static void insertPeople(Collection<Person> people) {
		final String sql =
				"INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?, ?, ?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			QueryRunner q = new QueryRunner();
			for (Person person : people) {
				q.fillStatementWithBean(ps, person, "name", "phoneNumber", "address");
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		try {
			return DatabaseUtil.getConnection();
		} catch (ClassNotFoundException e) {
			// avoid dealing with CNFE; we aren't going to do anything different in that case anyway
			throw new SQLException(e);
		}
	}
}
