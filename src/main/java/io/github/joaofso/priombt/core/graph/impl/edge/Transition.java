package io.github.joaofso.priombt.core.graph.impl.edge;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

public class Transition extends AbstractEdge {

	/**
	 * Constructor of an edge
	 * @param from The vertex that the new edge comes from
	 * @param label The edge's label
	 * @param to The vertex that the new edge goes to
	 * @param edgeType The edge's type: a step, condition, result, or a default type
	 */
	public Transition(State from, String label, State to, EdgeType edgeType) {
		super(from, label, to, edgeType);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object otherObject) {
		if(otherObject instanceof Transition){
			Transition otherEdge = (Transition) otherObject;
			return this.toString().equals(otherObject.toString());
		}
		return false;
	}

}
