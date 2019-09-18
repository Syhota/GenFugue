package org.genfugue;

import java.util.Random;

/**
 * Individual class for individuals in the population
 * @author Connor J Stephen, 2418377
 *
 */
public class Individual {
	private int melLength;
	private double fitness;
	private String pattern;
	private Random rand = new Random();
	private String[] notes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", 
			"F#", "G", "G#"};

	/**
	 * Constructor for individuals in the population
	 * @param length length of melody
	 */
	public Individual(int length) {
		this.melLength = length;
		create();
		fitness(GenFugue.getKey());
	}
	
	/**
	 * Creates a new pattern at random for the selected individual
	 */
	private void create() {
		pattern = "";
		for (int i = 0; i < melLength; i++) {
			if (pattern == "")
				pattern = (notes[rand.nextInt(notes.length)]);
			else 
				pattern = pattern + " " + (notes[rand.nextInt(notes.length)]);
		}
	}
	
	/**
	 * Returns the fitness of a given individual
	 * @return the fitness
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Returns the melody of a given individual
	 * @return the melody
	 */
	public String getMelody() {
		return pattern;
	}
	
	/**
	 * Sets a new melody for a given individual
	 * @param str the melody given
	 */
	public void setMelody(String str) {
		pattern = str;
	}
	
	/**
	 * Sets the fitness for a given individual
	 * @param key the key to be compared against
	 */
	public void fitness(String key) {
		fitness = StringSimilarity.similarity(pattern, key);
	}
}
