package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.services.PropertiesService;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertiesController {

	@Autowired
	private PropertiesService propertiesService;

	@GetMapping("")
	public List<PropertiesEntity> getAllProperties() {
		return propertiesService.getAllPropertiess();
	}

	@GetMapping("{id}")
	public PropertiesEntity getPropertiesById(@PathVariable long id) {
		return propertiesService.getProperties(id);
	}

	@PostMapping("")
	public PropertiesEntity addProperties(@RequestBody PropertiesEntity propertiesEntity) {
		return propertiesService.addProperties(propertiesEntity);
	}

	@PutMapping("{id}")
	public PropertiesEntity updateProperties(@RequestBody PropertiesEntity propertiesEntity, @PathVariable long id) {
		return propertiesService.updateProperties(propertiesEntity, id);

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeProperties(@PathVariable Long id) {
		propertiesService.deleteProperties(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_property}/article/{id_article}")
	public PropertiesEntity setPropertyArticle(@PathVariable long id_property, @PathVariable long id_article) {
		return propertiesService.setPropertyArticle(id_property, id_article);

	}

}
