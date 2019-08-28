package org.utkuozdemir.sample1.json;

import static java.util.Objects.requireNonNull;

public class Address {
	private String firstLine;
	private String secondLine;
	private Integer postalCode;

	public Address() {
	}

	public Address(String firstLine, String secondLine, Integer postalCode) {
		this.firstLine = requireNonNull(firstLine);
		this.secondLine = secondLine;
		this.postalCode = postalCode;
	}

	public String getFirstLine() {
		return firstLine;
	}

	public String getSecondLine() {
		return secondLine;
	}

	public Integer getPostalCode() {
		return postalCode;
	}
}
