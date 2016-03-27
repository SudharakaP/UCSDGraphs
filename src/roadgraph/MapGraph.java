package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and Sudharaka Palamakumbura
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between roads
 *
 */
public class MapGraph {

	// Member Variables
	private int numVertices;
	private int numEdges;
	private Map<GeographicPoint, ArrayList<EdgeData>> adjListsMap;
	
	// HashMap to store MapNode objects
	private Map<GeographicPoint, MapNode> vertexList;
	
	// HashMap to store MapNodeAStar objects -> subclass of MapNode
	Map<GeographicPoint, MapNodeAStar> vertexListAStar = new HashMap<GeographicPoint, MapNodeAStar>();
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// Constructor for this class
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<GeographicPoint, ArrayList<EdgeData>>();
		vertexList = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return adjListsMap.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// Check for null input
		if (location == null) return false;
		
		// Check if location is already contained in the graph
		if (adjListsMap.containsKey(location)) return false;
		
		adjListsMap.put(location, new ArrayList<EdgeData>());
		vertexList.put(location, new MapNode(location));
		numVertices++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		// Check all boundary conditions are satisfied
		if (!adjListsMap.containsKey(from) || !adjListsMap.containsKey(to)
				|| roadName == null || roadType == null || length < 0){
			throw new IllegalArgumentException();
		}
		
		EdgeData edge = new EdgeData(to, roadName, roadType, length);
		
