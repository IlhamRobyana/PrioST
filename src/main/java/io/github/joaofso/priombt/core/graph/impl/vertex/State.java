package io.github.joaofso.priombt.core.graph.impl.vertex;

public class State extends AbstractVertex {

	/**
	 * Constructor of a State
	 * @param label The vertex's label
	 */
	public State(String label) {
		super(label);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object otherObject) {
		if(otherObject instanceof State){
			State otherVertex = (State) otherObject;
			return this.getLabel().equals(otherVertex.getLabel());
		}
		return false;
	}

}
