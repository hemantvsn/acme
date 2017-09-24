package com.hemant.acme.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.hemant.acme.model.Movement;

@Repository
public class MovementDAOImpl {
	
	@Autowired
	@Qualifier(value = "acmeMongoTemplate") 
	private MongoTemplate acmeMongoTemplate;
	
	public void savePointInTimeData(Movement pointInTimeData) {
		acmeMongoTemplate.save(pointInTimeData);
	}
}
