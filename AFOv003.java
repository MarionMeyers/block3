import java.util.Scanner;

// Basis of alternative form for graph.


/**
 * @author Risbakk
 * @version 2
 * 
 * Our main representation of graphs.   
 */
class altForm 
{
	/**
	 *  Contains information on which index number is which node.
	 *  The integer array node states that the node with index i is known by the number in that place.
	 */
	int[] node;
	
	/**
	 * 2D array showing which nodes are adjacent to which, using indexes.
	 * So it is basically an adjacency matrix, but contains node indexes instead.
	 * Can be understood as adjacent[i] being the array of all the indexes of 
	 * nodes that are adjacent to the node of index[i].
	 */
	int[][] adjacent; 
}

// */

/*
	Put this where you normally write the code in ReadGraph to get the stuff.
	
	ColEdge[] eFix = AFOv003.cleanColEdge(e);
			
			altForm f = new altForm();
			
			AFOv003.graphFullConvert(eFix, seen, f);
			
			AFOv003.printAltForm(f);
			
			int[][] sg = null;
			
			sg = AFOv003.findSubGraphInt(f);
			
			System.out.println("\nIndependent graphs: ");
			for (int i = 0; i < sg.length; ++i)
			{
				System.out.print("// g[" + i + "]: ");
				AFOv003.printAdjacentArray(sg[i], f.node);
			}
			
			
			// Depth first search start!
			int[] clr = null;
			// clr = AFOv003.depthFirstSearch(f, sg[0], clr);
			clr = AFOv003.searchAllDFS(f, sg, clr);
			
			System.out.println("\nFinal result: ");
			AFOv003.printCLR(f, sg, clr);

*/


// Contains methods for manipulating altForm;
// "Alternative Form Operations, version XXX."
/**
 * @author Risbakk, Meyers
 * Contains methods for manipulating the altForm. As well as finding the chromatic number.
 * name is short for "Alternative Form Operations".
 */
public class AFOv003
{
	// Only used for testing purposes.
	static final boolean DEBUG = false;
	
	/*
	 * Process for transforming ColEdge[] e object into altForm[] f object...
	 * - Create fixed version of E with no redundant edges.
	 * - Find actual number of nodes used in graph.
	 * - Count how many separate subgraphs exists within main graph,
	 * and create some sort of array holding starting point for each.
	 * - Convert eFix to f.
	 */
	 
	 
	// Better check if I can make things work.
	public static void main(String[] args)
	{
		/* TEST BINARY SEARCH.
		int[] a = {1, 3, 5, 7, 9, 11, 13 ,15 ,17 ,19, 21, 23, 25, 27 ,29, 31, 33, 35, 37, 39};
		Scanner in = new Scanner(System.in);
		System.out.print("Give number: ");
		System.out.println("Index: " + altFindNode( a, in.nextInt() ) ); // */
		//Put this where you normally write the code in ReadGraph to get the stuff.
	/*
	ColEdge[] eFix = AFOv001.cleanColEdge(e);
			
			altForm f = new altForm();
			
			AFOv001.graphFullConvert(eFix, seen, f);
			
			AFOv001.printAltForm(f);
		int[][]sg = new int[f.node.length/2][f.node.length];
		
		int[][]subgraphs = findSubGraphInt(f,sg); 
		for(int i=0; i<subgraphs.length; i++)
		{
			for(int j=0; j<subgraphs[0].length; j++)
			{
				System.out.print(subgraphs[i][j]);
			}
			System.out.print("\n");
		}*/
		
		
	} // End main.
	 
	 

