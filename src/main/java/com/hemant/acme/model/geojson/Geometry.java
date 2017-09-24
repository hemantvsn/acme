package com.hemant.acme.model.geojson;

/**
 * https://github.com/MPriess/GeoJSON-POJO/blob/master/src/main/java/org/geojson/geometry/Geometry.java
 * @author hemant
 *
 */

public abstract class Geometry {

	protected String type;

	public Geometry(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
