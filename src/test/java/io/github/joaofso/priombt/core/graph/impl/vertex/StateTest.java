package io.github.joaofso.priombt.core.graph.impl.vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;

public class StateTest {

	@Test
	public void testEqualsObject() {
		State v1 = new State("1");
		State v2 = new State("1");
		State v3 = new State("2");
		assertEquals(v1, v2);
		assertNotEquals(v1, v3);
	}

	@Test
	public void testGetLabel() {
		State v = new State("0");
		assertEquals(v.getLabel(), "0");
		assertNotSame(v.getLabel(), "Different");
	}

	@Test
	public void testSetLabel() {
		State v = new State("0");
		assertEquals(v.getLabel(), "0");
		v.setLabel("1");
		assertEquals(v.getLabel(), "1");
		assertNotEquals(v.getLabel(), "0");
	}

	@Test
	public void testIncomingOutgoingEdge() {
		State v0 = new State("0");
		State v1 = new State("1");
		State v2 = new State("2");
		
		Transition e1 = new Transition(v0, "Edge text 1", v1, EdgeType.STEP);
		assertEquals(v0.getIncomingEdges().size(), 0);
		assertEquals(v0.getOutgoingEdges().size(), 1);
		assertEquals(v1.getIncomingEdges().size(), 1);
		assertEquals(v1.getOutgoingEdges().size(), 0);
		
		Transition e2 = new Transition(v0, "Edge text 2", v2, EdgeType.STEP);
		assertEquals(v0.getIncomingEdges().size(), 0);
		assertEquals(v0.getOutgoingEdges().size(), 2);
		assertEquals(v2.getIncomingEdges().size(), 1);
		assertEquals(v2.getOutgoingEdges().size(), 0);
	}
}
