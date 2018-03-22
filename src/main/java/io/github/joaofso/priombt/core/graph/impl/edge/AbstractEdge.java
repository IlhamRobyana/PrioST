package io.github.joaofso.priombt.core.graph.impl.edge;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.api.Vertex;

/**
 * This abstract class comprises the basic operations and attributes that an
 * edge might have, but an actual class must extend this one, implementing the
 * abstract methods
 * 
 * @author joao felipe
 *
 */
public abstract class AbstractEdge implements Edge{
	
	//attributes of an edge
	private Vertex from;
	private String label;
	private Vertex to;
	private EdgeType type; 
	
	/**
	 * The constructor of an abstract edge, which is used by a subclass
	 * @param from The vertex that the new edge comes from
	 * @param label The edge's label
	 * @param to The vertex that the new edge goes to
	 * @param edgeType The edge's type: a step, condition, result, or a default type
	 */
	public AbstractEdge(Vertex from, String label, Vertex to, EdgeType edgeType) {
		this.from = from;
		this.label = label;
		this.to = to;
		this.type = edgeType;
		this.from.addOutgoingEdge(this);
		this.to.addIncomingEdge(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Vertex getFrom() {
		return from;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setFrom(Vertex from) {
		this.from = from;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Vertex getTo() {
		return to;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setTo(Vertex to) {
		this.to = to;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public EdgeType getEdgeType(){
		return this.type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setEdgeType(EdgeType edgeType){
		this.type = edgeType;
	}
	
	/**
	 * Return a string representation of the edge. The main use of this method is to assemble the toString of a test case
	 * 
	 * @return The string representation of an edge expressing the from and to vertexes, the current label and the edge type
	 */
	public String toString(){
		return "[" + this.from.getLabel() + "|(" + this.type.getTypeText() + ") " + this.getLabel() + "|" + this.to.getLabel() + "]";
	}
	
	/**
	 * Compare the edge to other instance
	 * 
	 * @param otherObject The other object to compare
	 * @return A boolean value indicating whether the edge is equals
	 */
	public abstract boolean equals(Object otherObject);
	
}
