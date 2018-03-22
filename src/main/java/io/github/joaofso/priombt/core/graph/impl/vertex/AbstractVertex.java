package io.github.joaofso.priombt.core.graph.impl.vertex;

import java.util.LinkedList;
import java.util.List;

import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.api.Vertex;

/**
 * This abstract class comprises the basic operations and attributes that a
 * vertex might have, but an actual class must extend this one, implementing the
 * abstract methods
 * 
 * @author joao felipe
 *
 */
public abstract class AbstractVertex implements Vertex {
	
	//Attributes of a vertex
	private String label;
	private List<Edge> incomingEdges;
	private List<Edge> outgoingEdges;
	
	
	/**
	 * The constructor of an abstract vertex, which is used by a subclass
	 * @param label The vertex's label
	 */
	public AbstractVertex(String label){
		this.label = label;
		this.incomingEdges = new LinkedList<Edge>();
		this.outgoingEdges = new LinkedList<Edge>();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Edge> getIncomingEdges() {
		return this.incomingEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Edge> getOutgoingEdges() {
		return this.outgoingEdges;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addIncomingEdge(Edge incomingEdge) {
		this.incomingEdges.add(incomingEdge);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addOutgoingEdge(Edge outgoingEdge) {
		this.outgoingEdges.add(outgoingEdge);
	}
	
	/**
	 * Compare the vertex to other object
	 * @param otherObject The other object to compare
	 * @return A boolean value indicating whether the vertex is equals
	 */
	public abstract boolean equals(Object otherObject);
}