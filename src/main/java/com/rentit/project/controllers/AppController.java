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
		List<String> el = new ArrayList<String>();
		el.add("iPhone 12");
		el.add("Dell XPS 13");
		el.add("HP OfficeJet 6700");
		el.add("Samsung Galaxy Fold 2");
		return el;
	}

}