		/**Takes in an array of edges, ColEdge objects, and removes all instances of
		 * duplicate edges. It goes through each edge, and if we find and equivalent
		 * we mark it. We then create a new a new array and fill it with the 
		 * remaining edges.
		 * 
		 * Edge similarity is thought in terms of. If we have two ColEdge object with 
		 * (u,v) and (u1,v1), if (u,v) is first found then (u1,v1) is removed if either
		 * u=u1 and v=v1, or u=v1 and v=u1.
		 * 
		 * @param e the array of ColEdge objects, a list of edges.
		 * @return eFix the ColEdge array, but this time with redundant edges removed.
		 */
		public static ColEdge[] cleanColEdge(ColEdge[] e)
		{
			// Number-to-be of unique edges.
			int eNum = e.length;
			
			// Go through all edges.
			for (int i = 0; i < e.length; ++i)
			{
				// As long as we have not removed this element...
				if (e[i] != null) {
					// ... go through following edges.
					 for (int j = i + 1; j < e.length; ++j)
					 {
						 // If the edge has not already been removed,
						if (e[j] != null)
						{
							// then remove it, and decrease number of edges.
							if (e[i].u == e[j].u && e[i].v == e[j].v)
							{
								e[j] = null;
								--eNum;
							}
							else if (e[i].u == e[j].v && e[i].v == e[j].u)
							{
								e[j] = null;
								--eNum;
							}
						}
					 } // End for-j.
				}
			} // End for-i.
			 
			 // Make new array of e-objects without redundant edges.
			 ColEdge[] eFix = new ColEdge[eNum];
			 int tmp = 0; // Index for f.
			 
			 // We fill eFix with remaining edges.
			 for (int i = 0; i < e.length; ++i)
			 {
				 if (e[i] != null)
				{
					 eFix[tmp] = e[i];
					 ++tmp;
				}
			}
			 
			 return eFix;
		} // End cleanColEdge.
	 
	
	/* takes the information of vertices in e, what nodes are used in the graph, 
	   and transforms it into full alternative form */
		
	/**takes the information of vertices in e, what nodes are used in the graph, 
	 * and transforms it into full alternative form
	 * <p>
	 * Takes in an array of ColEdge objects e that, if not already containing only
	 * unique edges, should have passed through cleanColEdge. It first finds out
	 * how many nodes are actually used in the graph, and sets up f.node.
	 * After that, for the f.adjacent, it finds the required length
	 * for each, i.e. how many connections it has, and after that fills them.
	 * 
	 * @param e array of ColEdge objects. NB! All edges need to be unique.
	 * @param seen Tells if a given node has been used in e, or not.
	 * @param f The graph wish to convert e into.
	 */
	public static void graphFullConvert(ColEdge[] e, boolean[] seen, altForm f)
	{
		// All purpose iterator.
		int temp = 0;
		
		// First create a sorted node of indexes to node numbers.
		f.node = new int[getGraphNodeNumber(seen, 1)];
		// This inserts nodes in sorted order into f.node, given they are in the graph.
		for (int i = 1; i < seen.length; ++i)
		{
			while (seen[i] == false) {
				++i;
			}
			if (seen[i] == true) 
			{
				f.node[temp] = i;
				++ temp;
			}
		} // End for-i.
		
		// Do we have all nodes we need?
		if (DEBUG)
		{
			System.out.println("\n// nodes: ");
			printIntArray(f.node);
			System.out.println();
		}
		
		// This finds all the adjacents of each node and makes an array of them.
		f.adjacent = new int[f.node.length][0];
		
		for (int i = 0; i < f.node.length; ++i)
		{
			// f[i].adjacents = new int[countEdges(f[i].node, e) ];
			f.adjacent[i] = new int[countEdges(f.node[i], e) ];
			
			// Find the rascals.
			temp = 0; // reusing temp to iterate through f[i].adjacents.
			// while there are still elements to find...
			for (int j = 0; temp < f.adjacent[i].length && j < e.length; ++j) 
			{
				if (e[j].u == f.node[i])
				{
					f.adjacent[i][temp] = altFindNode(f.node, e[j].v );
					++temp;
				}
				else if (e[j].v == f.node[i])
				{
					f.adjacent[i][temp] = altFindNode(f.node, e[j].u );
					++temp;
				}
			} // End for-j.	
			
			// We have now, optimally, found all the elements.
			// Sort them at this point, using merge sort.
			MergeAttempt.topDownMergeSort(f.adjacent[i], f.adjacent[i].length);
		} // End for-i.
		
	} // END graphFullConvert. */
	
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
			// FIND SUBGRAPHS
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	
	
