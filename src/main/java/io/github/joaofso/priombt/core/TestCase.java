package io.github.joaofso.priombt.core;

import java.util.LinkedList;

import io.github.joaofso.priombt.core.graph.api.Edge;

/**
 * This class represents an abstraction of a black box, system level test case,
 * frequently used as the result of a test case generation algorithm. A test
 * case is a sequence of steps and expected results, subject to different
 * conditions. In this implementation, a test case behaves as a linked list,
 * because the order of the elements matter.
 * 
 * @author felipe
 *
 */
public class TestCase extends LinkedList<Edge>{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	/**
	 * The simple constructor of a test case.
	 */
	public TestCase(){
		super();
		this.id = "";
		this.name = "";
	}
	
	/**
	 * The constructor of a test case. This constructor gives the property of
	 * identification for a test case, allowing to set an in and a name.
	 * 
	 * @param id An identifier of the test case.
	 * @param name A name for the test case.
	 */
	public TestCase(String id, String name){
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	

}
