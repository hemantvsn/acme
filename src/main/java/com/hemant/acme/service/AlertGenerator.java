package com.hemant.acme.service;

import org.springframework.stereotype.Service;

import com.hemant.acme.model.Movement;
import com.hemant.acme.model.Truck;

/**
 * This just prints to console
 * @author hemant
 *
 */
@Service
public class AlertGenerator {

	public void generateAlert(Movement pointInTimeData, Truck truck, double distanceFromRoute) {
		System.out.println("==================================================");
		System.out.println("Truck :" + truck + " has deviated from its route");
		System.out.println("Current location of the truck is :" + pointInTimeData.getLocation());
		System.out.println("The distance of the truck from its nearest route is :" + distanceFromRoute + " KMS");
		System.out.println("==================================================");
	}
}
