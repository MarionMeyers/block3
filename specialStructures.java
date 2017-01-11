public class specialStructures
{
	private static boolean DEBUG = true; 
	/*A graph is CONNECTED when there is a path between every pair of vertices. 
	In a connected graph, there are no unreachable vertices.
	A graph that is not connected is disconnected. ...
	A graph with just one vertex is connected.*/
	
	/* A graph is said to be MINIMALLY CONNECTED if removal of any one edge from it
	disconnects the graph. Clearly, a minimally connected graph has no cycles.
	Because if you remove one edge from a circle, you can still access the rest of the graph */
	
	
	//TRUE IF SUBGRAPH IS A TREE, FALSE IF NOT
	//because we know that the subgraph is a connected graph, the algorithm to determine if it's a tree
	//becomes very simple because we actually only need to check if there s n-1 edges (n = number of nodes)
	public static boolean findTree(int[]aSubgraph, altForm f)
	{
		
		//NUMBER OF VERTICES AND EDGES
		int numVertices = aSubgraph.length; 
		int numEdges = 0;
		
		
		//calculate the edges
		for(int i=0; i<aSubgraph.length; i++)
		{
			numEdges+=(f.adjacent[aSubgraph[i]].length);
		}
		
		numEdges=(numEdges/2);
		if(DEBUG)
		{
			System.out.println("number of edges is : "+numEdges);
			System.out.println("number of vertices is : "+numVertices);
		}
		
		
		
		
		//IF A SUBGRAPH HAS N NODES AND N-1 EDGES, IT IS A TREE
		
		//not a tree
		if(numEdges!= (numVertices-1))
		{
			return false; 
		}
		//tree --> chromatic number = 2
		else
		{
			return true; 
		}
			
	}
	
	/*If a graph is complete, chromatic number = number of vertices*/
	public static boolean findCompleteGraph(int[]aSubgraph, altForm f)
	{
		int numVertices = aSubgraph.length; 
		boolean complete = true; 
		
		for(int i=0; i<aSubgraph.length; i++)
		{
			if(f.adjacent[aSubgraph[i]].length != (numVertices-1))
				complete = false; 
		}
		
		//The graph is complete, chrom number = numb vertices
		if(complete==true)
			return true;
		//not complete
		else
			return false; 
	}
	
	public static boolean Circle(int[]aSubgraph, altForm f)
	{

		for(int i=0; i<aSubgraph.length; i++)
		{
			if(f.adjacent[aSubgraph[i]].length != 2){
			return false; }
		}
		
		return true;
	}
	
	
}