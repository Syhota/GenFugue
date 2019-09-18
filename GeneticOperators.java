package org.genfugue;

import java.util.Random;
import java.util.Vector;

public class GeneticOperators {
	
	static Random rand = new Random();
	/**
	 * Initial population, called from main
	 * @param popNum total individuals in population
	 * @param mLen length of melodies
	 */
	static void populate(Vector<Individual> arr, int popNum, int mLen) {
		for (int i = 0; i < popNum; i++) {
			Individual ind = new Individual(mLen);
			arr.add(ind);
		}
	}

	/**
	 * Uniform crossover operation
	 * @param chromA fittest individual
	 * @param chromB second fittest pattern
	 * @return genetically crossed over pattern
	 */
	static Individual crossover(Individual ind1, Individual ind2) {
		String chromC = "";
		String[] chromA = StringOperators.stringToStringArr(ind1.getMelody());
		String[] chromB = StringOperators.stringToStringArr(ind2.getMelody());
		if (ind1.getMelody().length() != ind2.getMelody().length()) {
			return ind1;
		}
		for (int i = 0; i < chromA.length; i++) {
			String tmp = chromA[i];
			chromA[i] = chromB[i];
			chromC = chromC + tmp; 
		}
		ind1.setMelody(chromC);
		ind1.fitness(GenFugue.getKey());
		return ind1;
	}

	/**
	 * Mutates a pattern randomly utilising Fischer-Yates shuffle
	 * @param ind individual with a pattern to mutate
	 * @return Mutated individual
	 */
	static Individual mutate(Individual ind) {
		String st = ind.getMelody();
		String[] str = st.split(" ");
		for (int i = 0; i < str.length; i++) {
			// get a random index of the array past the current index
			int randomValue = i + rand.nextInt(str.length - i);
			// swap the random element with the present element
			String randomElement = str[randomValue];
			str[randomValue] = str[i];
			str[i] = randomElement;
		}
		st = StringOperators.stringBuilder(str);
		ind.setMelody(st);
		ind.fitness(GenFugue.getKey());
		return ind;
	}

	/**
	 * Gets highest fitness in the population array and returns its index
	 * @return index of highest fit
	 */
	static int getHighestFitness(Vector<Individual> arr) {
		int index = 0;
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).getFitness() > arr.get(index).getFitness()) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Gets second highest fitness in the population array and returns its index
	 * @return index of second highest fit
	 */
	static int getSecondHighest(Vector<Individual> arr) {		
		double max = arr.get(0).getFitness();
		double secondMax = 0;
		int first = 0, second = 0;
		
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getFitness() > max) {
				secondMax = max;
				second = first;
				max = arr.get(i).getFitness();
				first = i;
			} else if (arr.get(i).getFitness() > secondMax) {
				secondMax = arr.get(i).getFitness();
				second = i;
			}
		}
		
		if (arr.get(first).getFitness() == arr.get(second).getFitness()) {
			second = first;
		}
		return second;
	}
	
	/**
	 * Gets the index of the lowest fitness in an array of individuals
	 * @return the index of the individual with the lowest fitness
	 */
	static int getLowestFitness(Vector<Individual> arr) {
		double min = arr.get(0).getFitness();
		int i = 0;
		int lowest = 0;
		for (; i < arr.size(); i++) {
			if (arr.get(i).getFitness() < min) {
				min = arr.get(i).getFitness();
				lowest = i;
			}
		}
		return lowest;
	}
}
