package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.phonebook.Person;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		Person cj = new Person ("Chris Johnson", "(321) 231-7876", "452 Freeman Drive, Algonac, MI");
		Person dw = new Person ("Dave Williams", "(231) 502-1236", "285 Huron St, Port Austin, MI");
		
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.close();
			cn.commit();
			
			addPerson (cn, cj);
			addPerson (cn, dw);
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}
	
	public static void addPerson (Connection cn, Person person) {
		try {
			Statement stmt = cn.createStatement();
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES("
					+ quoted(person.name) + ","
					+ quoted(person.phoneNumber) + ","
					+ quoted(person.address)
					+ ")");
			stmt.close();
			cn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static List<Person> getPeople(Connection cn, String name) {
		List<Person> results = new ArrayList<Person>();
		try {
			Statement stmt = cn.createStatement();
			String selector = (name != null  &&  ! name.isEmpty())
					? "SELECT * FROM PHONEBOOK WHERE name = '" + name + "'"
					: "SELECT * FROM PHONEBOOK";
			ResultSet rs = stmt.executeQuery(selector);
			while (rs.next()) {
				Person person = new Person (
						rs.getString("NAME"),
						rs.getString("PHONENUMBER"),
						rs.getString("ADDRESS")
				);
				results.add(person);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private static String quoted(String text) {
		return "'" + text + "'";
	}
}
