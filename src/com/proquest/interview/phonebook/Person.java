package com.proquest.interview.phonebook;

public class Person {
	public String name;
	public String phoneNumber;
	public String address;
	
	public Person (String name, String phone, String address) {
		this.name = name;
		this.phoneNumber = phone;
		this.address = address;
	}
	
	public String toString() {
		return name + ", " + phoneNumber + ", " + address;
	}
}
