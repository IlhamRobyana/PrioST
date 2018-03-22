package io.github.joaofso.priombt.generation;

import java.util.ArrayList;
import java.util.List;

import io.github.joaofso.priombt.core.TestCase;
import io.github.joaofso.priombt.core.TestSuite;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.api.Vertex;
import io.github.joaofso.priombt.core.graph.impl.graph.LTS;
import io.github.joaofso.priombt.util.PrioSTException;

/**
 * The constructor of a test case generator that satisfy the AllNLoopPath, where
 * N is the amount of times a loop must be traversed. This implementation is
 * based on the Depth-First Search algorithm.
 * 
 * @author felipe
 *
 */
public class AllNLoopPath implements TestCaseGenerator {
	
	int amountLoops = 0;
	
	/**
	 * This is the constructor of a test case generator using N = 1 as a default
	 * behavior, which coincides with the All-one-loop-paths criterion described
	 * by Utting and Legeard 2007.
	 */
	public AllNLoopPath(){
		this.amountLoops = 1;
	}
	
	/**
	 * This constructor adds the possibility of varying the amount of times that
	 * the loops need to be traversed. N is a natural number, where N &ge; 0.
	 * The idea is try to generalize the All-one-loop-path criterion.
	 * 
	 * @param amountLoops
	 *            The positive integer value indicating the maximum amount of
	 *            times a loop must be traversed.
	 * @throws PrioSTException
	 *             Thrown when a negative integer or zero is provided.
	 */
	public AllNLoopPath(int amountLoops) throws PrioSTException {
		if(amountLoops > 0){
			this.amountLoops = amountLoops;
		}else{
			throw new PrioSTException("The provided value must be an integer greater than zero");
		}
	}

	/**
	 * Generate the test cases from the provided model, without id and name.
	 * 
	 * @param model The LTS representing the system behavior. 
	 */
	public TestSuite generate(LTS model) {
		return this.generate(model, "", "");
	}
	
	/**
	 * Generate the test cases from the provided model, with id and name for
	 * identification purposes.
	 * 
	 * @param model
	 *            The LTS representing the system behavior.
	 * @param testSuiteId
	 *            The test case id.
	 * @param testSuiteName
	 *            The test case name.
	 */
	public TestSuite generate(LTS model, String testSuiteId, String testSuiteName) {
		List<Edge> currentPath = new ArrayList<Edge>();
		TestSuite testSuite = new TestSuite(testSuiteId, testSuiteName);
		traverse(model.getInitialState(), currentPath, testSuite);
		return testSuite;
	}

	/**
	 * Recursive method that explores taking as starting point the provided
	 * vertex. Its stop condition is whether the execution reaches a vertex with
	 * no child or whether it reaches a vertex already visited an amount of
	 * times related to N
	 * 
	 * @param state The vertex to begin the exploration.
	 * @param currentPath A list with the edges currently traversed to control the recursion.
	 * @param testSuite The output parameter to store the test cases generated during the model's exploration.
	 */
	private void traverse(Vertex state, List<Edge> currentPath, TestSuite testSuite){
		if(state.getOutgoingEdges().size() == 0){ //the algorithm traverses a path that ends in a vertex with no child
			testSuite.add(this.getTestCase(currentPath));
		}else{
			for (Edge edge : state.getOutgoingEdges()) {
				Vertex vertexTo = edge.getTo();
				if(this.occurrences(currentPath, vertexTo) <= this.amountLoops){
					currentPath.add(edge);
					traverse(vertexTo, currentPath, testSuite);
					currentPath.remove(currentPath.size() - 1); //remove the last element after the recursive call
				}else{
					testSuite.add(this.getTestCase(currentPath)); //the algorithm traverses a path that ends when the loop is traversed N times
				}
			}
		}
	}

	/**
	 * This method assembles a test case using a list of edges.
	 * @param currentPath The list of edges to be organized as a test case.
	 * @return The correspondent test case object.
	 */
	private TestCase getTestCase(List<Edge> currentPath) {
		TestCase tc = new TestCase();
		tc.addAll(currentPath);
		return tc;
	}

	/**
	 * This method counts the occurrences of a given vertex in a sequence of edges.
	 * @param currentPath The list of edges to be traversed.
	 * @param vertex The vertex to be analyzed.
	 * @return An integer value indicating the amount of times the given vertex appear in the provided list of edges.
	 */
	private int occurrences(List<Edge> currentPath, Vertex vertex) {
		int count = 0;
		if(currentPath.size() > 0){
			if(currentPath.get(0).getFrom().getLabel().equals(vertex.getLabel())){
				count++;
			}
			for (int i = 0; i < currentPath.size(); i++) {
				if(currentPath.get(i).getTo().getLabel().equals(vertex.getLabel())){
					count++;
				}
			}
		}
		return count;
	}

}
