package com.proquest.interview.phonebook;

public class Person {
	public String name;
	public String phonenumber;	// Fixed case
	public String address;
	public boolean inDB;		// Added to indicate in-memory only
	
	public String toString()
	{
		return(name + " " + address + " " + phonenumber );
	}
}
