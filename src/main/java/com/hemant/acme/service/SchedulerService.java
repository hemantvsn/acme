package com.hemant.acme.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hemant.acme.dao.MovementDAOImpl;
import com.hemant.acme.dao.TruckDAOImpl;
import com.hemant.acme.model.Movement;
import com.hemant.acme.model.Truck;
import com.hemant.acme.rest.RestClient;

/**
 * Mock implementation of the RestClient
 * This will generate the point-in-time data for each truck in system
 * @author hemant
 *
 */
@Configuration
@EnableScheduling
@Service
public class SchedulerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);
	
	@Autowired
	private TruckDAOImpl truckDAO;
	
	@Autowired
	private MovementDAOImpl movementDAOImpl;
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private PointInTimeDataHandler pointInTimeDataHandler;
	
	

	/**
	 * 1. For each truck in system, this will query the restClient (Mock)
	 */
	//@Scheduled(cron = "0 0/1 * * * *")
	@Scheduled(fixedDelay=5000)
	public void getMovementData() {
		for(Truck truck : truckDAO.getAllTrucks()) {
			LOG.info("Fetching pointInTime data for truck :{}", truck);
			Movement pointInTimeData = restClient.getMovementDetails(truck);
			if(pointInTimeData == null) {
				continue;
			}
			processPointInTimeData(pointInTimeData, truck);
		}
		
	}

	/**
	 * 1. Save the data
	 * 2. Call handler so that it calculates the data and generates the alert
	 * based on evaluation
	 * @param pointInTimeData
	 * @param truck 
	 */
	private void processPointInTimeData(Movement pointInTimeData, Truck truck) {
		movementDAOImpl.savePointInTimeData(pointInTimeData);
		LOG.info("Saved pointIntime data in history table");
		pointInTimeDataHandler.handlePointInTimeData(pointInTimeData, truck);
	}
	
}