		adjListsMap.get(from).add(edge);
		numEdges++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighed)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighed)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Initialize data structures
		List<GeographicPoint> queue = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap
				<GeographicPoint, GeographicPoint>();
		
		
		// Implementation of the bfs algorithm
		queue.add(start);
		visited.add(start);
		
		while(!queue.isEmpty()){
			GeographicPoint curr = queue.remove(0);
			
			// Hook for visualization.
			nodeSearched.accept(curr);
			
			if(curr.equals(goal)){
				List<GeographicPoint> shortestPath = new ArrayList<GeographicPoint>();
				shortestPath.add(curr);
				
				GeographicPoint child = parentMap.get(curr);
				
				while(!child.equals(start)){
					shortestPath.add(child);
					child = parentMap.get(child);
				}
				shortestPath.add(start);
				Collections.reverse(shortestPath);
				return shortestPath;
			}
			
			for(EdgeData n : adjListsMap.get(curr)){
				if (!visited.contains(n.getGeoPoint())){
					visited.add(n.getGeoPoint());
					parentMap.put(n.getGeoPoint(), curr);
					queue.add(n.getGeoPoint());
				}
			}
		}
		// The path from start to goal does not exist
		return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Initialize data structures
		Queue<MapNode> queue = new PriorityQueue<MapNode>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap
				<GeographicPoint, GeographicPoint>();
		
		// Make all CurrentDistances of the MapNode objects to infinity
		for(MapNode mapNode: vertexList.values()){
			mapNode.setCurrentDistance(Double.POSITIVE_INFINITY);
		}
		
		// Implementation of the Dijkstra's algorithm
		queue.add(new MapNode(start, 0));
		
		while(!queue.isEmpty()){
			MapNode curr = queue.remove();
			
			// Hook for visualization.
			nodeSearched.accept(curr.getGeoPoint());
			
			if(!visited.contains(curr.getGeoPoint())){
				visited.add(curr.getGeoPoint());
			
				if(curr.getGeoPoint().equals(goal)){
					List<GeographicPoint> shortestPath = new ArrayList<GeographicPoint>();
					shortestPath.add(curr.getGeoPoint());
					
					GeographicPoint child = parentMap.get(curr.getGeoPoint());
					
					while(!child.equals(start)){
						shortestPath.add(child);
						child = parentMap.get(child);
					}
					shortestPath.add(start);
					Collections.reverse(shortestPath);
	
					return shortestPath;
				}

				for(EdgeData n : adjListsMap.get(curr.getGeoPoint())){
					
					if (!visited.contains(n.getGeoPoint())){										
						if(curr.getCurrentDistance() + n.getRoadLength() < 
								vertexList.get(n.getGeoPoint()).getCurrentDistance()){
							
							vertexList.get(n.getGeoPoint()).setCurrentDistance(n.getRoadLength() 
									+ curr.getCurrentDistance());
							parentMap.put(vertexList.get(n.getGeoPoint()).getGeoPoint(), curr.getGeoPoint());
							queue.add(vertexList.get(n.getGeoPoint()));
						}
					}
				}
			}
		}
		// The path from start to goal does not exist		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{		
		// Initialize data structures
		Queue<MapNodeAStar> queue = new PriorityQueue<MapNodeAStar>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap
				<GeographicPoint, GeographicPoint>();
		
		
		
		// Copy all vertexList objects to vertexListAStar
		for(GeographicPoint geoPoint: vertexList.keySet()){
			vertexListAStar.put(geoPoint, new MapNodeAStar(geoPoint, 
					Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		}
		
		// Make all CurrentDistances of the MapNodeAStar objects to infinity
		for(MapNodeAStar mapNode: vertexListAStar.values()){
			mapNode.setCurrentDistance(Double.POSITIVE_INFINITY);
		}
		
		// Implementation of the A-Star algorithm
		queue.add(new MapNodeAStar(start, 0, 0));
		
		while(!queue.isEmpty()){
			MapNodeAStar curr = queue.remove();
			
			
			// Hook for visualization.
			nodeSearched.accept(curr.getGeoPoint());
			
			if(!visited.contains(curr.getGeoPoint())){
				
				visited.add(curr.getGeoPoint());
			
				if(curr.getGeoPoint().equals(goal)){
					List<GeographicPoint> shortestPath = new ArrayList<GeographicPoint>();
					shortestPath.add(curr.getGeoPoint());
					
					GeographicPoint child = parentMap.get(curr.getGeoPoint());
					
					while(!child.equals(start)){
						shortestPath.add(child);
						child = parentMap.get(child);
					}
					shortestPath.add(start);
					Collections.reverse(shortestPath);
	
					return shortestPath;
				}
				
				for(EdgeData n : adjListsMap.get(curr.getGeoPoint())){
					
					if (!visited.contains(n.getGeoPoint())){
						
						if(curr.getPredictedDistance() + n.getRoadLength() < 
								vertexListAStar.get(n.getGeoPoint()).getPredictedDistance()){
							
							vertexListAStar.get(n.getGeoPoint()).setCurrentDistance(n.getRoadLength() 
									+ curr.getCurrentDistance());
							vertexListAStar.get(n.getGeoPoint()).setPredictedDistance(goal);
							parentMap.put(vertexListAStar.get(n.getGeoPoint()).getGeoPoint(), curr.getGeoPoint());
							queue.add(vertexListAStar.get(n.getGeoPoint()));
						}
					}
				}
			}
		}
		// The path from start to goal does not exist		
		return null;
	}
	
	/** Takes a set of vertices and returns a route (not necessarily the best) which visits 
	 * each vertex and returns back to the start. This method uses the Greedy Algorithm.
	 * 
	 * @param nodes the list of nodes to traverse with the first one being the starting node
	 * @return the list of nodes that forms a path through the given vertices
	 *
	 */
	public List<GeographicPoint> greedyAlgorithm(GeographicPoint start, List<GeographicPoint> nodes){
		
		// if the start node is contained in the nodes remove it 
		// (since we already visits the start node at the beginning)
		if (nodes.contains(start)){
			nodes.remove(start);
		}
		
		// the list to return after executing the greedy algorithm
		List<GeographicPoint> greedyList = new ArrayList<GeographicPoint>();
		GeographicPoint curr = start;
			
		// perform the Greedy algorithm
		while(!nodes.isEmpty()){

			GeographicPoint node = nodes.get(0);
			GeographicPoint minNode = node;
			aStarSearch(curr, node);
		    double minDistance  = vertexListAStar.get(node).getCurrentDistance();
		    
			for (int i = 0; i < nodes.size(); i++){
				
				node = nodes.get(i);
				aStarSearch(curr, node);
				double distance = vertexListAStar.get(node).getCurrentDistance(); 
			
				if(distance < minDistance){
					minNode = node;
					minDistance = distance;
				}

			}
			nodes.remove(minNode);
			greedyList.add(minNode);
			curr = minNode;
		}
		return greedyList;
	}
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		/*System.out.println(theMap.bfs(new GeographicPoint(1.0, 1.0), 
				new GeographicPoint(8.0, -1.0)));
		
		System.out.println(theMap.dijkstra(new GeographicPoint(1.0, 1.0), 
				new GeographicPoint(8.0, -1.0)));
		
		System.out.println(theMap.aStarSearch(new GeographicPoint(1.0, 1.0), 
				new GeographicPoint(8.0, -1.0)));*/
		
		GeographicPoint start = new GeographicPoint(4.0, 1.0);
		List<GeographicPoint> list = new ArrayList<GeographicPoint>();
		
		list.add(new GeographicPoint(4.0, -1.0));
		list.add(new GeographicPoint(4.0, 0.0));
		list.add(new GeographicPoint(8.0, -1.0));
		list.add(new GeographicPoint(5.0, 1.0));
		list.add(new GeographicPoint(6.5, 0.0));
		
		System.out.println(theMap.greedyAlgorithm(start, list));
		
		// Use this code in Week 3 End of Week Quiz
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);*/
		
	}
	
}