	/**Finds subgraphs of altform f and represents them as arrays of subgraphs, each
	 * array containing the indexes of the given subgraph. There is always at least
	 * one subgraph, which is then the entire graph.   
	 * 
	 * @param f the graph in question.
	 * @return subgraphs 2D array containing all subgraphs.
	 */
	public static int[][] findSubGraphInt(altForm f)
	{
		// set size to biggest possible number of independent graphs.
		int[][] sg = new int[f.node.length/2][];
		int sgIndex = 0; // What subgraph we are on right now.
		
		// Keeps track of seen variables in current subgraph.
		boolean[] seenNow = new boolean[f.node.length];
		// Keeps track of seen variables through all subgraphs.
		boolean[] seenTotal = new boolean[f.node.length];
		
		// Startpoint in a graph.
		int start;
		
		// while we have not covered all nodes in the graph.
		while ( ! allTrue(seenTotal) )
		{
			// Starting point for next subgraph.
			start = findNextStartSG(seenTotal);
			if (DEBUG) { System.out.println("\nstarting point: " + start); }
			// set-up for next graph.
			setBoolVal(seenNow, false);
			
			// Finds all connected nodes from a given startNode.
			subGraphBoolSearch(f, start, seenNow, seenTotal); // CREATE THIS.
			
			printBoolArray(seenNow);
			
			// creates array of all indexes of subgraph from array of seen nodes.
			sg[sgIndex] = setIndexByBool(seenNow, true);

			printIntArray(sg[sgIndex]);
			
			++sgIndex; // next sub-graph.
		} // End while.
		
		// Make a new sgFinal with no extra empty arrays at the end.
		int[][] sgFinal = new int[sgIndex][];
		for (int i = 0; i < sgFinal.length; ++i)
		{
			sgFinal[i] = new int[sg[i].length];
			System.arraycopy(sg[i], 0, sgFinal[i], 0, sg[i].length);
		}
		
		return sgFinal; 
	} // End findSubGraphInt.
	
	
	/** returns true if all boolean values in array b are true.
	 *
	 * @param b array of booleans
	 * @return truthVal
	 */
	public static boolean allTrue(boolean[] b)
	{
		for (int i = 0; i < b.length; ++i)
		{
			if (b[i] == false) {return false;}
		}
		return true;
	} // End allTrue;
	
	
	/**	Set all values of a given boolean array to val.
	 *
	 * @param b array of boolean values
	 * @param val truth value we wish to set them to.
	 */
	public static void setBoolVal(boolean[] b, boolean val)
	{
		for (int i = 0; i < b.length; ++i)
		{
			b[i] = val;
		}
	} // End setBoolVal.
	
	
	/** Returns the index of the first value (from the left) found that is false.
	 * @param seenTotal boolean array representing if nodes have been found.
	 * @return val node index of the first value that is false.
	 */
	public static int findNextStartSG(boolean[] seenTotal)
	{
		for (int i = 0; i < seenTotal.length; ++i)
		{
			// If false, return index.
			if ( !seenTotal[i] ) { return i; }
		}
		System.out.println("This is not supposed to happen.");
		return 0; // Catch-all. The code should never reach this point!
	} // End findNextStartSG.
	
	
	/** Creates an array where the elements are the index values of
	 * each element in seenNow with truth value val.
	 * 
	 * @param seenNow the nodes found to be connected to each other.
	 * @param val the value we wish to set into the array.
	 * @return subgraphArray an array of an independent suvgraph.
	 */
	public static int[] setIndexByBool(boolean[] seenNow, boolean val)
	{
		
		int[] subgraph;
		int size = getGraphNodeNumber(seenNow, 0);
		
		if (DEBUG) {System.out.println("Size: " + size);};
		
		int i = 0;
		
		// Get subgraph size.
		if (val) { subgraph = new int[size]; }
		else { subgraph = new int[seenNow.length - size] ;}
		// The last one is for if you want to create an array of indexes with negative value.
		
		// Enter elements.
		for (int j = 0; j < seenNow.length; ++j)
		{
			// if match, add index.
			if (seenNow[j] == val)
			{ 
				subgraph[i] = j; 
				++i; 
			}
		}
		return subgraph;
	} // End setIndexByBool.
	
