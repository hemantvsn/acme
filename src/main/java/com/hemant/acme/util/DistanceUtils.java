package com.hemant.acme.util;

import java.awt.Point;

/**
 * http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
 * https://stackoverflow.com/questions/4438244/how-to-calculate-shortest-2d-distance-between-a-point-and-a-line-segment-in-all
 * 
 * @author hemant
 *
 */
public class DistanceUtils {

	/**
	 * Slope of the line will be same y2-y1/x2-x1 = y3-y1/x3-x1
	 * 
	 * @param A
	 * @param B
	 * @param X
	 * @return
	 */
	public boolean doesPointPLiesOnLineAB(Point A, Point B, Point P) {
		double slopeAB = (A.y - B.y) / (A.x - B.x);
		double slopeAP = (A.y - P.y) / (A.x - P.x);

		/*
		 * compare 2 doubles
		 * https://stackoverflow.com/questions/8081827/how-to-compare-two-double-values-in-java
		 */
		return (Math.abs(slopeAB - slopeAP) <= 0.001) ? true : false;
	}

	/**
	 * http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
	 * 
	 * @param A
	 * @param B
	 * @param P
	 * @return
	 */
	public double pointToLineDistance(Point A, Point B, Point P) {
		double normalLength = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
		return Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength;
	}

}
