package com.hemant.acme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Value("${mongodb.host}")
	private String mongoHost;
	@Value("${mongodb.database}")
	private String mongoDatabase;
	@Value("${mongodb.authentication-database}")
	private String mongoAuthDatabase;
	@Value("${mongodb.username}")
	private String mongoUsername;
	@Value("${mongodb.password}")
	private String mongoPassword;
	@Value("${mongodb.port}")
	private String mongoPort;

	@Bean(name = "acmeMongoTemplate")
	public MongoTemplate getMongoTemplate() {
		return new MongoTemplate(
				getMongoFactory(mongoUsername, mongoAuthDatabase, 
						mongoPassword, mongoHost, mongoPort, mongoDatabase));
	}

	public MongoDbFactory getMongoFactory(String username, String authenticationDatabase, String password, String host,
			String port, String connectingDatabase) {

		List<MongoCredential> credentialsList = new ArrayList<>();
		MongoCredential creds = MongoCredential.createCredential(username, authenticationDatabase,
				password.toCharArray());
		credentialsList.add(creds);
		ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
		return new SimpleMongoDbFactory(new MongoClient(serverAddress, credentialsList), connectingDatabase);
	}
}