	/**Backtracking algorithm for finding subgraph.
	 * 
	 * @param f the graph
	 * @param the starting point
	 * @param seenNow nodes seen in this current run
	 * @param seenTotal nodes seen through all runs so far.
	 */
	public static void subGraphBoolSearch(altForm f, int start, boolean[]seenNow, boolean[]seenTotal)
	{
		//stopping condition
		if(seenNow[start]==true)
			return; 
		
		//find subgraph
		seenNow[start]=true; 
		seenTotal[start]=true; 
		for(int i=0; i<f.adjacent[start].length; i++)
		{
			//seeNow[f.adjacents[start][i]]=true; 
			//seeTotal[f.adjacents[start][i]]=true; 
			subGraphBoolSearch(f, f.adjacent[start][i], seenNow, seenTotal);
		}
		
		
	}
	
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
			// DEPTH FIRST SEARCH
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	
	
	/**Goes through all subgraphs to find the chromatic number.
	 * 
	 * @param f graph
	 * @param subgraph all independent subgraphs.
	 * @param clr array of abstract colours.
	 * @return clrFinal a final solution.
	 */
	public static int[] searchAllDFS(altForm f, int[][] subgraph, int[] clr)
	{
		// Will be the chromatic number.
		int chromaticNumber = 0;
		// The entire colouring of the graph.
		clr = new int[f.adjacent.length + 1];
		
		// Go through all independent subgraphs, find their colourings.
		for (int i = 0; i < subgraph.length; ++i)
		{
			int[] clrSubset = null;
			clrSubset = depthFirstSearch(f, subgraph[i], clrSubset);
			// The chromatic number is equal to the chromatic number
			// of the most complex independent subgraph.
			if (chromaticNumber < clrSubset[0])
			{ chromaticNumber = clrSubset[0]; }
			
			// We now copy the subgraph colouring to the entire-graph colouring.
			for (int j = 0; j < subgraph[i].length; ++j)
			{
				clr[1+subgraph[i][j]] = clrSubset[1+j] ;
			} // End for-j.
			// Set chromatic number.
			clr[0] = chromaticNumber;
		} // End for-i.
		
		return clr;
	}
	
	
	/**Depth first search through the graph to find chromatic number.
	 * 
	 * @param f graph
	 * @param subgraph
	 * @param clr array of colours to use
	 * @return clrFinal a particular solution.
	 */
	public static int[] depthFirstSearch(altForm f, int[] subgraph, int[] clr)
	{
		if (DEBUG) 
		{ 
			System.out.println("\n// Start depth first search..."); 
			System.out.println("// Nodes: " + subgraph.length); 
			}
		
		// The first element is the highest n# of searchable colours 
		clr = new int[subgraph.length+1];
		
		// Nodes we have been through. The first element is how far
		// we are in the search path. -1 signifies an empty path element.
		int[] path = new int[subgraph.length+1];
		setIntArray(path, (-1) );
		
		// Search as long as this is true.
		boolean keepSearching = true;
		
		// Search for a given amount of colours.
		for (int i = 2; i <= subgraph.length && keepSearching; ++i)
		{	
			clr[0] = i;
			path[0] = 0;
			
			if (DEBUG) { System.out.println("\n// Search for " + clr[0] + " colours.\n"); } 
			
			keepSearching = !dfSearch(subgraph[0], path, clr, subgraph, f);
			
			// Reset for next search.
			if (keepSearching)
			{
				setIntArray(clr, 0);
				setIntArray(path, (-1) );
				
				if (DEBUG)
				{
					System.out.println("\nEnsuring reset: ");
					printIntArray(clr);
					printIntArray(path);
				}
			} // End if-keepSearching.
			
		} // End for-i.
		
		return clr;
	} // End depthFirstSearch.
		

