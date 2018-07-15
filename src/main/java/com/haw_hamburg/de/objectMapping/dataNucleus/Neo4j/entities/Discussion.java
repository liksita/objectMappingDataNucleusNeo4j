package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Discussion {

	@PrimaryKey
	@Persistent(customValueStrategy="identity")
	private long id;

	private String topic;

//	@Join(table = "User")
	private Set<User> users = new HashSet<>();

	public Discussion(String topic) {
		this.topic = topic;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
