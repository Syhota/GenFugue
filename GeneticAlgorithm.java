package org.genfugue;

import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm {
	static Random rand = new Random();

	/**
	 * Steady-state genetic algorithm utilising tournament selection
	 * @param melLength length of the melody to be created in the Individual class
	 */
	public static void genetic(Vector<Individual> arr, int melLength, int popNum, double acceptable) {
		GeneticOperators.populate(arr, popNum, melLength); // build up the initial population
		boolean belowAcceptable = true; // condition
		int loop = 0;
		int bestNum = 4;
		int mutateChance = 1;
		System.out.println("[GENETIC ALGORITHM STARTING] Starting loop with " + popNum 
				+ " individuals.\n[STATUS] Acceptable average mean is " + acceptable
				+ ".\n[STATUS] Melody length is " + melLength);
		while (belowAcceptable) {
			Vector<Individual> selected = selection(arr);
			Individual child1 = GeneticOperators.crossover(selected.get(0), selected.get(1));
			Individual child2 = GeneticOperators.crossover(selected.get(1), selected.get(0));
			int mutate = rand.nextInt(100);
			if (mutate <= mutateChance) { // 1% chance of mutation
				child1 = GeneticOperators.mutate(child1);
				child2 = GeneticOperators.mutate(child2);
			}
			Vector<Individual> best = new Vector<Individual>(bestNum);
			best.add(selected.get(0));
			best.add(selected.get(1));
			best.add(child1);
			best.add(child2);
			Vector<Individual> bestOffspring = selection(best);
			arr.set(arr.indexOf(selected.get(0)), bestOffspring.get(0));
			arr.set(arr.indexOf(selected.get(1)), bestOffspring.get(1));
			removePoorFit(arr, melLength);
			loop++;
			belowAcceptable = check(arr, acceptable);
			System.out.println("[STATUS] Average fitness is " + mean(arr) +
					"\n[STATUS] Best fitness is " + arr.get(GeneticOperators.getHighestFitness(arr)).getFitness()
					+ "\n[STATUS] Generation " + loop);
		}
		System.out.println("[GENETIC ALGORITHM COMPLETE] Acceptable solution found after " + loop + " generation(s)");
	}

	/**
	 * Removes an individual with poor fitness from the population
	 * and then creates a new individual and adds it to the population
	 * @param arr the array to remove poor individuals from
	 * @param mLen the length of the melody for the new individuals
	 */
	public static void removePoorFit(Vector<Individual> arr, int mLen) {
		int removeSize = arr.size() - 1; // we add one new individual and remove another poorly fitting individual
		for (int i = 0; i < removeSize; i++) {
			arr.remove(GeneticOperators.getLowestFitness(arr));
			Individual newIndividual = new Individual(mLen);
			arr.add(newIndividual);
		}
	}

	/**
	 * Tournament selection
	 * @param arr the array to be passed in
	 * @return array of the two best individuals in the population
	 */
	private static Vector<Individual> selection(Vector<Individual> arr) {
		Vector<Individual> best = new Vector<Individual>(2);
		Individual max = arr.get(GeneticOperators.getHighestFitness(arr));
		Individual second = arr.get(GeneticOperators.getSecondHighest(arr));
		best.add(max);
		best.add(second);
		return best;
	}

	/**
	 * Calculates the average of the fitnesses
	 * @return the average fitness
	 */
	private static double mean(Vector<Individual> arr) {
		double mean = 0;
		for (int i = 0; i < arr.size(); i++) {
			mean = mean + arr.get(i).getFitness();
		}
		mean = mean / arr.size();
		return mean;
	}

	/**
	 * Checks if the mean is above or below the acceptable fitness
	 * @param arr The array currently being worked on
	 * @param acceptable the acceptable fitness
	 * @return true or false
	 */
	private static boolean check(Vector<Individual> arr, double acceptable) {
		if (mean(arr) < acceptable) {
			return true;
		}
		return false;
	}
}
