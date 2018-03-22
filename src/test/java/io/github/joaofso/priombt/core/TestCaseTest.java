package io.github.joaofso.priombt.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

public class TestCaseTest {

	@Test
	public void testSimpleTC() {
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
		
		assertEquals("", tc.getId());
		assertEquals("", tc.getName());
		
		assertEquals(3, tc.size());
		assertEquals(EdgeType.CONDITION, tc.get(0).getEdgeType());
		assertEquals("Precondition", tc.get(0).getLabel());
		assertEquals(EdgeType.STEP, tc.get(1).getEdgeType());
		assertEquals("User step", tc.get(1).getLabel());
		assertEquals(EdgeType.RESULT, tc.get(2).getEdgeType());
		assertEquals("Expected result", tc.get(2).getLabel());
	}
	
	@Test
	public void testIdentifiedTC() {
		State v1 = new State("0");
		State v2 = new State("1");
		Edge e1 = new Transition(v1, "Precondition", v2, EdgeType.CONDITION);
		
		State v1m = new State("1");
		State v2m = new State("2");
		Edge e2 = new Transition(v1m, "User step", v2m, EdgeType.STEP);
		
		State v1n = new State("2");
		State v2n = new State("3");
		Edge e3 = new Transition(v1n, "Expected result", v2n, EdgeType.RESULT);
		
		TestCase tc = new TestCase("TC1", "Test a certain use case flow");
		tc.add(e1);
		tc.add(e2);
		tc.add(e3);
		
		assertEquals("TC1", tc.getId());
		assertEquals("Test a certain use case flow", tc.getName());
		
		assertEquals(3, tc.size());
		assertEquals(EdgeType.CONDITION, tc.get(0).getEdgeType());
		assertEquals("Precondition", tc.get(0).getLabel());
		assertEquals(EdgeType.STEP, tc.get(1).getEdgeType());
		assertEquals("User step", tc.get(1).getLabel());
		assertEquals(EdgeType.RESULT, tc.get(2).getEdgeType());
		assertEquals("Expected result", tc.get(2).getLabel());
	}

}
