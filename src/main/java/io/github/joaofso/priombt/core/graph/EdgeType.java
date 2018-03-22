package io.github.joaofso.priombt.core.graph;

/**
 * This enumeration defines the types of edges
 * 
 * @author joao felipe
 *
 */
public enum EdgeType {
	/**
	 * The type to not using any type
	 */
	DEFAULT ("default", "D"),
	
	/**
	 * The type that defines an user action
	 */
	STEP ("step", "S"),
	
	/**
	 * The type that defines a system's response
	 */
	RESULT ("result", "R"),
	
	/**
	 * The type that defines a condition
	 */
	CONDITION ("condition", "C");
	
	private String type;
	private String shortText;
	
	/**
	 * The constructor of a type using a textual representation
	 * @param typeText The textual representation of the edge type
	 */
	EdgeType(String typeText, String shortText){
		this.type = typeText;
		this.shortText = shortText;
	}
	
	/**
	 * Return the textual representation of this edge type
	 * @return The textual representation of the type
	 */
	public String getTypeText(){
		return this.type;
	}
	
	/**
	 * Return the short representation of this edge type
	 * @return The short textual representation of the edge type
	 */
	public String getShortText(){
		return this.shortText;
	}
	
	/**
	 * Return a new instance of an edge type given the textual representation of
	 * the edge type
	 * 
	 * @param typeText
	 *            The textual representation of the type
	 * @return The enumeration element corresponding to the textual
	 *         representation
	 */
	public static EdgeType getEdgeType(String typeText){
		for (EdgeType edgeType : values()) {
			if(edgeType.type.equalsIgnoreCase(typeText) || edgeType.shortText.equalsIgnoreCase(typeText)){
				return edgeType;
			}
		}
		return null;
	}
}
