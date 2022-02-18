package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	//public List people;
	
	@Override
	public void addPerson(Person newPerson) {
		
	if(newPerson != null) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			String insertQuery = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?,?,?)";
			
			con = DatabaseUtil.getConnection();
			
			if (con !=null) {
				
				stmt = con.prepareStatement(insertQuery);
				stmt.setString(1, newPerson.getName());
				stmt.setString(2, newPerson.getPhoneNumber());
				stmt.setString(3, newPerson.getAddress());
				stmt.execute();
				con.commit();
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
					if(stmt != null) {
						stmt.close();
					}
					if(con != null) {
						con.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
	}
		
}
	
	@Override
	public List<Person> findPerson() {		
		return this.searchPerson(null, null);
		
}
	
	public static void main(String[] args) {
		
			DatabaseUtil.initDB();  // Do not remove this line, it creates the simulated database.
			
			// Context: the basic idea is that the phone book lives in ("is persisted to") an
			// SQL database.  For this exercise, we're using a simulated database (that really just
			// lives in memory), but pretend that it is a "real", persisted-on-disk database.
			//
			// But ALL of the data should live in-memory in an instance of
			// the PhoneBookImpl class, AS WELL AS being persisted to the database.
		
			PhoneBook phoneBook  = new PhoneBookImpl();
				
			// TODO: 1. Create these new Person objects, and put them in both PhoneBook and Database.
			//    John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
			//    Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI

			Person person = null;
			
			// Adding First person
			person = preparePersonObject("John Smith","(248) 123-4567","1234 Sand Hill Dr, Royal Oak, MI");
			phoneBook.addPerson(person);
			
			//Adding second person
			person = preparePersonObject("Cynthia Smith","(824) 128-8758","875 Main St, Ann Arbor, MI");
			phoneBook.addPerson(person);
		
		// TODO 2: Print the whole phone book to System.out.
			
			processPersonResult(phoneBook.findPerson());
			
		// TODO 3: Find Cynthia Smith and print just her entry to System.out.
			
			processPersonResult(phoneBook.findPerson("cynthia","Smith"));
					
		// Hint: you don't have to implement these features strictly in that order.
	}

	
	@Override
	public List<Person> findPerson(String firstName, String lastName) {
		
		
		return this.searchPerson(firstName, lastName);
		
	
	}
	
	public List<Person> searchPerson(String firstName, String lastName) {

		List<Person> listPerson = new ArrayList<Person>();
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			
			StringBuffer selectQuery = new StringBuffer("SELECT * FROM PHONEBOOK");
			
			if(!(StringUtil.isEmpty(firstName) || StringUtil.isEmpty(lastName))) {
				selectQuery.append(" WHERE LOWER(NAME)=?");
			}
			
			con = DatabaseUtil.getConnection();
			stmt = con.prepareStatement(selectQuery.toString());
			
			
			if(!(StringUtil.isEmpty(firstName) || StringUtil.isEmpty(lastName))) {
				stmt.setString(1, (firstName + " " + lastName).toLowerCase());
			}
		
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
		        
		        Person person = new Person();
		        person.setName(rs.getString("NAME"));
		        person.setPhoneNumber(rs.getString("PHONENUMBER"));
		        person.setAddress(rs.getString("ADDRESS"));
		  
		        listPerson.add(person);

		      }
			
		}catch(SQLException e){
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			   // TODO Throw the user defined exception
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}

		return listPerson;
	}


	private static Person preparePersonObject(String name, String phoneNumber, String address) {
		  	Person person = new Person();
	        person.setName(name);
	        person.setPhoneNumber(phoneNumber);
	        person.setAddress(address);
	        
		return person;
		
	}
	
	private static void processPersonResult(List<Person> listPerson) {
		
		if(listPerson != null && ! listPerson.isEmpty()) {
			for(Person searchPerson : listPerson)
				System.out.println(searchPerson);
		}else
		{
			System.out.println("No person found");
		}
	}
	
	/** 
	 * Improvement 1:  We can create a new user defined exception class and customize error messages accordingly.
	 * Improvement 2: insertQuery can be placed in a property file and read from there instead of keeping hardcoded query here.
	 */
}
