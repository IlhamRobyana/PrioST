package io.github.joaofso.priombt.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineInterface {
	
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final Options getOptions(){
		Options commandOptions = new Options();
		
		commandOptions.addOption(generateOption());
		commandOptions.addOption(inputFileOption());
		commandOptions.addOption(outputFileOption());
		commandOptions.addOption(helpOption());
		
		return commandOptions;
	}

	private static final Option generateOption() {
		Option genOption = new Option("g", "generate", true, "generate test cases from the use case definition in <inputFile> saving them in <outputFile>.");
		genOption.setArgName("travLoops");
		return genOption;
	}
	
	private static final Option inputFileOption() {
		Option inOption = new Option("i", "input-file", true, "the input file to be processed.");
		inOption.setArgName("inputFile");
		return inOption;
	}
	
	private static final Option outputFileOption() {
		Option outOption = new Option("o", "output-file", true, "the output xml file with the resultant test suite");
		outOption.setArgName("outputFile");
		return outOption;
	}
	
	private static final Option helpOption() {
		Option helpOption = new Option("h", "help", false, "print the options usage.");
		helpOption.setArgName("outputFile");
		return helpOption;
	}
	
	
	
	
	private static void runCommand(CommandLine line, Options options) {
		try{
			if(line.hasOption("generate")){
				int loops = Integer.parseInt(line.getOptionValue("g", "1"));
				String inputFile = line.getOptionValue("i");
				String outputFile = line.getOptionValue("o");
				
				SystemController controller = new SystemController();
				String result = controller.generateTestCases(inputFile, loops, outputFile);
				System.out.println(result);
			}else if(line.hasOption("help")){
				printUsage(options);
			}else{ //an else for each feature, and the last 'else' is the option that prints the usage
				printUsage(options);
			}
				
		}catch (NumberFormatException e) {
			System.err.println("For the travLoops argument, please provide an integer value.");
		}
		
	}
	
	private static void printUsage(Options options) {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("priost [-g|-p] [-i|--input-file] inputFilePath [-o|--output-file] outputFilePath", options);
	}

	public static void main(String[] args) {
		try {
			String[] commLine = {"-g", "2", "-i", "/home/felipe/Desktop/ConditionStepResult.tgf", "-o", "/home/felipe/Desktop/output.xml"}; 
//			String[] commLine = {"-h"};
			Options options = getOptions();
			CommandLineParser commandParser = new DefaultParser();
			CommandLine line = commandParser.parse(options, commLine);
			
			runCommand(line, options);
			
		} catch (ParseException e) {
			System.err.println("Parsing failed. Reason: " + e.getMessage());
		}
	}

	
}
