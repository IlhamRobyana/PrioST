package io.github.joaofso.priombt.core.graph.impl.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

public class LTSTest {

	@Test
	public void test() {
		State a = new State("0");
		State b = new State("1");
		State c = new State("2");
		State d = new State("3");

		Transition tr1 = new Transition(a, "Precondition", b, EdgeType.CONDITION);
		Transition tr2 = new Transition(b, "Step", c, EdgeType.STEP);
		Transition tr3 = new Transition(b, "Result", d, EdgeType.RESULT);
		
		LTS lts = new LTS();
		lts.addTransition(tr1);
		lts.addTransition(tr2);
		lts.addTransition(tr3);
		
		assertEquals(lts.getEdges().size(), 3);
		assertEquals(lts.getVertexes().size(), 4);
		assertEquals(lts.getInitialState().getLabel(), "0");
		assertEquals(lts.getInitialState(), a);
		assertEquals(lts.getInitialState().getOutgoingEdges().get(0).getTo().getOutgoingEdges().size(), 2);
	}

}