	/**recursive method for depth-first search. 
	 * <p>
	 * Takes as arguments the node index
	 * to search from (n), the unique path number for that step (p), the path that
	 * has been traveled so far (path), the array of colour assignments (clr), the
	 * independent subgraph of nodes in f that we are looking through (subgraph), 
	 * and the adjacency matrix we are looking for (altForm f).
	 *  
	 * @param n node searched from
	 * @param path the path traveled so far.
	 * @param clr the current clr array.
	 * @param subgraph 
	 * @param f graph
	 * @return truthVal was the search successful or not?
	 */
	public static boolean dfSearch(int n, int path[], int[] clr, int[] subgraph, altForm f)
	{
		// Local boolean for each step.
		boolean search;
		// This is the position of this step in the path.
		int p;
		
		p = 1 + path[0]; // Create place in path.
		++path[0]; // Prepare for next path element.
		path[p] = n; // Path is this node.
		
		if (DEBUG)
		{ System.out.println("// Init: Node " + n + ", Path: " + p); }
		
		// Search through colours.
		for (int i = 1; i <= clr[0]; ++i)
		{
			
			// Only need to check for one colour for first node, and 2 for second node connected to first node.
			// They are always guaranteed to be these numbers if 'i' is the chromatic numbers.
			if (p == 1 & i > 1) 
			{ 
				if (DEBUG) { System.out.println("Ending colour search for first node."); }
				return false; 
			}
			if (p == 2 & i > 2) 
			{ 
				if (DEBUG) { System.out.println("Ending colour search for second node."); }
				return false; 
			}
			
			
			// is the colour legal? Then continue on.
			if ( legalColour(n, i, clr, subgraph, f) )
			{
				// Assign colour. 
				clr[1 + altFindNode(subgraph, n)] = i;
				search = true; // The search is on.
				
				if (DEBUG)
				{
					System.out.println("// Legal colour: " + i);
					printDebugDFS(n, i, p, clr, path);
					printIndexOrderedColourMap(clr,path);
				}
				
				// Search through all adjacent nodes.
				for (int j = 0; search && j < f.adjacent[n].length; ++j )
				{
					// Only search nodes that are uncoloured.
					if ( clr[1 + altFindNode(subgraph, f.adjacent[n][j])] == 0 )
					{ search = dfSearch(f.adjacent[n][j], path, clr, subgraph, f); }
				} // End for-j.
				
				if (DEBUG)
				{
					System.out.println("Search: " + search);
					System.out.println("Back at node: " + n + ", step: " + p );
				}
				
				// If search is true, then the searches of all adjacents were true.
				// And we have coloured all sub-adjacents successfully.
				if (search) { return true; }
				else 
				{
					if (DEBUG) 
					{
						System.out.println("\n// Colour clash!");
						printDebugDFS(n, i, p, clr, path);
						printIndexOrderedColourMap(clr,path);
					}
					
					// If we are here, there was a colour clash. Reset path.
					for (int j = p + 1; j <= path[0]; ++j)
					{
						if (DEBUG) { System.out.println("clr: " + (1 + altFindNode(subgraph, path[j])) + " = 0."); }
						
						// clr[1 + path[j] ] = 0; // Reset colour.
						clr[1 + altFindNode(subgraph, path[j] ) ] = 0; // Reset colour.
						path[j] = -1;
					} // End for-j.
					path[0] = p; // Reset path.
					
					if (DEBUG)
					{
						System.out.println("// Is the clearing method correct?");
						printDebugDFS(n, i, p, clr, path);
						printIndexOrderedColourMap(clr,path);
					}
					
				}
			} // End if-legalColour.
			else if (DEBUG) { System.out.println("// Illegal colour: " + i); }
			
		} // End for-i.
		
		if (DEBUG) {System.out.println("Search failed for step: " + p ); }
		// We have searched through all colours and come up empty.
		return false;
	} // End dfSearch.

	
	/**See if node (n) can be coloured with (c).
	 * @param n node to be coloured
	 * @param c colour
	 * @param clr current array of clr
	 * @param subgraph
	 * @param f graph
	 * @return truthVal is the colouring legal?
	 */
	public static boolean legalColour(int n, int c, int[] clr, int[] subgraph, altForm f)
	{
		// Go through adjacents of n to see if any colours clash.
		for (int i = 0; i < f.adjacent[n].length; ++i)
		{
			// To access the colour: 
			// clr[1 + altFindNode(subgraph, f.adjacent[n][i])]
			// If colour clash, return false.
			if (clr[1 + altFindNode(subgraph, f.adjacent[n][i])] == c ) 
			{ return false; }
		}
		// No colour clashes, continue!
		return true;
	} // End legalColour.

	
	/**Sets all values of a given array (a) to a number (n).
	 * @param a array
	 * @param n the number.
	 */
	public static void setIntArray(int[] a, int n)
	{
		for (int i = 0; i < a.length; ++i)
		{
			a[i] = n;
		}
	} // End setIntArray.
	
	
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
			// Some small operations.
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	
	
	
	
