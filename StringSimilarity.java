package org.genfugue;

/**
 * Operations to compare the similarity of two strings
 * @author Connor J Stephen, 2418377
 *
 */
public class StringSimilarity {

	/**
	 * Calculates the similarity between two given strings
	 * @param first first string
	 * @param second second string
	 * @return the similarity as a double between 0 and 1
	 */
	public static double similarity(String first, String second) {
		String longer = first, shorter = second;
		if (first.length() < second.length()) { // longer should always have greater length
			longer = second; shorter = first;
		}
		int longerLength = longer.length();
		if (longerLength == 0)
			return 1.0; // both strings are zero length
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	/**
	 * Levenshtein edit distance implementation
	 * @param first the first string to compare with
	 * @param second the second string to compare with
	 * @return the
	 */
	public static int editDistance(String first, String second) {
		first = first.toLowerCase();
		second = second.toLowerCase();

		int[] costs = new int[second.length() + 1];
		for (int i = 0; i <= first.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= second.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (first.charAt(i - 1) != second.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[second.length()] = lastValue;
		}
		return costs[second.length()];
	}
}