package com.epam.rd.java.basic.topic07.task01.db.entity;

public class Team {

	private int id;
		
	private String name;

	public Team(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			Team other = (Team) obj;
			return this.name.equals(other.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	public static Team createTeam(String name){
		return new Team(0,name);
	}
}