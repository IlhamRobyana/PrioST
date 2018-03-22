package io.github.joaofso.priombt.core.graph.impl.edge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

public class TransitionTest {
	
	@Test
	public void testEqualObjects() {
		State v1 = new State("0");
		State v2 = new State("1");
		Edge e1 = new Transition(v1, "Edge", v2, EdgeType.DEFAULT);
		
		State v1m = new State("0");
		State v2m = new State("1");
		Edge e2 = new Transition(v1m, "Edge", v2m, EdgeType.DEFAULT);
		assertTrue(e1.equals(e2));
	}
	
	@Test
	public void testNotEqualObjects() {
		State v1 = new State("0");
		State v2 = new State("1");
		Edge e1 = new Transition(v1, "Edge 1", v2, EdgeType.DEFAULT);
		
		State v1m = new State("0");
		State v2m = new State("1");
		Edge e2 = new Transition(v1m, "Edge 2", v2m, EdgeType.DEFAULT);
		assertFalse(e1.equals(e2));
		
		State v1n = new State("0");
		State v2n = new State("2");
		Edge e3 = new Transition(v1n, "Edge", v2n, EdgeType.DEFAULT);
		assertFalse(e1.equals(e3));
	}
	
	@Test
	public void testToString() {
		
	}
}
