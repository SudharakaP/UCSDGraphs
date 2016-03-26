package roadgraph;

import geography.GeographicPoint;

/**
 * @author Sudharaka Palamakumbura
 * A class that represents a vertex along with its associated data
 * 
 */
public class MapNode implements Comparable<MapNode>{
	
	private GeographicPoint geoPoint;
	private double currentDistance;
	
	public MapNode(){}
	
	public MapNode(GeographicPoint geoPoint){
		this.setGeoPoint(geoPoint);
	}
	
	public MapNode(GeographicPoint geoPoint, double currentDistance){
		this.setGeoPoint(geoPoint);
		this.currentDistance = currentDistance;
	}
	
	/**
	 * @return the currentDistance
	 */
	public double getCurrentDistance() {
		return currentDistance;
	}
	
	/**
	 * @param currentDistance the currentDistance to set
	 */
	public void setCurrentDistance(double currentDistance) {
		this.currentDistance = currentDistance;
	}

	@Override
	public int compareTo(MapNode mapNode) {
		return Double.compare(this.currentDistance, mapNode.currentDistance);
	}

	/**
	 * @return the geoPoint
	 */
	public GeographicPoint getGeoPoint() {
		return geoPoint;
	}

	/**
	 * @param geoPoint the geoPoint to set
	 */
	public void setGeoPoint(GeographicPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
}
