package io.github.joaofso.priombt.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.github.joaofso.priombt.core.graph.EdgeType;
import io.github.joaofso.priombt.core.graph.api.Edge;
import io.github.joaofso.priombt.core.graph.api.Vertex;
import io.github.joaofso.priombt.core.graph.impl.edge.Transition;
import io.github.joaofso.priombt.core.graph.impl.graph.LTS;
import io.github.joaofso.priombt.core.graph.impl.vertex.State;
import io.github.joaofso.priombt.util.PrioSTException;

/**
 * This class is responsible for converting a TGF file into an LTS object, as
 * well as converting an LTS object to a TGF file.
 * 
 * @author felipe
 *
 */
public class TGFParser {
	private int lineCounter = 0;
	private Map<Integer, State> states = null;
	private final String NEW_LINE = System.getProperty("line.separator");
	
	/**
	 * Reads a TGF file and converts to a TGF object.
	 * 
	 * @param tgfFilePath
	 *            The path to the TGF file representing an LTS
	 * @return the LTS object
	 * @throws PrioSTException
	 *             Thrown when a file presents a malformation. Its message
	 *             pinpoint the problem in the file, in order to make the
	 *             correction easier.
	 */
	public LTS read(String tgfFilePath) throws PrioSTException{
		this.lineCounter = 0;
		this.states = new HashMap<Integer, State>();
		File file = new File(tgfFilePath);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new PrioSTException("The file " + file.getName() + " does not exist.");
		}
		
		if(!file.getName().contains(".tgf")){
			scanner.close();
			throw new PrioSTException("The file " + file.getName() + " is not a TGF file.");
		}
		
		LTS lts = new LTS();
		this.readStates(scanner, file.getName());
		this.readTransitions(scanner, lts);
		return lts;
	}
	
	private void readStates(Scanner scanner, String fileName) throws PrioSTException {
		boolean atLeastOneState = false;
		if(!scanner.hasNextLine()){
			throw new PrioSTException("The file " + fileName +" is empty.");
		}
		String line = scanner.nextLine();
		this.lineCounter++;
		while(!line.equals("#") && scanner.hasNextLine()){
			String[] tokens = line.trim().split(" ");
			
			if(tokens.length == 1 || line.trim().equals("")){
				if(scanner.hasNextLine()){
					line = scanner.nextLine();
					this.lineCounter++;
					continue;
				}else{
					if(!atLeastOneState){
						throw new PrioSTException("The file " + fileName +" is empty.");
					}
				}
			}
			
			if(tokens.length != 2){
				throw new PrioSTException("Line " + this.lineCounter + " has a malformed state or \"#\" that separate states from transitions was not found.");
			}
			
			try{
				int index = Integer.parseInt(tokens[0].trim());
				String label = tokens[1].trim();
				
				if(this.states.containsKey(index)){
					throw new PrioSTException("Line " + this.lineCounter + " has a duplicated state.");
				}
				this.states.put(index, new State(label));
				atLeastOneState = true;
			}catch(NumberFormatException exc){
				throw new PrioSTException("Line " + this.lineCounter + " contains a non-numeric value in its first field.");
			}
			line = scanner.nextLine();
			this.lineCounter++;
		}
		if(!scanner.hasNextLine() && !line.equals("#")){
			throw new PrioSTException("The file " + fileName +" does not contain any transition.");
		}
	}

	private void readTransitions(Scanner scanner, LTS lts) throws PrioSTException {
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			this.lineCounter++;
			
			String[] tokens = line.split(" ");
			if(tokens.length == 0 || line.trim().equals("")){
				if(scanner.hasNextLine()){
					line = scanner.nextLine();
					this.lineCounter++;
					continue;
				}else{
					break;
				}
			}
			
			if(tokens.length == 1){
				throw new PrioSTException("Line " + this.lineCounter + " has a malformed transition.");
			}
			if(tokens.length == 2){
				throw new PrioSTException("Line " + this.lineCounter + " has a transition with an empty label.");
			}
			
			String fromIndexString = tokens[0].trim();
			String toIndexString = tokens[1].trim();
			String label = this.assembleLabel(tokens);
			
			try{
				int fromIndex = Integer.parseInt(fromIndexString);
				int toIndex = Integer.parseInt(toIndexString);
				
				State from = this.states.get(fromIndex);
				if(from == null){
					throw new PrioSTException("Line " + this.lineCounter + " has a transition with a \"from\" state not declared.");
				}
				State to = this.states.get(toIndex);
				if(to == null){
					throw new PrioSTException("Line " + this.lineCounter + " has a transition with a \"to\" state not declared.");
				}
				EdgeType edgeType = EdgeType.DEFAULT;
				if(label.startsWith("S -") || label.startsWith("C -") || label.startsWith("R -")){
					int hyphenIndex = label.indexOf("-");
					String preffix = label.substring(0, hyphenIndex).trim();
					label = label.substring(hyphenIndex + 1).trim();
					edgeType = EdgeType.getEdgeType(preffix);
					
				}
				Transition transition = new Transition(from, label, to, edgeType);
				lts.addTransition(transition);
			}catch(NumberFormatException exc){
				throw new PrioSTException("Line " + this.lineCounter + " contains a non-numeric value for either \"from\" or \"to\" state.");
			}
		}
	}

	private String assembleLabel(String[] tokens) {
		String result = "";
		for (int i = 2; i < tokens.length; i++) {
			if(i != 2){
				result += " ";
			}
			result += tokens[i];
		}
		
		return result.trim();
	}
	
	/**
	 * Writes the structure of an LTS to a TGF file
	 * @param lts The LTS object to be written
	 * @param filePath The path where the result file will be stored
	 */
	public void write(LTS lts, String filePath){
		StringBuffer buffer = new StringBuffer();
		for (Vertex v : lts.getVertexes()) {
			String label = v.getLabel();
			buffer.append(label + " " + label + NEW_LINE);
		}
		buffer.append("#");
		
		for (Edge e : lts.getEdges()) {
			String fromLabel = e.getFrom().getLabel();
			String toLabel = e.getTo().getLabel();
			String label = e.getEdgeType().getShortText() + " - " + e.getLabel();
			buffer.append(NEW_LINE + fromLabel + " " + toLabel + " " + label);
		}
		
		try {
			int index = filePath.lastIndexOf("/") == -1 ? 0 : filePath.lastIndexOf("/") + 1;
			String directoryName = filePath.substring(0, index);
			String fileName = filePath.substring(index);
			File file = new File(directoryName);
			file.mkdirs();
			FileWriter writer = new FileWriter(directoryName + fileName);
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e1) {
			
		}
	}
}
