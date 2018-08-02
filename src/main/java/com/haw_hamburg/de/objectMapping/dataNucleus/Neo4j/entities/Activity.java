package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities;

import java.util.Date;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@Inheritance(strategy=javax.jdo.annotations.InheritanceStrategy.COMPLETE_TABLE)
public abstract class Activity {

	@PrimaryKey
	@Persistent(customValueStrategy="uuid")
	String id;
	
	String title;

	Date date;
	
	User author;

	// constructors, getters and setters...

	public Activity(Date date) {
		this.date = date;
	}

	public abstract String getId();

	public abstract void setId(String id);
	
	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract Date getDate();

	public abstract void setDate(Date date);

	public abstract User getAuthor();

	public abstract void setAuthor(User author);


}