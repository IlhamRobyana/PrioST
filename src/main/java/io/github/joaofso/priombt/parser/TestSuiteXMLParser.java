/*
 * @(#)WriterTestSuiteXML.java
 *
 * Revision:
 * Author                                         Date           
 * --------------------------------------------   ------------   
 *  Jeremias D. Serafim de Araujo                     14/11/2012    
 */

package io.github.joaofso.priombt.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.output.XMLOutputter;

import io.github.joaofso.priombt.core.TestCase;
import io.github.joaofso.priombt.core.TestSuite;
import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;
import io.github.joaofso.priombt.util.PrioSTException;

/**
 * This class parses TestSuite objects in a XML representation and given a XML
 * representation, it converts for a TestSuite object. The structure of
 * this XML must conform with the LTSBTTestSuiteSchema.xsd definition.
 * 
 * @author Joao Felipe
 * 
 */

public class TestSuiteXMLParser {
	
	private final static String SCHEMA_NAME = "LTSBTTestSuiteSchema.xsd";
	private List<Edge> edgeList;
	
	public TestSuiteXMLParser(){
		this.edgeList = new LinkedList<Edge>();
	}

	//=====================================Writing the XML File===============================
	/**
	 * Writes a XML representation of the TestSuite object.
	 * 
	 * @param filePath is a name of output XML file.
	 * @param testSuite is a set of tests that will be written to the file.
	 */
	public void write(TestSuite testSuite, String filePath) {
		Document xmlRepresentation = this.convertTestSuiteToXML(testSuite);
		XMLOutputter outputter = new XMLOutputter();
		try {
			FileWriter writer = new FileWriter(filePath);
			outputter.output(xmlRepresentation, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Convert a test suite to a XML tag representation, according to the
	 * LTSBTTestSuiteSchema.xsd file.
	 * 
	 * @param suite The TestSuite object to be converted.
	 * @return The corresponding XML tag.
	 */
	private Document convertTestSuiteToXML(TestSuite suite) {
		Element testSuiteTag = new Element("TestSuite");
		for (TestCase testCase : suite) {
			Element testCaseTag = new Element("TestCase");
			this.convertTestCaseToXML(testCaseTag, testCase);
			testSuiteTag.addContent(testCaseTag);
		}
		Document xmlDocument = new Document(testSuiteTag);
		return xmlDocument;
	}

	/**
	 * Converts a single TestCase object into a corresponding XML tag, according
	 * to the defined schema.
	 * @param testSuiteTag The TestSuite XML parent tag.
	 * @param testCase The TestCase object that will be converted.
	 */
	private void convertTestCaseToXML(Element testSuiteTag, TestCase testCase) {
		for (Edge edge : testCase) {
			Element edgeTag = new Element("Edge");
			this.convertEdgetoXML(edgeTag, edge);
			testSuiteTag.addContent(edgeTag);
		}
	}

	/**
	 * Converts a single InterfaceEdge object into a corresponding XML tag, according
	 * to the defined schema.
	 * @param edgeTag The Edge XML tag.
	 * @param edge The InterfaceEdge object that will be converted.
	 */
	private void convertEdgetoXML(Element edgeTag, Edge edge) {
		edgeTag.setAttribute("from", edge.getFrom().getLabel());
		edgeTag.setAttribute("to", edge.getTo().getLabel());
		edgeTag.setAttribute("type", edge.getEdgeType().toString());
		Element labelTag = new Element("label");
		labelTag.setText(edge.getLabel());
		edgeTag.addContent(labelTag);
	}
	
	//=====================================Reading the XML File===============================
	
	/**
	 * Converts the XML file representation of a test suite into a TestSuite object.
	 * @param filePath The path of the XML file in the file system.
	 * @return An Equivalent TestSuite object. 
	 * @throws PrioSTException Thrown when a problem occurs with the input file.
	 */
	public TestSuite read(String filePath) throws PrioSTException{
		Document parsedXML = this.parseXML(filePath);
		TestSuite resultantTestSuite = new TestSuite();
		for (Element testCaseTag : parsedXML.getRootElement().getChildren("TestCase")) {
			resultantTestSuite.add(this.convertTestCase(testCaseTag));
		}
		return resultantTestSuite;
	}
	
	/**
	 * Converts a single TestCase XML tag into a TestCase object, intending to compose a test suite.
	 * @param testCaseTag The sub root TestCase tag to be analyzed.
	 * @return The TestCase instance related to the XML file.
	 */
	private TestCase convertTestCase(Element testCaseTag) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (Element edgeTag : testCaseTag.getChildren("Edge")) {
			Edge edge = this.getEdge(edgeTag.getAttributeValue("from"), edgeTag.getChildText("label"), edgeTag.getAttributeValue("to"), edgeTag.getAttributeValue("type"));
			if(edge == null){
				State from = new State(edgeTag.getAttributeValue("from"));
				State to = new State(edgeTag.getAttributeValue("to"));
				edge = new Transition(from, edgeTag.getChildText("label"), to, EdgeType.getEdgeType(edgeTag.getAttributeValue("type")));
				this.edgeList.add(edge);
			}
			edges.add(edge);
		}
		TestCase result = new TestCase();
		result.addAll(edges);
		return result;
	}

	/**
	 * A search method that verify and return the edge already created in another test case, intending to use the same instance of the edge.
	 * @param fromLabel The label of the vertex that the current edge comes from.
	 * @param label The label of the current edge.
	 * @param toLabel The label of the vertex that the current edge goes to.
	 * @param type The type of the edge.
	 * @return The InterfaceEdge object, if already was created, or null if it was not created yet.
	 */
	private Edge getEdge(String fromLabel, String label, String toLabel, String type) {
		for (Edge edge: this.edgeList) {
			if (edge.getFrom().getLabel().equals(fromLabel)
					&& edge.getLabel().equals(label)
					&& edge.getTo().getLabel().equals(toLabel)
					&& edge.getEdgeType().toString().equals(type)) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * Parses and validate the input XML file against the defined XML schema.
	 * @param filePath The path of the XML file to be validated and parsed.
	 * @return The object representation of the valid XML file to be converted into a TestSuite instance.
	 * @throws PrioSTException Thrown when a problem occurs with the input file.
	 */
	private Document parseXML(String filePath) throws PrioSTException{
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(SCHEMA_NAME);
			XMLReaderJDOMFactory schemafac = new XMLReaderXSDFactory(url);
			SAXBuilder builder = new SAXBuilder(schemafac);
			File xmlfile = new File(filePath);
			return builder.build(xmlfile);
		} catch (Exception e) {
			throw new PrioSTException(e.getLocalizedMessage());
		} 
	}
}
