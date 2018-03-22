package io.github.joaofso.priombt.generation;

import io.github.joaofso.priombt.core.TestSuite;
import io.github.joaofso.priombt.core.graph.impl.graph.LTS;

/**
 * This interface describes the behavior of a test case generator, which is the
 * action of generate test cases from an graph, either with or without
 * identification.
 * 
 * @author felipe
 *
 */
public interface TestCaseGenerator {

	public TestSuite generate(LTS model);
	
	public TestSuite generate(LTS model, String testSuiteId, String testSuiteName);
}
