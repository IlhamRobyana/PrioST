package io.github.joaofso.priombt.core;

import java.util.HashSet;

/**
 * This class represents an abstraction of a black bok, system level test suite,
 * frequently used as a result of a test case generation algorithm operation. A
 * test suite is a set of test cases and in this implementation, this class
 * behaves as a HashSet, because we do not need to guarantee the elements' order
 * and, in this situation, HashSets are faster than TreeSets.
 * 
 * @author felipe
 *
 */
public class TestSuite extends HashSet<TestCase>{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	/**
	 * The simple constructor of a test case.
	 */
	public TestSuite(){
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
	public TestSuite(String id, String name){
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
