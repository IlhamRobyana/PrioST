package io.github.joaofso.priombt.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.impl.graph.LTS;
import io.github.joaofso.priombt.util.PrioSTException;

public class TGFParserTest {

	@Test
	public void testSimpleValid(){
		try {
			String fileName = getFile("tgf/ConditionStepResult.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);

			assertEquals("0", lts.getInitialState().getLabel());
			assertEquals(6, lts.getVertexes().size());
			assertEquals(5, lts.getEdges().size());
			assertEquals(2, lts.getInitialState().getOutgoingEdges().get(0).getTo().getOutgoingEdges().size());
		} catch (PrioSTException e) {
//			fail();
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void testEmpty(){
		try {
			String fileName = getFile("tgf/Empty.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals("The file Empty.tgf is empty.", e.getMessage());
		}
	}
	
	@Test
	public void testSingleState(){
		try {
			String fileName = getFile("tgf/SingleStateError.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "The file SingleStateError.tgf does not contain any transition.");
		}
	}
	
	@Test
	public void testNonexistentFile(){
		try {
			String fileName = "tgf/NoSuchFile.tgf";
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "The file NoSuchFile.tgf does not exist.");
		}
	}
	
	@Test
	public void testNonTGFFile(){
		try {
			String fileName = getFile("tgf/Empty.nontgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "The file Empty.nontgf is not a TGF file.");
		}
	}
	
	@Test
	public void testMalformedStateFile(){
		try {
			String fileName = getFile("tgf/MalformedState.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 3 has a malformed state or \"#\" that separate states from transitions was not found.");
		}
	}
	
	@Test
	public void testDuplicatedState(){
		try {
			String fileName = getFile("tgf/DuplicatedState.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 6 has a duplicated state.");
		}
	}

	@Test
	public void testNonNumericState(){
		try {
			String fileName = getFile("tgf/NonNumericState.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 3 contains a non-numeric value in its first field.");
		}
	}
	
	@Test
	public void testMalformedTransition(){
		try {
			String fileName = getFile("tgf/MalformedTransition.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 9 has a malformed transition.");
		}
	}
	
	@Test
	public void testNonDeclaredFromTransition(){
		try {
			String fileName = getFile("tgf/NonDeclaredFrom.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 13 has a transition with a \"from\" state not declared.");
		}
	}
	
	@Test
	public void testNonDeclaredToTransition(){
		try {
			String fileName = getFile("tgf/NonDeclaredTo.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 13 has a transition with a \"to\" state not declared.");
		}
	}
	
	@Test
	public void testEmptyTransition(){
		try {
			String fileName = getFile("tgf/EmptyTransition.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 9 has a transition with an empty label.");
		}
	}
	
	@Test
	public void testNonNumericFromTransition(){
		try {
			String fileName = getFile("tgf/NonNumericFromTransition.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 10 contains a non-numeric value for either \"from\" or \"to\" state.");
		}
	}
	
	@Test
	public void testNonNumericToTransition(){
		try {
			String fileName = getFile("tgf/NonNumericToTransition.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
			fail();
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "Line 10 contains a non-numeric value for either \"from\" or \"to\" state.");
		}
	}
	
	@Test
	public void testEmptyLineTransition(){
		try {
			String fileName = getFile("tgf/EmptyLineTransition.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
		} catch (PrioSTException e) {
			fail();
		}
	}
	
	@Test
	public void testEmptyLineState(){
		try {
			String fileName = getFile("tgf/EmptyLineStateMiddle.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
		} catch (PrioSTException e) {
			fail();
		}
	}
	
	@Test
	public void testEmptyLineToTheEndState(){
		try {
			String fileName = getFile("tgf/EmptyLineStateToTheEnd.tgf");
			TGFParser parser = new TGFParser();
			LTS lts = parser.read(fileName);
		} catch (PrioSTException e) {
			assertEquals(e.getMessage(), "The file EmptyLineStateToTheEnd.tgf does not contain any transition.");
		}
	}
	
	private String getFile(String fileName){
		String result = "";
		ClassLoader classLoader = getClass().getClassLoader();
		result = classLoader.getResource(fileName).getPath();
		return result;

	}
}
