package com.hemant.acme.controllers;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/hello")
    public Map<String, String> home() {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			String x = Integer.toString(i);
			map.put(x, new ObjectId().toHexString());
		}
		return map;
	}
}
