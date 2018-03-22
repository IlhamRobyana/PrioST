package io.github.joaofso.priombt.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import io.github.joaofso.priombt.core.TestSuite;
import io.github.joaofso.priombt.core.graph.impl.graph.LTS;
import io.github.joaofso.priombt.generation.AllNLoopPath;
import io.github.joaofso.priombt.generation.TestCaseGenerator;
import io.github.joaofso.priombt.parser.TGFParser;
import io.github.joaofso.priombt.parser.TestSuiteXMLParser;

public class SystemController {
	
	private static final String PROPERTIES_FILE_MESSAGES = "messages.properties";
	
	private Properties appProperties;
	
	public SystemController(){
		try {
			this.appProperties = new Properties();
			this.appProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_MESSAGES));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String generateTestCases(String modelPath, int loops, String resultFile){
		try {
			LTS model;
			
			if(isTGF(modelPath)){
				model = parseTGFFile(modelPath);
			}else{
				return appProperties.getProperty("provide.supported.file.with.extension");
			}
			
			TestSuite generatedSuite = generateAllLoopPaths(model, loops);
			
			TestSuiteXMLParser parser = new TestSuiteXMLParser();
			parser.write(generatedSuite, resultFile);
			
			return appProperties.getProperty("success.generation") + " " + resultFile;
			
		} catch (PrioSTException e) {
			return e.getMessage();
		}
	}
	
	public void prioritizeTestSuite(String testSuitePath){
		
	}
	
	
	private LTS parseTGFFile(String modelPath) throws PrioSTException{
		TGFParser parser = new TGFParser();
		LTS lts = parser.read(modelPath);
		return lts;
	}

	private boolean isTGF(String modelPath) {
		return modelPath.toLowerCase().endsWith(".tgf");
	}
	
	private TestSuite generateAllLoopPaths(LTS lts, int loops) throws PrioSTException {
		TestCaseGenerator generator = new AllNLoopPath(loops);
		TestSuite suite = generator.generate(lts);
		return suite;
	}


	private String getFile(String fileName){
		String result = "";
		ClassLoader classLoader = getClass().getClassLoader();
		result = classLoader.getResource(fileName).getPath();
		return result;
	}
	
}
