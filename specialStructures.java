public class specialStructures
{
	/*A graph is CONNECTED when there is a path between every pair of vertices. 
	In a connected graph, there are no unreachable vertices.
	A graph that is not connected is disconnected. ...
	A graph with just one vertex is connected.*/
	
	/* A graph is said to be MINIMALLY CONNECTED if removal of any one edge from it
	disconnects the graph. Clearly, a minimally connected graph has no cycles.
	Because if you remove one edge from a circle, you can still access the rest of the graph */
	
	
	//TRUE IF SUBGRAPH IS A TREE, FALSE IF NOT
	public boolean findTree(int[][]aSubgraph)
	{
		//restrictions for f to be a tree
		//- there exists only one path for each pair of vertices
		
		//- Any connected graph with n vertices and (n-1) edges is a tree
		//- f is a tree iff it is minimally connected
		
		//NUMBER OF VERTICES AND EDGES
		int numVertices = aSubgraph.length; 
		int numEdges = 0;
		
		
		//calculate the edges
		for(int i=0; i<aSubgraph.length; i++)
		{
			numEdges+=(aSubgraph[i].length);
		}
		
		numEdges=(numEdges/2);
		
		
		//IF A SUBGRAPH HAS N NODES AND N-1 EDGES, IT IS A TREE
		if((numEdges-1)!=numVertices)
		{
			return false; 
		}
		
		else
			return true; 
 
	}
	
	
}