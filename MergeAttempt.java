/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author marion
 */
// Just trying to make a top-down merge sort work in java. 

// This was taken from the wikipedia article on the merge sort algorithm.
// See:   https://en.wikipedia.org/wiki/Merge_sort
public class MergeAttempt
{
	public static void main(String[] args) {
		
		
		// A to be sorted.
		int[] A = {6, 5, 3, 1, 8, 7, 2, 4, 55, 24, 31, 500, 11, 8};
		
		System.out.println("Unsorted array: ");
		printArray(A);
		
		// Here we sort.
		topDownMergeSort(A, A.length);
		
		System.out.println("Sorted array: ");
		printArray(A);
		
	}
	
	// This begins the sorting algorithm.
	// Take in A, create a.
	public static void topDownMergeSort(int[] A, int n) 
	{
		int[] B = new int[A.length]; // This will copy array A into B.
		copyArray(A, 0, A.length, B);
		topDownSplitMerge(B, 0, n, A); // This will sort data from B to A.
	}
	
	/* This splits up the elements in smaller and smaller pieces recursively,
	   they are then sorted as the parts merge upward and are put into position */
	public static void topDownSplitMerge(int[] B, int start, int stop, int[] A)
	{
		// While elements are bigger than size 1, split further.
		if (stop-start >= 2)
		{
			// Split runs longer than 2 into halves.
			int middle = (start + stop) / 2;
			
			// Recursively sort elements of A into B.
			topDownSplitMerge(A, start, middle, B);
			topDownSplitMerge(A, middle, stop, B);
			
			// Merge the results from array B into A.
			topDownMerge(B, start, middle, stop, A);
			
		}
	}
	
	// This sorts the stuff from A int B.
	public static void topDownMerge(int[] A, int start, int middle, int stop, int[] B)
	{
		int i = start;
		int j = middle;
		
		// While there are elements left in the left or right runs...
		for (int k = start; k < stop; ++k)
		{
			// if left run head exists, and is less than or equal to existing right run head.
			if ( i < middle && (j >= stop || A[i] <= A[j]) )
			{
				B[k] = A[i];
				i += 1;
			}
			else 
			{
				B[k] = A[j];
				j += 1;
			}
		}
	}
	
	// Copy array A into B.
	public static void copyArray(int[] A, int start, int stop, int[] B)
	{
		for(int k = start; k < stop; k++)
			B[k] = A[k];
	}

	// ---------------------------------------------------------------------
	
	
	// Prints elements in array A.
	public static void printArray(int[] A) 
	{
		for (int i = 0; i < A.length; ++i)
		{
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}
}







// End.