	// This returns the number of nodes actually used in the graph.
	public static int getGraphNodeNumber(boolean[] seen, int start)
	{
		int observed = 0;
		
		for (int i = start; i < seen.length; ++i)
		{
			if (seen[i] == true) 
				++observed;
		}
		
		return observed;
	} // End getGraphNodeNumber.
	
	
	// Counts how many edges node has in e.
	public static int countEdges(int node, ColEdge[] e)
	{
		int total = 0;
		for (int i = 0; i < e.length; ++i)
		{
			if (e[i].u == node) ++total;
			else if (e[i].v == node) ++ total;
		}
		
		return total;
	} // End countEdges.
	 
	 
	/* Given an array of sorted numbers, and a given number, the method 
    * finds the index of the given number or returns -1 if it fails.
    * This was adapted from the wikipedia article,:
    * https://en.wikipedia.org/wiki/Binary_search_algorithm  
	*/
	public static int altFindNode(int[] node, int n)
	{
		
		int l = 0; // Left-most value
		int r = node.length-1; // Right-most value
		int m = 0; // Middle
		
		while ( node[m] != n)
		{
			if (l > r) { return (-1); }
			
			m = (l+r)/2;
			
			if (node[m] < n)
				l = m + 1;
			else if (node[m] > n)
				r = m-1;
		}
		
		return m;
	} // End altFindNode.
	 
	 
	 
	/*
		Returns the number of edges in the subgraph. We know the subgraph is independent,
		and each edge is listed two times, so we only need to sum the number of adjacents
		for all nodes in subgraph and divide by two to get that number.
	*/
	public static int getEdgeNumberInSubgraph(altForm f, int[] subgraph)
	{
		int edges = 0;
		
		for (int i = 0; i < subgraph.length; ++i)
		{
			edges += f.adjacent[subgraph[i]].length;
		}
		
		return (edges/2);
	} // End getEdgeNumberInSubraph
	 
	 
	 /*
		A complete graph has 1+2+...+(n-1) edges. This computes that number for a given
		number of nodes n.
	*/
	public static int completeGraphEdgeNumber(int n)
	{
		//
		return (((n-1)*n)/2 );
	} 
	 
	 
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
			// Printers. I should put these in a separate class, honestly.
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------
	 
	 
	 
