package com.researchspace.mendeley.api;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Author {
	@Override
	public String toString() {
		return "Author [first_name=" + firstName + ", last_name=" + lastName + "]";
	}
	private String firstName;
	private String lastName;
	@JsonGetter("first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonGetter("last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
