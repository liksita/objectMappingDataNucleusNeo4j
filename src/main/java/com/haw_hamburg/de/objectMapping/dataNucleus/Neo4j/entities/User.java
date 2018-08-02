package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable
public class User {
	
	@PrimaryKey
	@Persistent(customValueStrategy="identity")
	private long id;

	private String firstName;
	private String lastName;
	
	private LoginData loginData;
	
	private Set<Post> userPosts = new HashSet<>();

	private Set<Comment> userComments = new HashSet<>();
	
	private Set<Discussion> discussions = new HashSet<>();

	// constructors, getters and setters...

	User() {
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Post> getUserPosts() {
		return userPosts;
	}

	public void setUserPosts(Set<Post> userPosts) {
		this.userPosts = userPosts;
	}
	
	public Set<com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Comment> getUserComments() {
		return userComments;
	}

	public void setUserComments(Set<com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Comment> userComments) {
		this.userComments = userComments;
	}

	public LoginData getLoginData() {
		return loginData;
	}

	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}

	public Set<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(Set<Discussion> discussions) {
		this.discussions = discussions;
	}

}
