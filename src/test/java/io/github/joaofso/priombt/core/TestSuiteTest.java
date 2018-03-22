package io.github.joaofso.priombt.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

public class TestSuiteTest {

	@Test
	public void testSimple() {
		TestSuite ts = new TestSuite();
		TestCase tc = getTestCase1();
		ts.add(tc);
		assertTrue(ts.size() == 1);
		assertEquals("", ts.getId());
		assertEquals("", ts.getName());
	}
	
	@Test
	public void testNamed() {
		TestSuite ts = new TestSuite("TS1", "First test suite");
		TestCase tc = getTestCase1();
		ts.add(tc);
		assertTrue(ts.size() == 1);
		assertEquals("TS1", ts.getId());
		assertEquals("First test suite", ts.getName());
	}
	
	@Test
	public void testEquality() {
		TestSuite ts = new TestSuite();
		TestCase tc = getTestCase1();
		ts.add(tc);
		assertTrue(ts.size() == 1);
		ts.add(tc); //Since a test suite is a set, the second instance should not be added 
		assertTrue(ts.size() == 1);
		
		TestCase tc2 = getTestCase2();
		ts.add(tc2); //This test case, although its has the same labels for transitions, it has different labels for transitions and it should be added
		assertTrue(ts.size() == 2);
	}

	private TestCase getTestCase1() {
		State v1 = new State("0");
		State v2 = new State("1");
		Edge e1 = new Transition(v1, "Precondition", v2, EdgeType.CONDITION);
		
		State v1m = new State("1");
		State v2m = new State("2");
		Edge e2 = new Transition(v1m, "User step", v2m, EdgeType.STEP);
		
		State v1n = new State("2");
		State v2n = new State("3");
		Edge e3 = new Transition(v1n, "Expected result", v2n, EdgeType.RESULT);
		
		TestCase tc = new TestCase();
		tc.add(e1);
		tc.add(e2);
		tc.add(e3);
		return tc;
	}
	
	private TestCase getTestCase2() {
		State v1 = new State("0");
		State v2 = new State("4");
		Edge e1 = new Transition(v1, "Precondition", v2, EdgeType.CONDITION);
		
		State v1m = new State("4");
		State v2m = new State("5");
		Edge e2 = new Transition(v1m, "User step", v2m, EdgeType.STEP);
		
		State v1n = new State("5");
		State v2n = new State("6");
		Edge e3 = new Transition(v1n, "Expected result", v2n, EdgeType.RESULT);
		
		TestCase tc = new TestCase();
		tc.add(e1);
		tc.add(e2);
		tc.add(e3);
		return tc;
	}

}
