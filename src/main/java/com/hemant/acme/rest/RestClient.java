package com.hemant.acme.rest;

import java.util.Date;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hemant.acme.dao.JourneyDAOImpl;
import com.hemant.acme.model.Journey;
import com.hemant.acme.model.Movement;
import com.hemant.acme.model.Truck;

/**
 * Mock implementation of the restclient which will be used
 * to get the GPS data from the server
 * @author hemant
 *
 */
@Component
public class RestClient {
	private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

	@Autowired
	private JourneyDAOImpl journeyDAO;
	
	
	public Movement getMovementDetails(Truck truck) {
		// only get movement data of trucks which are in some 
		// designated journey
		if(truck.getCurrentJourneyId() == null) {
			LOG.info("Truck :{} has no active journey, so not calling API", truck);
			return null;
		}
		
		return getMovementDetailsFromAPI(truck);
		
	}

	/**
	 * Since this is not an actual API,
	 * so in order to generate some data close to journey coordinates,
	 * this method, takes the journey and chooses a random point
	 * and then adds some random offset so that truck location is around that point
	 * @param truck
	 * @return
	 */
	private Movement getMovementDetailsFromAPI(Truck truck) {
		LOG.info("fetching movement data of truck :{}", truck);
		Journey currentJourney = journeyDAO.getJourney(truck.getCurrentJourneyId());
		
		Movement movement = new Movement();
		movement.setTruckId(truck.getId());
		movement.setCreatedOn(new Date());
		movement.setLocation(currentJourney.getRandomPointInJourney());
		movement.setId(new ObjectId().toString());
		return movement;
	}
	
	
}
