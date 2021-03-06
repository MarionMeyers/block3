/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
/**
 *
 * @author marion
 */
class ColEdge
{
    int u;
    int v;
}
		
public class ReadGraphMarion
{

    public final static boolean DEBUG = false;

    public final static String COMMENT = "//";
    
    private static altForm f;

    public static void main( String args[] )
    {
                    if( args.length < 1 )
                            {
                            System.out.println("Error! No filename specified.");
                            System.exit(0);
                            }


                    String inputfile = args[0];

                    boolean seen[] = null;

                    //! n is the number of vertices in the graph
                    int n = -1;

                    //! m is the number of edges in the graph
                    int m = -1;

                    //! e will contain the edges of the graph
                    ColEdge e[] = null;

                    try 	{ 
                                    FileReader fr = new FileReader(inputfile);
                                    BufferedReader br = new BufferedReader(fr);

                                    String record = new String();

                                    //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
                                    //! These comments are only allowed at the top of the file.

                                    //! -----------------------------------------
                                    while ((record = br.readLine()) != null)
                                            {
                                            if( record.startsWith("//") ) continue;
                                            break; // Saw a line that did not start with a comment -- time to start reading the data in!
                                            }

                                    if( record.startsWith("VERTICES = ") )
                                            {
                                            n = Integer.parseInt( record.substring(11) );					
                                            if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
                                            }

                                    seen = new boolean[n+1];	

                                    record = br.readLine();

                                    if( record.startsWith("EDGES = ") )
                                            {
                                            m = Integer.parseInt( record.substring(8) );					
                                            if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
                                            }

                                    e = new ColEdge[m];	

                                    for( int d=0; d<m; d++)
                                            {
                                            if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
                                            record = br.readLine();
                                            String data[] = record.split(" ");
                                            if( data.length != 2 )
                                                            {
                                                            System.out.println("Error! Malformed edge line: "+record);
                                                            System.exit(0);
                                                            }
                                            e[d] = new ColEdge();

                                            e[d].u = Integer.parseInt(data[0]);
                                            e[d].v = Integer.parseInt(data[1]);

                                            seen[ e[d].u ] = true;
                                            seen[ e[d].v ] = true;

                                            if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);

                                            }

                                    String surplus = br.readLine();
                                    if( surplus != null )
                                            {
                                            if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
                                            }

                                    }
                    catch (IOException ex)
                            { 
                            // catch possible io errors from readLine()
                            System.out.println("Error! Problem reading file "+inputfile+ " " +ex);
                            System.exit(0);
                            }

                    for( int x=1; x<=n; x++ )
                            {
                            if( seen[x] == false )
                                    {
                                    if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
                                    }
                            }

                    //! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
                    //! e[1] will be the second edge...
                    //! (and so on)
                    //! e[m-1] will be the last edge
                    //! 
                    //! there will be n vertices in the graph, numbered 1 to n

                    //! INSERT YOUR CODE HERE!
                    ColEdge[] eFix = AFOv003.cleanColEdge(e);

                    f = new altForm();

                    AFOv003.graphFullConvert(eFix, seen, f);

                    //AFOv003.printAltForm(f);

                    int[][] sg = AFOv003.findSubGraphInt(f);

                    System.out.println("\nIndependent graphs: ");
                    for (int i = 0; i < sg.length; ++i)
                    {
                            System.out.print("// g[" + i + "]: ");
                            AFOv003.printIntArray(sg[i]);
                    }
                    
                    /*FIND TREES IN GRAPH*/
                    int countTrees=0; 
                    for(int i=0; i<sg.length; i++)
                    {
                    	 if(specialStructures.findTree(sg[i],f) == true)
                    	 {
                    		if(DEBUG) 
                    			System.out.println("this is a tree");
                    		
                    		 countTrees++; 
                    	 }
                    	 else
                         {
                         	if(DEBUG)
                         		System.out.println("this is not a tree");
                         }
                         
                    }
                   	System.out.println("There are "+countTrees+"trees");
                   
                    /*FIND COMPLETE GRAPHS*/
                    int countComp=0;
                    for(int i=0; i<sg.length; i++)
                    {
                    	 if(specialStructures.findCompleteGraph(sg[i],f) == true)
                    	 {
                    		 if(DEBUG)
                    			System.out.println("this is a complete graph");
                    		 
                    		countComp++;
                    		
                    	 }
                    	 else
                         {
                         	if(DEBUG)
                         		System.out.println("this is not complete");
                         }
                         
                    }
                    
                  
                	System.out.println("There are "+countComp+" complete graphs");
                   


            }
   
}