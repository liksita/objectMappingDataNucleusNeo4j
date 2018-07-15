package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.Pair;

import com.haw_hamburg.de.objectMapping.DataNucleus.Neo4j.utils.Result;

public class FrameworkTest {

	// Result Object
	private Result result;
	//
	// // Node and Port Config
	// private String node = "localhost";
	// private Integer port = 27017;
	//
	// // Host
	// private MongoClient mongoClient;
	//
	// // Collection
	// private String collection_user_name = "User";
	// private DBCollection collection_user = null;
	//
	// // Collection
	// private String collection_post_name = "Post";
	// private DBCollection collection_post = null;
	//
	// // DB
	// private String db_name = "UserPosts";
	// private DB db = null;
	//
	// Testkonfig
	public Integer inserts = 100000;
	public Integer runs = 5;

	MongoHibernate mh;

	public FrameworkTest() {

	}

	public FrameworkTest(Integer inserts, Integer runs) {
		this.inserts = inserts;
		this.runs = runs;
		mh = new MongoHibernate(inserts);
	}

	//
	// public Integer getInserts() {
	// return inserts;
	// }
	//
	// public void setInserts(Integer inserts) {
	// this.inserts = inserts;
	// }
	//
	// public Integer getRuns() {
	// return runs;
	// }
	//
	// public void setRuns(Integer runs) {
	// this.runs = runs;
	// }
	//
	// private ServerAddress initialise() throws UnknownHostException {
	// ServerAddress addrs = new ServerAddress(this.node, this.port);
	// return addrs;
	// }
	//
	public Result performWriteTest() throws Exception {
		//
		// Intialize Variables
		this.result = new Result();
		//
		// // Create Test Environment
		// createTestEnvironment();
		//
		// Execute Runs
		for (Integer i = 0; i < this.runs; i++) {

			// Record Start Time
			long startTime = System.nanoTime();

			// Insert Documents
			// mh.persistEntities();
			mh.persistEntitiesDataNucleus();

			// Print Count
			// printCount();

			// Record End Time and calculate Run Time
			long estimatedTime = System.nanoTime() - startTime;
			double seconds = (double) estimatedTime / 1000000000.0;

			result.addMeasureResult("Run" + (i), seconds, this.inserts);
			System.out.println("Run" + (i) + " finished");
			mh.closeConnection();
			printDbContents();

		}

		// // Delete Test Environment
		// deleteTestEnvironment();

		// Print Result
		return this.result;

	}
	//
	// private void createTestEnvironment() throws Exception {
	//
	// // Get URI
	// ServerAddress addrs = initialise();
	//
	// // Connect to MongoDB Server
	// this.mongoClient = new MongoClient(addrs);
	//
	// // Set Read Preference
	// this.mongoClient.setReadPreference(ReadPreference.secondary());
	//
	// // Connect to Database (Creates the DB if it does not exist)
	// this.db = this.mongoClient.getDB(this.db_name);
	//
	// // Create and Connect to Collection
	// this.db.createCollection(this.collection_user_name, null);
	// this.db.createCollection(this.collection_post_name, null);
	// this.collection_user = this.db.getCollection(this.collection_user_name);
	// this.collection_post = this.db.getCollection(this.collection_post_name);
	//
	// }
	//
	// private void deleteTestEnvironment() {
	//
	// // Delete Connection
	// this.db.getCollection(this.collection_user_name).drop();
	// this.db.getCollection(this.collection_post_name).drop();
	// this.mh.closeConnection();
	//
	// }
	//
	// // private void insertDocuments() {
	// // for (int i = 0; i < this.inserts; i++) {
	// // try {
	// // this.collection.insert(new BasicDBObject(String.valueOf(i), "test"));
	// // } catch (Exception e) {
	// // System.out.println("Error on inserting element: " + i);
	// // e.printStackTrace();
	// // }
	// // }
	// // }
	//
	// private void printCount() {
	// System.out.println("Count users " + this.collection_user.find().count());
	// System.out.println("Count posts " + this.collection_post.find().count());
	// }

	private static void printDbContents() {
		final GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("/home/diana/Dokumente/userPostsDataNucleusNeo4j"));
		
		
		// Wherever the Neo4J storage location is.
//        File storeDir = new File("/home/diana/Dokumente/test");
//
//        ServerBootstrapper serverBootstrapper = new CommunityBootstrapper();
//        serverBootstrapper.start(
//            storeDir,
//            Optional.empty(), // omit configfile, properties follow
//            Pair.of("dbms.connector.http.address","127.0.0.1:7474"),
//            Pair.of("dbms.connector.http.enabled", "true"),
//            Pair.of("dbms.connector.bolt.enabled", "true"),
//
//            // allow the shell connections via port 1337 (default)
//            Pair.of("dbms.shell.enabled", "true"),
//            Pair.of("dbms.shell.host", "127.0.0.1"),
//            Pair.of("dbms.shell.port", "1337")
//        );
        // ^^ serverBootstrapper.start() also registered shutdown hook!

//        NeoServer neoServer = serverBootstrapper.getServer();
//        GraphDatabaseService graphDb = neoServer.getDatabase().getGraph();
		
		
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

			//
			// NeoFromFile.writeGMLBasic(graphDb, "/home/diana/Dokumente/testGraph.gml");

			// graph.saveGraphML("/home/diana/Dokumente/testGraph.gml");
			tx.success();
		} finally {
			tx.terminate();
			graphDb.shutdown();
		}

//		final GraphDatabaseService graphDb2 = new GraphDatabaseFactory()
//				.newEmbeddedDatabase(new File("/home/diana/Dokumente/userPostsDataNucleusNeo4j"));

		// try {
		// Neo4jGraph graph = new Neo4jGraph(graphDb2);
		// GMLWriter writerGML = new GMLWriter(graph);
		// writerGML.outputGraph("/home/diana/Dokumente/testGraph.gml");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	// public void helperMethod() {
	//// String db = "/home/diana/Dokumente/userPostsDataNucleusNeo4j/schema";
	//// Graph graph = new Neo4jHaGraph(db);
	//// try {
	//// GraphMLWriter.outputGraph(graph,
	// "/home/diana/Dokumente/userPostsDataNucleusNeo4j.gml");
	//// } catch (IOException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	// //Import file
	// Container container;
	// ImportController importController =
	// Lookup.getDefault().lookup(ImportController.class);
	// try {
	// File file = new
	// File(getClass().getResource("/home/diana/Dokumente/userPostsDataNucleusNeo4j").toURI());
	// container = importController.importFile(file);
	// container.getLoader().setEdgeDefault(EdgeDirectionDefault.UNDIRECTED);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// System.out.println(ex);
	// return;
	// }
	// }

}
