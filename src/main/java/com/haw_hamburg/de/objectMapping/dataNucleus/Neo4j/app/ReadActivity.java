package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app;

import java.util.List;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Comment;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Discussion;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Post;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.User;

public class ReadActivity {

	private List<User> users;
	private List<Post> posts;
	private List<Comment> comments;
	private List<Discussion> discussions;

	PersistenceManager persistenceManager;

	public ReadActivity(PersistenceManagerFactory persistenceManagerFactory) {
		persistenceManager = persistenceManagerFactory.getPersistenceManager();
	}

	@SuppressWarnings("unchecked")
	public void readEntities() {
		persistenceManager.currentTransaction().begin();
		Query<User> queryUsers = persistenceManager
				.newQuery("SELECT FROM com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.User ");
		users = queryUsers.executeList();
		Query<Post> queryPosts = persistenceManager
				.newQuery("SELECT FROM com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Post ");
		posts = queryPosts.executeList();
		Query<Comment> queryComments = persistenceManager
				.newQuery("SELECT FROM com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Comment ");
		comments = queryComments.executeList();
		Query<Discussion> queryDiscussions = persistenceManager
				.newQuery("SELECT FROM com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Discussion ");
		discussions = queryDiscussions.executeList();
		persistenceManager.currentTransaction().commit();

	}

	public void closeConnection() {
		persistenceManager.close();
	}

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("javax.jdo.PersistenceManagerFactoryClass",
				"org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		properties.setProperty("javax.jdo.option.ConnectionURL",
				"neo4j:/home/diana/Dokumente/userPostsDataNucleusNeo4j");
		PersistenceManagerFactory persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory(properties);
		ReadActivity readActivity = new ReadActivity(persistenceManagerFactory);
		readActivity.readEntities();
		System.out.println("User count: " + readActivity.getUsers().size());
		System.out.println("Post count: " + readActivity.getPosts().size());
		System.out.println("Comment count: " + readActivity.getComments().size());
		System.out.println("Discussion count: " + readActivity.getDiscussions().size());
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}

}
