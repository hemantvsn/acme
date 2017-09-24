package com.hemant.acme.model.geojson;

/**
 * https://docs.mongodb.com/manual/reference/geojson/
 * 
 * @author hemant
 * 
 *         Embedded object as per Mongo geojson structure
 * 
 *         { type: "Point", coordinates: [ 40, 5 ] } { type: "LineString",
 *         coordinates: [ [ 40, 5 ], [ 41, 6 ] ] } { type: "Polygon",
 *         coordinates: [ [ [ 0 , 0 ] , [ 3 , 6 ] , [ 6 , 1 ] , [ 0 , 0 ] ] ]}
 */
public class Location {

	private GeoType type;
	private Double[] coordinates;

	public GeoType getType() {
		return type;
	}

	public void setType(GeoType type) {
		this.type = type;
	}

	public Double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}

}
