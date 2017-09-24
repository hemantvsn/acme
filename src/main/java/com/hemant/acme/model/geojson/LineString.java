package com.hemant.acme.model.geojson;

/**
 * https://github.com/MPriess/GeoJSON-POJO/blob/master/src/main/java/org/geojson/geometry/Geometry.java
 * @author hemant
 *
 */

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("LineString")
public class LineString extends Geometry {

	private List<double[]> coordinates;

	public LineString() {
		super(LineString.class.getSimpleName());
	}

	public LineString(List<Point> coordinates) {
		this();
		if (coordinates != null) {
			this.coordinates = new ArrayList<>();

			for (Point point : coordinates) {
				this.coordinates.add(point.getCoordinates());
			}
		}

	}

	public List<double[]> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(List<double[]> coordinates) {
		this.coordinates = coordinates;
	}

}
