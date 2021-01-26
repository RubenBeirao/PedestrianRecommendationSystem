package org.quasar.route.graphhopper;

import java.util.LinkedList;

import org.quasar.route.dbConnection.PointOfInterest;
/**
 * This class permits to do permutations of a set of POIs in the same way we do permutations in maths.
 * Having any ordered sequence in hand with a number "n" of distinct elements, 
 * any other sequence formed by the same "n" reordered elements is called permutation.
 * In this way, we can say that if A is a permutation of B, then A and B are made up of the same elements, 
 * but ordered differently.
 * @author Rúben Beirão
 *
 */
public class poiCombination {

	/**
	 * 
	 * @param original The original array
	 * @param nElementsInPermutation The number of elements in each permutation
	 * @return
	 */
	public static LinkedList<LinkedList<PointOfInterest>> choose(LinkedList<PointOfInterest> original,
			int nElementsInPermutation) {
		LinkedList<LinkedList<PointOfInterest>> allPermutations = new LinkedList<LinkedList<PointOfInterest>>();
		enumerate(original, original.size(), nElementsInPermutation, allPermutations);
		return allPermutations;
	}

	/**
	 * 
	 * @param original The original array
	 * @param originalSize The array size
	 * @param nElementsInPermutation The number of elements in each permutation
	 * @param allPermutations All the different permutations
	 */
	private static void enumerate(LinkedList<PointOfInterest> original, int originalSize, int nElementsInPermutation,
			LinkedList<LinkedList<PointOfInterest>> allPermutations) {
		if (nElementsInPermutation == 0) {
			LinkedList<PointOfInterest> singlePermutation = new LinkedList<PointOfInterest>();
			for (int i = originalSize; i < original.size(); i++) {
				singlePermutation.add(original.get(i));
			}
			allPermutations.add(singlePermutation);
			return;
		}

		for (int i = 0; i < originalSize; i++) {
			swap(original, i, originalSize - 1);
			enumerate(original, originalSize - 1, nElementsInPermutation - 1, allPermutations);
			swap(original, i, originalSize - 1);
		}
	}

	/**
	 * Helper function that swaps a.get(i) and a.get(j)
	 * @param a A list of POIs
	 * @param i An int to get the element of the list in that position and set another in that place
	 * @param j An int to get the element of the list in that position and set another in that place
	 */
	public static void swap(LinkedList<PointOfInterest> a, int i, int j) {
		PointOfInterest temp = a.get(i);
		a.set(i, a.get(j));
		a.set(j, temp);
	}
}
