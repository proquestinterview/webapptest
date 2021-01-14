package com.proquest.interview.phonebook;

public class Person {
	
	private String name;
	private String phoneNumber;
	private String address;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "Person ["
				+ (name != null ? "name=" + name + ", " : "")
				+ (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", "
						: "") + (address != null ? "address=" + address : "")
				+ "]";
	}	

}
