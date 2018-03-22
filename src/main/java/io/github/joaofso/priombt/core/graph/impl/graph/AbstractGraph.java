package io.github.joaofso.priombt.core.graph.impl.graph;

import java.util.HashSet;
import java.util.Set;

import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.api.Graph;
import io.github.joaofso.priombt.core.graph.api.Vertex;

/**
 * This abstract class comprises the basic operations and attributes that a
 * graph might have, but an actual class must extend this one, implementing the
 * abstract methods. 
 * 
 * According Cormen et al. 2009, A graph G=(V, E) is a strutcture where:
 * - V is a set of vertexes (or nodes)
 * - E is a set of edges
 * G may be represented as a collection of adjacency lists, mainly for sparse graphs. 
 * @author joao felipe
 *
 */
public abstract class AbstractGraph implements Graph{
	
	protected Set<Vertex> vertexes;
	protected Set<Edge> edges;
	
	/**
	 * The constructor of an abstract graph, which is used by subclasses
	 */
	public AbstractGraph() {
		this.vertexes = new HashSet<Vertex>();
		this.edges = new HashSet<Edge>();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Vertex> getVertexes() {
		return this.vertexes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Edge> getEdges() {
		return this.edges;
	}
	
	/**
	 * Add a single edge to the edge set
	 * @param edgeToAdd The instance of the edge to add
	 */
	protected void addEdge(Edge edgeToAdd){
		if(!this.vertexes.contains(edgeToAdd.getFrom())){
			this.vertexes.add(edgeToAdd.getFrom());
		}
		if(!this.vertexes.contains(edgeToAdd.getTo())){
			this.vertexes.add(edgeToAdd.getTo());
		}
		this.edges.add(edgeToAdd);
	}
}
