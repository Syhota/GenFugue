package org.genfugue;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.jfugue.pattern.*;
import org.jfugue.player.Player;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 *  @author Connor J Stephen, 2418377 
 */

/**
 * TASKS REMAINING:
 * -combine a list of valid patterns into a song
 * -GUI
 */
public class GenFugue {
	public static ChordProgression chordProg = new ChordProgression("I IV V III");
	public static Vector<Individual> popInd;
	private static Pattern finalSong;
	private static Integer melLength;
	private static Integer popNum;
	private static Double acceptable;
	private static String key;
	private static String[] keys = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#",
								"G", "G#", "AMIN", "A#MIN", "BMIN", "CMIN", "C#MIN", 
								"DMIN", "D#MIN", "EMIN", "FMIN", "F#MIN", "GMIN", "G#MIN"};

	/**
	 * Main class to instantiate and run the algorithm
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		CommandLine commandLine;
	    Option option_p = Option.builder("p").argName("population").hasArg().desc("The population size").build();
	    Option option_k = Option.builder("k").argName("key").hasArg().desc("The key").build();
	    Option option_l = Option.builder("l").argName("length").hasArg().desc("The length of the melodies").build();
	    Option option_a = Option.builder("a").argName("acceptable fitness").hasArg().desc("The acceptable mean fitness").build();
	    Options options = new Options();
	    CommandLineParser parser = new DefaultParser();

	    options.addOption(option_p);
	    options.addOption(option_k);
	    options.addOption(option_l);
	    options.addOption(option_a);

	    String header = "               [<arg1> [<arg2> [<arg3> ...\n       Options, flags and arguments may be in any order";
	    String footer = "Automatically generate melodies from a given key utilising a genetic algorithm.";
	    HelpFormatter formatter = new HelpFormatter();
	    formatter.printHelp("GenFugue", header, options, footer, true);
	    
	    try {
	        commandLine = parser.parse(options, args);

	        if (commandLine.hasOption("p")) {
	            popNum = Integer.parseInt(commandLine.getOptionValue("p"));
	            if (popNum < 4) {
	            	System.out.println("Population number is " + popNum + ". Must be 4 or above.");
	            	System.exit(-1);
	            }
	        }

	        if (commandLine.hasOption("k")) {
	            key = commandLine.getOptionValue("k");
	            key = key.toUpperCase();
	            boolean found = false;
	            for (String element: keys) {
	            	if (element.equals(key)) {
	            		found = true;
	            		continue;
	            	}
	            }
	            if (!found) {
	            	System.out.println("Unacceptable key. Example: C, AMIN, G#, F#MIN");
	            	System.exit(-1);
	            }
	        }

	        if (commandLine.hasOption("l")) {
	            melLength = Integer.parseInt(commandLine.getOptionValue("l"));
	            if (melLength < 4) {
	            	System.out.println("Length is " + melLength + ". Must be 4 or above.");
	            	System.exit(-1);
	            }
	        }
	        
	        if (commandLine.hasOption("a")) {
	        	acceptable = Double.parseDouble(commandLine.getOptionValue("a"));
	        	if (acceptable < 0 || acceptable > 1) {
	        		System.out.println("Acceptable fitness is " + acceptable + ". Must be between 0 and 1.");
	        		System.exit(-1);
	        	}
	        }
	    }
	    
	    catch (ParseException exception) {
	        System.out.print("Parse error: ");
	        System.out.println(exception.getMessage());
	    }
	    
	    if (popNum == null) {
	    	popNum = 10;
	    }
	    if (melLength == null) {
	    	melLength = 4;
	    }
	    if (acceptable == null) {
	    	acceptable = 0.5;
	    }
	    if (key == null) {
	    	setKey("C");
	    }
		
		popInd = new Vector<Individual>(popNum);

		GeneticAlgorithm.genetic(popInd, melLength, popNum, acceptable);
		combine();
		saveToFile(finalSong);
		
		Player player = new Player();
		System.out.println("[FINISHED] Now playing final song");
		player.play(finalSong);
		
		System.exit(0);
	}

	/**
	 * Combines the final state of the population into one song
	 */
	static void combine() {
		finalSong = new Pattern();
		for (int i = 0; i < popInd.size(); i++) {
			finalSong.add(StringOperators.stringToPattern(popInd.get(i).getMelody()));
		}
	}

	/**
	 * Saves a given pattern to a file with a number appended to the name
	 * @param writeable the pattern to be written
	 * @param i the number given to name the file with
	 */
	private static void saveToFile(Pattern writeable) {
		File file = new File("song" + ".staccato");
		System.out.println("[STATUS] Writing " + writeable.toString() + " to " + file.getAbsolutePath());
		try {
			writeable.save(file, "Generated by GenFugue");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles key defined by user
	 * @param key the given key
	 */
	private static void setKey(String key) {
		switch (key) {
		case "C": chordProg.setKey(key); 	 break;
		case "CMIN": chordProg.setKey(key);  break;
		case "C#": chordProg.setKey(key); 	 break;
		case "C#MIN": chordProg.setKey(key); break;
		case "D": chordProg.setKey(key); 	 break;
		case "DMIN": chordProg.setKey(key);  break;
		case "D#": chordProg.setKey(key); 	 break;
		case "D#MIN": chordProg.setKey(key); break;
		case "E": chordProg.setKey(key); 	 break;
		case "EMIN": chordProg.setKey(key);  break;
		case "F": chordProg.setKey(key); 	 break;
		case "FMIN": chordProg.setKey(key);  break;
		case "F#": chordProg.setKey(key); 	 break;
		case "F#MIN": chordProg.setKey(key); break;
		case "G": chordProg.setKey(key); 	 break;
		case "GMIN": chordProg.setKey(key);  break;
		case "G#": chordProg.setKey(key); 	 break;
		case "G#MIN": chordProg.setKey(key); break;
		case "A": chordProg.setKey(key); 	 break;
		case "AMIN": chordProg.setKey(key);	 break;
		case "A#": chordProg.setKey(key); 	 break;
		case "A#MIN": chordProg.setKey(key); break;
		case "B": chordProg.setKey(key);	 break;
		case "BMIN": chordProg.setKey(key);	 break;
		default: chordProg.setKey("C");
		}
	}

	/**
	 * Gets key defined by user
	 * @return
	 */
	public static String getKey() {
		Chord[] key = chordProg.getChords();
		String strKey = StringOperators.chordFixer(key);
		return strKey;
	}
}