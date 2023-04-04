package com.epam.rd.java.basic.topic07.task01.db.entity;

public class User {

	private int id;

	private String login;

	public User(int id, String login) {
		this.id = id;
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return login;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return this.login.equals(other.login);
		}
		return false;
	}

	public static User createUser(String login){
		return new User(0,login);
	}
}