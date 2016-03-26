package roadgraph;

import geography.GeographicPoint;

/**
 * @author Sudharaka Palamakumbura
 * A class that represents a vertex for the A-Star algorithm. 
 * This class extends the MapNode class which is defined for
 * general vertices of the MapGraph.
 * 
 */
public class MapNodeAStar extends MapNode{
	
	private double predictedDistance;
	
	public MapNodeAStar(GeographicPoint geoPoint){
		this.setGeoPoint(geoPoint);
	}
	
	public MapNodeAStar(GeographicPoint geoPoint, double currentDistance, double predictedDistance){
		this.setGeoPoint(geoPoint);
		this.setCurrentDistance(currentDistance);
		this.predictedDistance = predictedDistance;
	}

	@Override
	public int compareTo(MapNode mapNode) {
		return Double.compare(this.predictedDistance, ((MapNodeAStar)mapNode).predictedDistance);
	}
	
	/**
	 * @return the predictedDistance
	 */
	public double getPredictedDistance() {
		return predictedDistance;
	}

	/**
	 * @param mapNode the MapNodeAStar object
	 * that the Predicted Distance is associated with
	 */
	public void setPredictedDistance(GeographicPoint start) {
		this.predictedDistance = this.getCurrentDistance()
				+ this.getGeoPoint().distance(start);
	}
}
