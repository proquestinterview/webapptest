package com.proquest.interview.phonebook;

/**
 * Person represents a person with a name, address, and phone number.
 * Instances of this class are immutable.
 *
 */
public class Person {
	private final String name;
	private final String phoneNumber;
	private final String address;
	
	/**
	 * Creates a person. The name, address, and phone number may not be null.
	 * 
	 * @param name the full name of the person
	 * @param phoneNumber the person's phone number
	 * @param address the person's address
	 * @throws IllegalArgumentException if any of the parameters are null
	 */
	public Person(String name, String phoneNumber, String address) {
		if (name == null || phoneNumber == null || address == null) {
			throw new IllegalArgumentException("null");
		}
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	/** Returns the person's name. */
	public String getName() {
		return name;
	}

	/** Returns the person's phone number. */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/** Returns the person's address. */
	public String getAddress() {
		return address;
	}
	
	/** Returns a string representation of the person. */
	@Override public String toString() {
		return name + ", " + phoneNumber + ", " + address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
}
