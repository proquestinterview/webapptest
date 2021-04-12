package com.proquest.interview.phonebook.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.phonebook.entity.Person;
import com.proquest.interview.phonebook.exception.ServiceException;
import com.proquest.interview.phonebook.service.PhoneBook;
import com.proquest.interview.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhoneBookImpl implements PhoneBook {
	@Override
	public void addPerson(Person newPerson) throws Exception {

		Connection cn = DatabaseUtil.getConnection();
		try {
			PreparedStatement stmt = cn
					.prepareStatement("INSERT INTO PHONEBOOK (FST_NAME, LST_NAME, PHONENUMBER, ADDRESS) VALUES(?, ?, ?, ?)");
			stmt.setString(1, newPerson.name.split(" ")[0].trim());
			stmt.setString(2, newPerson.name.split(" ")[1].trim());
			stmt.setString(3, newPerson.phoneNumber);
			stmt.setString(4, newPerson.address);
			int i = stmt.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getLocalizedMessage());
		} finally {
			cn.close();
		}
	}

	@Override
	public List<Person> findPerson(String firstName, String lastName) throws Exception {
		Connection cn = DatabaseUtil.getConnection();
		List<Person> persons = new ArrayList<Person>();
		Person person = null;
		try {
			PreparedStatement stmt = cn.prepareStatement("select * from PHONEBOOK where FST_NAME = ? and LST_NAME = ?");
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				person = new Person();
				person.name = rs.getString(1) + " " + rs.getString(2);
				person.phoneNumber = rs.getString(3);
				person.address = rs.getString(4);
				persons.add(person);
			}
		} catch (SQLException e) {
			throw new ServiceException(e.getLocalizedMessage());
		} finally {
			cn.close();
		}
		return persons;
	}

	@Override
	public List<Person> findAllPersons() throws Exception {
		Connection cn = DatabaseUtil.getConnection();
		List<Person> persons = new ArrayList<Person>();
		Person person = null;
		try {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from PHONEBOOK");
			while (rs.next()) {
				person = new Person();
				person.name = rs.getString(1) + " " + rs.getString(2);
				person.phoneNumber = rs.getString(3);
				person.address = rs.getString(4);
				persons.add(person);
			}
		} catch (SQLException e) {
			throw new ServiceException(e.getLocalizedMessage());
		} finally {
			cn.close();
		}
		return persons;
	}
}
