/**
 * 
 */
package io.github.joaofso.priombt.core.graph.api;

import java.util.List;

/**
 * The vertex behavior defines the link between graph edges
 *
 * @author joao felipe
 *
 */
public interface Vertex {
	
	/**
	 * Return the vertex's label
	 * @return Its current label
	 */
	public String getLabel();
	
	/**
	 * Set the vertex's label
	 * @param newLabel The new label to be assign
	 */
	public void setLabel(String newLabel);
	
	/**
	 * Get a list with the edges that come to this vertex
	 * @return A list with the incoming edges
	 */
	public List<Edge> getIncomingEdges();
	
	/**
	 * Get a list with the edges that go out of this vertex
	 * @return A list with the outgoing edges
	 */
	public List<Edge> getOutgoingEdges();
	
	/**
	 * Add an edge to the list of the incoming edges
	 * @param incomingEdge The new incoming edge
	 */
	public void addIncomingEdge(Edge incomingEdge);
	
	/**
	 * Add as edge to the list of outgoing edges
	 * @param outgoingEdge The new outgoing edge
	 */
	public void addOutgoingEdge(Edge outgoingEdge);

}
