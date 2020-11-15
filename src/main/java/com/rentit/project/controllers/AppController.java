package com.rentit.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AppController {
	
	@GetMapping("/")
	public List<String> home() {
		List<String> el = new ArrayList();
		el.add("element 1");
		el.add("element 2");
		el.add("element 3");
		el.add("element 4");
		return el;
	}

}
