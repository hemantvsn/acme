package com.hemant.acme.service;

import static com.hemant.acme.util.DistanceUtils.doesPointPLiesOnLineAB;
import static com.hemant.acme.util.DistanceUtils.getXYPointFromGeoJsonPoint;
import static com.hemant.acme.util.DistanceUtils.pointToLineDistance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hemant.acme.dao.JourneyDAOImpl;
import com.hemant.acme.model.Journey;
import com.hemant.acme.model.Movement;
import com.hemant.acme.model.Truck;
import com.hemant.acme.model.geojson.Point;

/**
 * This can actually be a event listener
 * Or we can use AMQP and this will be amqp consumer
 * @author hemant
 *
 */
@Service
public class PointInTimeDataHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(PointInTimeDataHandler.class);
	// Each degree of latitude is approximately 69 miles (111 kilometers) 
	private static final double degreesToKms = 111;
	
	
	@Value("${min.distance.threshold}")
	private double minThreshold;	
	@Autowired
	private JourneyDAOImpl journeyDAO;
	@Autowired
	private AlertGenerator alertGenerator;
	
	
	
	
	/**
	 * Get the distance between point where the truck is located 
	 * and LineString of the current truck journey
	 * @param pointInTimeData
	 */
	public void handlePointInTimeData(Movement pointInTimeData, Truck truck) {
		double minDistance = getMinimumDistanceFromRoute(pointInTimeData, truck) * degreesToKms;
		LOG.info("The truck is adrift from the route by :{}", minDistance);
		if (minDistance > minThreshold) {
			alertGenerator.generateAlert(pointInTimeData, truck, minDistance);
		} else {
			LOG.info("No alert has to be generated");
		}
		
	}
	
	private double getMinimumDistanceFromRoute(Movement pointInTimeData, Truck truck) {
		LOG.info("Determining if pointInTime :{} data of truck violates threshold :{}", pointInTimeData, truck);
		Journey journey = journeyDAO.getJourney(truck.getCurrentJourneyId());
		final Point currentLoc = pointInTimeData.getLocation();
		/*
		 * Take 2 points at a time 
		 * 0,1
		 * 1,2
		 * 2,3
		 * .
		 * .
		 * n-1, n
		 */
		double minDistance = Double.POSITIVE_INFINITY;
		List<double[]> coordinates = journey.getLocation().getCoordinates();
		for (int i = 0; i < coordinates.size() -1 ; i++) {
			Point geoA = new Point();
			geoA.setCoordinates(coordinates.get(i));
			
			Point geoB = new Point();
			geoB.setCoordinates(coordinates.get(i+1));
			
			if (doesPointPLiesOnLineAB(getXYPointFromGeoJsonPoint(geoA), getXYPointFromGeoJsonPoint(geoB), getXYPointFromGeoJsonPoint(currentLoc))) {
				LOG.info("Point :{} lies on the lineSegment for the lineString defined in Route");
				return 0;
			}
			
			// Point  doesn't lie on any routeSegment. Hence calculate its distance
			// minimum distance is only reassigned, if calculation gets a lower value
			double calculated = pointToLineDistance(getXYPointFromGeoJsonPoint(geoA), getXYPointFromGeoJsonPoint(geoB), getXYPointFromGeoJsonPoint(currentLoc));
			minDistance = (calculated < minDistance) ? calculated : minDistance;
			LOG.info("Min distance of truck:{} from route is :{}. Current iteration is {}", truck, minDistance, i);
		}
		LOG.info("FINAL Min distance of truck:{} from route is :{}", truck, minDistance);
		return minDistance;
	
	}
}
