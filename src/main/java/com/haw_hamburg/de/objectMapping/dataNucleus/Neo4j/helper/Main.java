package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.helper;

import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app.FrameworkTest;

public class Main 
{
    public static void main( String[] args )
    {
//		 MongoDB
		FrameworkTest mongodb = new FrameworkTest(1, 1);
		try {
			mongodb.performWriteTest().printMeasureResult();
//			PartitionGraph graph = new PartitionGraph();
//			graph.script();
		} catch (Exception e) {
			System.out.println("Test Failed");
			e.printStackTrace();
		}
		
    }
}