	// We print altForm.
	public static void printAltForm(altForm f)
	{
		for (int i = 0; i < f.adjacent.length; ++i)
		{
			System.out.print("// " + " " + f.node[i] + ": {" );
			for (int j = 0; j < f.adjacent[i].length - 1; ++j) 
			{
				System.out.print(f.node[ f.adjacent[i][j] ] + ", ");
			}
			if ( f.adjacent[i].length > 0 )
			{
				System.out.println(f.node[ f.adjacent[i][f.adjacent[i].length - 1] ] + " }");
			}
			else
			{
				System.out.println(" }");
			}
		}
	} // */
	
	
	public static void printIntArray(int[] a)
	{
		if (a == null) {System.out.println("You dun goofd.");}
		// if (DEBUG) { System.out.println("size a: " + a.length); }
		
		for (int i = 0; i < a.length -1; ++i)
		{
			System.out.print(a[i] + ", ");
		}
		System.out.println(a[a.length-1]);		
	}
	
	
	public static void printAdjacentArray(int[] a, int[] nodes)
	{
		if (a == null) {System.out.println("You dun goofd.");}
		// if (DEBUG) { System.out.println("size a: " + a.length); }
		
		for (int i = 0; i < a.length -1; ++i)
		{
			System.out.print(nodes[a[i]] + ", ");
		}
		System.out.println(nodes[a[a.length-1]]);
	}
	
	
	public static void printBoolArray(boolean[] b)
	{
		for (int i = 0; i < b.length; ++i)
		{
			if (b[i]) { System.out.print("T "); }
			else { System.out.print("F "); }
		}
		System.out.println();
	}
	
	
	// Prints the graph in colours.
	public static void printCLR(altForm f, int[] sg, int[] clr)
	{
		System.out.println("Colour comparison: ");
		
		
		for (int i = 0; i < sg.length; ++i)
		{
			System.out.print("(" + f.node[sg[i]] + ":" + clr[i+1] + ") - ");
			for (int j = 0; j < f.adjacent[sg[i]].length; ++j)
			{
				System.out.print(f.node[f.adjacent[sg[i]][j]] + ":" + clr[1 + altFindNode(sg, f.adjacent[sg[i]][j])] + " ");
			}
			System.out.println();
		}
		
		System.out.println("\nColours: " + clr[0]);
	} // End printCLR.
	
	
	// Prints the graph in colours, now for all subgraphs.
		public static void printCLR(altForm f, int[][] sg, int[] clr)
		{
			System.out.println("Colour comparison: ");
			
			// Go through all subgraphs.
			for (int i = 0; i < sg.length; ++i)
			{
				System.out.println("\nIndependent subgraph " + i);
				// Print nodes & colours in subgraph.
				for (int j = 0; j < sg[i].length; ++j)
				{
					System.out.print("(" + f.node[ sg[i][j] ] + ":" + clr[ 1+sg[i][j] ] + ") - ");
					for (int k = 0; k < f.adjacent[sg[i][j]].length; ++k)
					{
						System.out.print(f.node[f.adjacent[sg[i][j]][k]] + ":" 
					+ clr[1 + f.adjacent[sg[i][j]][k]] + " ");
					}
					System.out.println();
				}
			}
			
			System.out.println("\nColours: " + clr[0]);
		} // End printCLR.
	
	
	public static void printDebugDFS(int n, int i, int p, int[] clr, int[] path)
	{
		System.out.println("// Node: " + n + ", Colour: " + i + ", step: " + p);
		System.out.println("// Colouring: ");
		System.out.print("// ");
		printIntArray(clr);
		System.out.println("// Path: ");
		System.out.print("// ");
		printIntArray(path);
		System.out.println();
	} // End printDebugDFS.
	
	
	// Prints the colours as ordered by index number.
	public static void printIndexOrderedColourMap(int[] clr, int[] path)
	{
		System.out.println("\nColour by Index Map: ");
		for (int i = 1;  i < path.length; ++i)
		{
			if (path[i] == -1)
			{
				System.out.print(". ");
			}
			else
			{
				System.out.print(clr[1+path[i]] + " ");
			}
		}
		System.out.println("\n");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
} // End.

















// .