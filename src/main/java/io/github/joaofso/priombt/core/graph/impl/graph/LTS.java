package io.github.joaofso.priombt.core.graph.impl.graph;

import io.github.joaofso.priombt.core.graph.api.Vertex;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;

/**
 * An LTS is a formalism used to represent the behavior of systems. According to
 * de Vries and Treatmans 2000, an LTS &lt;S, L, T, s0&gt; is a 4-tuple where: - S is
 * a set of states - L is a set of labels - T is a set of triples SxLxS, which
 * is a transition function - s0 is the initial state Here we represent an LTS
 * as a kind of Graph, therefore this class extends {@link AbstractGraph}
 * 
 * When assembling a new LTS, the first added edge defines its initial state as
 * the state that the edge comes from
 * 
 * @author joao felipe
 *
 */
public class LTS extends AbstractGraph {
	private State initialState;
	
	/**
	 * This is the constructor of an LTS
	 */
	public LTS(){
		super();
		this.initialState = null;
	}
	
	/**
	 * Adds a transition in the LTS. Used when parsing the input files to an LTS
	 * @param transition the edge representing the LTS transition
	 */
	public void addTransition(Transition transition){
		if(this.getEdges().size() == 0){
			this.initialState = (State) transition.getFrom();
		}
		this.addEdge(transition);
	}
	
	public Vertex getInitialState(){
		return this.initialState;
	}
	

}
