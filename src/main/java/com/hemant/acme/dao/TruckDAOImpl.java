package com.hemant.acme.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.hemant.acme.model.Truck;

@Repository
public class TruckDAOImpl {
	
	@Autowired
	@Qualifier(value = "acmeMongoTemplate") 
	private MongoTemplate acmeMongoTemplate;
	
	public List<Truck> getAllTrucks() {
		return acmeMongoTemplate.findAll(Truck.class);
	}
	
	public Truck getTruck(String id) {
		return acmeMongoTemplate.findById(id, Truck.class);
	}

}
