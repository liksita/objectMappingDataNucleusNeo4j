package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(embeddedOnly="true")
public class LoginData {

	private String id;
	private String username;
	private String password;

	private User user;

	// constructors, getters and setters...

	LoginData() {
	}

	public LoginData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}