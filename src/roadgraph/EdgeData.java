package roadgraph;

import geography.GeographicPoint;

/**
 * @author Sudharaka
 * 
 * A class that represents an edge along with its end-point vertex,
 * road name, road type and length
 * 
 */
public class EdgeData {
	
	private GeographicPoint geoPoint;
	private String roadName;
	private String roadType;
	private double roadLength;
	
	/**
	 * Create a new EdgeData object
	 * 
	 * @param geoPoint The MapNode associated with the edge
	 * @param roadName The road name associated with the edge
	 * @param roadType The road type associated with the edge
	 * @param roadLength The road length associated with the edge
	 */
	public EdgeData(GeographicPoint geoPoint, String roadName, 
			String roadType, double roadLength){
		this.setGeoPoint(geoPoint);
		this.roadName = roadName;
		this.roadType = roadType;
		this.setRoadLength(roadLength);		
	}

	/**
	 * @return the roadLength
	 */
	public double getRoadLength() {
		return roadLength;
	}

	/**
	 * @param roadLength the roadLength to set
	 */
	public void setRoadLength(double roadLength) {
		this.roadLength = roadLength;
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
