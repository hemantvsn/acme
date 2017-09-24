package com.hemant.acme.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.hemant.acme.model.Journey;

@Repository
public class JourneyDAOImpl {
	
	@Autowired
	@Qualifier(value = "acmeMongoTemplate") 
	private MongoTemplate acmeMongoTemplate;
	
	public Journey getJourney(String id) {
		return acmeMongoTemplate.findById(id, Journey.class);
	}
}
