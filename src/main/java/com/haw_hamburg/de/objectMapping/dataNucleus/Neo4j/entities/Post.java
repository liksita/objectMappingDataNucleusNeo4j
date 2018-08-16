package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Post extends Activity {

	@Persistent(defaultFetchGroup="true")
	private Set<Comment> userComments = new HashSet<>();

	// constructors, getters and setters...

	public Post(String title, Date date) {
		super(date);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Set<Comment> getUserComments() {
		return userComments;
	}

	public void setUserComments(Set<Comment> userComments) {
		this.userComments = userComments;
	}

}
