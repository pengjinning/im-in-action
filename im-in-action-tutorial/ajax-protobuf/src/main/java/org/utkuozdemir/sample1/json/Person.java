package org.utkuozdemir.sample1.json;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class Person {
	private String firstName;
	private String lastName;
	private int age;
	private boolean active;
	private Gender gender;
	private Collection<Address> addresses;

	public Person() {
	}

	public Person(String firstName, String lastName, int age, boolean active,
				  Gender gender, Collection<Address> addresses) {
		this.firstName = requireNonNull(firstName);
		this.lastName = requireNonNull(lastName);
		this.age = age;
		this.active = active;
		this.gender = gender;
		this.addresses = requireNonNull(addresses);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public boolean isActive() {
		return active;
	}

	public Gender getGender() {
		return gender;
	}

	public Collection<Address> getAddresses() {
		return addresses;
	}
}
