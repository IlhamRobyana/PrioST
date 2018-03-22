package io.github.joaofso.priombt.core.graph.api;

import java.util.Set;

/**
 * The graph behavior defines a intermediate representation for the operation of
 * the test case generation algorithm
 * 
 * @author joao felipe
 *
 */
public interface Graph {
	
	/**
	 * Get the graph's set of vertexes
	 * @return A set with the graph's vertexes
	 */
	public Set<Vertex> getVertexes();
	
	/**
	 * Get the graph's set of edges
	 * @return A set with the graph's edges
	 */
	public Set<Edge> getEdges();
}
