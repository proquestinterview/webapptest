package com.proquest.interview.phonebook;

public class Person {
	private String name;
	private String phoneNumber;
	private String address;

	public Person(String fName, String lName, String phoneNumber, String address) {
		this.name = fName + " " + lName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getName() {
	    return name;
    }

	public String getFirstName() {
		return name.substring(0, name.indexOf(" "));
	}

	public String getLastName() {
		return name.substring(name.indexOf(" ")+1, name.length());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
    public String toString() {
	    return getName() + ", " + getPhoneNumber() + ", " + getAddress();
    }

}
