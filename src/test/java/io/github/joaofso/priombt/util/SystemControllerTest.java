package io.github.joaofso.priombt.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class SystemControllerTest {
	
//	=====================generation===========================
	
	@Test
	public void smallTestSuiteNoLoops(){
		String smallTestSuiteFilePath = getFile("tgf/ConditionStepResult.tgf");
		String testSuiteResult = "result.xml"; 
		
		SystemController controller = new SystemController();
		String result = controller.generateTestCases(smallTestSuiteFilePath, 2, testSuiteResult);
		
		assertEquals("Test suite was generated successfully. The result is in file result.xml", result);
		File file = new File(testSuiteResult);
		file.delete();
	}
	
	@Test
	public void testSuiteWithLoop(){
		String smallTestSuiteFilePath = getFile("tgf/SimpleLoopModel.tgf");
		String testSuiteResult = "result.xml"; 
		
		SystemController controller = new SystemController();
		String result = controller.generateTestCases(smallTestSuiteFilePath, 2, testSuiteResult);
		
		assertEquals("Test suite was generated successfully. The result is in file result.xml", result);
		File file = new File(testSuiteResult);
		file.delete();
	}
	
	
	private String getFile(String fileName){
		String result = "";
		ClassLoader classLoader = getClass().getClassLoader();
		result = classLoader.getResource(fileName).getPath();
		return result;

	}
}