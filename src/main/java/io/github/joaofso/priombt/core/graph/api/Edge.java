package io.github.joaofso.priombt.core.graph.api;

import io.github.joaofso.priombt.core.graph.EdgeType;

/**
 * The edge behavior defines the main element of the control flow represented in
 * a model. Frequently carries the useful information about the steps, expected
 * results and conditions when modeling a system
 * 
 * @author joao felipe
 *
 */
public interface Edge {
	
	/**
	 * Return the vertex that is the origin of this edge
	 * @return The source vertex
	 */
	public Vertex getFrom();
	
	/**
	 * Define the origin of this edge
	 * @param sourceVertex The source vertex of this edge 
	 */
	public void setFrom(Vertex sourceVertex);
	
	/**
	 * Return the vertex that is the destination of this edge
	 * @return The destination vertex
	 */
	public Vertex getTo();
	
	/**
	 * Define the destination of this edge
	 * @param destinationVertex The destination vertex of this edge
	 */
	public void setTo(Vertex destinationVertex);
	
	/**
	 * Return the label of this edge
	 * @return The text of this edge's label
	 */
	public String getLabel();
	
	/**
	 * Define a text for this edge's label
	 * @param newLabel The text for this edge's label
	 */
	public void setLabel(String newLabel);
	
	/**
	 * Return the type of the edge
	 * @return The type of the edge
	 */
	public EdgeType getEdgeType();
	
	/**
	 * Define the type of this edge
	 * @param edgeType The type of the edge
	 */
	public void setEdgeType(EdgeType edgeType);
}
