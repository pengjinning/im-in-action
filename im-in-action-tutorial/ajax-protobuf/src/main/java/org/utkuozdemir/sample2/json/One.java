package org.utkuozdemir.sample2.json;

import java.util.Collection;

public class One {
	private String name;
	private Collection<Many> many;

	public One() {
	}

	public One(String name, Collection<Many> many) {
		this.name = name;
		this.many = many;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Many> getMany() {
		return many;
	}

	public void setMany(Collection<Many> many) {
		this.many = many;
	}
}
