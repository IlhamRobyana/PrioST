package io.github.joaofso.priombt.generation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.joaofso.priombt.core.TestSuite;
import io.github.joaofso.priombt.core.graph.impl.graph.LTS;
import io.github.joaofso.priombt.parser.TGFParser;
import io.github.joaofso.priombt.util.PrioSTException;

public class AllNLoopPathTest {

	@Test
	public void test() {
		try {
			String fileName = getFile("tgf/SimpleLoopModel.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			
			TestCaseGenerator generator = new AllNLoopPath();
			TestSuite testSuite = generator.generate(lts);
			assertEquals(5, testSuite.size());
		} catch (PrioSTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2Loop() {
		try {
			String fileName = getFile("tgf/SimpleLoopModel.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			
			TestCaseGenerator generator = new AllNLoopPath(2);
			TestSuite testSuite = generator.generate(lts);
			assertEquals(7, testSuite.size());
		} catch (PrioSTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testZeroLoop() {
		try {
			String fileName = getFile("tgf/SimpleLoopModel.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			
			TestCaseGenerator generator = new AllNLoopPath(0);
			TestSuite testSuite = generator.generate(lts);
		} catch (PrioSTException e) {
			assertEquals("The provided value must be an integer greater than zero", e.getMessage());
		}
	}
	
	@Test
	public void testNegativeLoop() {
		try {
			String fileName = getFile("tgf/SimpleLoopModel.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			
			TestCaseGenerator generator = new AllNLoopPath(-2);
			TestSuite testSuite = generator.generate(lts);
		} catch (PrioSTException e) {
			assertEquals("The provided value must be an integer greater than zero", e.getMessage());
		}
	}


	private String getFile(String fileName){
		String result = "";
		ClassLoader classLoader = getClass().getClassLoader();
		result = classLoader.getResource(fileName).getPath();
		return result;

	}
	
}

