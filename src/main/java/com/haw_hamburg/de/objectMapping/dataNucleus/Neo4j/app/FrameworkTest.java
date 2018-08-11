package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app;

import java.io.File;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.haw_hamburg.de.objectMapping.DataNucleus.Neo4j.utils.Result;

public class FrameworkTest {

	// Result Object
	private Result resultWrite;
	private Result resultRead;

	// Testkonfig
	public Integer inserts = 100000;
	public Integer runs = 5;

	StoreActivity storeActivity;
	ReadActivity readActivity;
	// Persistence Manager Factory
	private PersistenceManagerFactory persistenceManagerFactory;

	Properties properties = new Properties();

	public FrameworkTest() {

	}

	public FrameworkTest(Integer inserts, Integer runs) {
		this.inserts = inserts;
		this.runs = runs;
		properties.setProperty("javax.jdo.PersistenceManagerFactoryClass",
				"org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		properties.setProperty("javax.jdo.option.ConnectionURL",
				"neo4j:/home/diana/Dokumente/userPostsDataNucleusNeo4j");
		persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory(properties);
		storeActivity = new StoreActivity(inserts, persistenceManagerFactory);
		readActivity = new ReadActivity(persistenceManagerFactory);
	}

	public Result performWriteTest() throws Exception {
		//
		// Intialize Variables
		this.resultWrite = new Result();

		//
		// Execute Runs
		for (Integer i = 0; i < this.runs; i++) {

			// Record Start Time
			long startTime = System.nanoTime();

			// Insert Documents
			storeActivity.persistEntitiesDataNucleus();

			// Record End Time and calculate Run Time
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;

			resultWrite.addMeasureResult("Write Run" + (i), seconds, this.inserts, true);
			System.out.println("Write Run" + (i) + " finished");

		}
		storeActivity.closeConnection();

		// Print Result
		return this.resultWrite;

	}

	public Result performReadTest() throws Exception {
		// Intialize Variables
		this.resultRead = new Result();

		// Record Start Time
		long startTime = System.nanoTime();

		/// Read Documents
		// readActivity.readEntities();
		readActivity.readUsers();

		// Record End Time and calculate Run Time
		long estimatedTime = System.nanoTime() - startTime;
		double seconds = (double) estimatedTime / 1000000000.0;

		resultRead.addMeasureResult("Read All Entries", seconds, this.inserts * this.runs, false);
		//
		// }
		this.readActivity.closeConnection();
		persistenceManagerFactory.close();

	//	System.out.println("USERS: " + readActivity.getUsers().size());

		// Print Result
		return this.resultRead;
	}

	public void printDbContents() {
		final GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("/home/diana/Dokumente/userPostsDataNucleusNeo4j"));

		final Transaction tx = graphDb.beginTx();
		try {

			for (final Node node : graphDb.getAllNodes()) {
				System.out.print("Node: " + node.getId() + " ");
				for (final String key : node.getPropertyKeys()) {
					System.out.print("Property: " + key + " - " + node.getProperty(key) + ", ");
				}
				System.out.print("\n");
			}
			for (final Relationship relationship : graphDb.getAllRelationships()) {
				System.out.print("Relationship: " + relationship.getId() + " ");
				for (final String key : relationship.getPropertyKeys()) {
					System.out.print("Property: " + key + " - " + relationship.getProperty(key) + ", ");
					System.out.print("Start Node: " + relationship.getStartNode().getId() + ",");
					System.out.print("End Node: " + relationship.getEndNode().getId() + ", ");
				}
				System.out.print("\n");
			}
			tx.success();
		} finally {
			tx.terminate();
			graphDb.shutdown();
		}
	}
}
