package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.helper;

import com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.app.FrameworkTest;

public class Main {
	public static void main(String[] args) {
		// Neo4j
		FrameworkTest frameworkTest = new FrameworkTest(500, 3);
		try {
			System.out.println("WRITE TEST");
			frameworkTest.performWriteTest().printMeasureResultWrite();
			System.out.println("READ TEST");
			frameworkTest.performReadTest().printMeasureResultRead();
//			frameworkTest.printDbContents();
		} catch (Exception e) {
			System.out.println("Test Failed");
			e.printStackTrace();
		}
	}
}
