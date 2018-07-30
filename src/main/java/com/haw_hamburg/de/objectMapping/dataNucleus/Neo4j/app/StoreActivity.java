package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Comment;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Discussion;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.LoginData;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.Post;
import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.entities.User;

public class StoreActivity {

	// Testkonfig
	public Integer inserts = 1000;

	private static int runCount = 0;

	PersistenceManager pm;

	public StoreActivity(Integer inserts, PersistenceManagerFactory persistenceManagerFactory) {
		this.inserts = inserts;
		this.pm = persistenceManagerFactory.getPersistenceManager();
	}

	public void persistEntitiesDataNucleus() {

		runCount++;

		for (int j = 0; j < this.inserts; j++) {

			Transaction tx = pm.currentTransaction();

			try {
				tx.begin();

				// create a User
				User user1 = new User("user1", "user1" + runCount + j);
				User user2 = new User("user2", "user2" + runCount + j);

				LoginData loginData1 = new LoginData("user1", "password1");
				LoginData loginData2 = new LoginData("user2", "password2");

				user1.setLoginData(loginData1);
				user2.setLoginData(loginData2);

				Discussion discussion1 = new Discussion("discussion1" + runCount + j);
				Discussion discussion2 = new Discussion("discussion2" + runCount + j);

				discussion1.getUsers().add(user1);
				discussion1.getUsers().add(user2);
				user1.getDiscussions().add(discussion1);
				user1.getDiscussions().add(discussion2);

				discussion2.getUsers().add(user1);
				discussion2.getUsers().add(user2);
				user2.getDiscussions().add(discussion2);
				user2.getDiscussions().add(discussion1);

				// and three posts
				Post post1 = new Post("Title1 " + runCount + j, new Date());
				post1.setTitle("post1");
				Post post2 = new Post("Title2 " + runCount + j, new Date());
				post2.setTitle("post2");
				Post post3 = new Post("Title3 " + runCount + j, new Date());
				post3.setTitle("post3");

				// and two Comments
				Comment comment1 = new Comment(new Date());
				comment1.setTitle("comment1");
				Comment comment2 = new Comment(new Date());
				comment2.setTitle("comment2");

				// let Bob post two posts
				post1.setAuthor(user1);
				user1.getUserPosts().add(post1);

				comment1.setAuthor(user1);
				user1.getUserComments().add(comment1);

				comment2.setAuthor(user1);
				user1.getUserComments().add(comment2);

				comment1.setPost(post1);
				post1.getUserComments().add(comment1);

				comment2.setPost(post2);
				post2.getUserComments().add(comment2);

				post2.setAuthor(user1);
				user1.getUserPosts().add(post2);

				post3.setAuthor(user2);
				user2.getUserPosts().add(post3);

				pm.makePersistent(discussion1);
				pm.makePersistent(discussion2);

				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}

			}
		}
	}

	public Integer getInserts() {
		return inserts;
	}

	public void setInserts(Integer inserts) {
		this.inserts = inserts;
	}

	public void closeConnection() {
		pm.close();
	}

}
