package com.proquest.interview.phonebook;

public class Person {
	
	public String name;
	public String phoneNumber;
	public String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Person [Name= " + name + ", PhoneNumber= " + phoneNumber + ", Address= " + address + "]";
	}
	
	
	
}
