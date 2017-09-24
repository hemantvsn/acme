package com.hemant.acme.util;

import org.springframework.data.geo.Point;

/**
 * http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
 * https://stackoverflow.com/questions/4438244/how-to-calculate-shortest-2d-distance-between-a-point-and-a-line-segment-in-all
 * 
 * Assumption : Lat/Lon are spherical coordinates
 * But assuming them as normal cartesian ones for use case
 * 
 * @author hemant
 */
public class DistanceUtils {
	/**
	 * 	Latitude is the Y axis, longitude is the X axis.
	 * @param geoPoint
	 * @return
	 */
	public static Point getXYPointFromGeoJsonPoint(com.hemant.acme.model.geojson.Point geoPoint) {
		double x = geoPoint.getCoordinates()[0];
		double y = geoPoint.getCoordinates()[1];
		Point p = new Point(x, y);
		return p;
	}

	/**
	 * Slope of the line will be same y2-y1/x2-x1 = y3-y1/x3-x1
	 * 
	 * @param A
	 * @param B
	 * @param X
	 * @return
	 */
	public static boolean doesPointPLiesOnLineAB(Point A, Point B, Point P) {
		double slopeAB = (A.getY() - B.getY()) / (A.getX() - B.getX());
		double slopeAP = (A.getY() - P.getY()) / (A.getX() - P.getX());

		/*
		 * compare 2 doubles
		 * https://stackoverflow.com/questions/8081827/how-to-compare-two-double-values-in-java
		 */
		return (Math.abs(slopeAB - slopeAP) <= 0.000001) ? true : false;
	}

	/**
	 * http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
	 * 
	 * @param A
	 * @param B
	 * @param P
	 * @return
	 */
	public static double pointToLineDistance(Point A, Point B, Point P) {
		double normalLength = Math.sqrt((B.getX() - A.getX()) * (B.getX() - A.getX()) + (B.getY() - A.getY()) * (B.getY() - A.getY()));
		return Math.abs((P.getX() - A.getX()) * (B.getY() - A.getY()) - (P.getY() - A.getY()) * (B.getX() - A.getX())) / normalLength;
	}

}
