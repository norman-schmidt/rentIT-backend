package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.PropertiesService;

@RestController
@RequestMapping("/api/properties")
public class PropertiesController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private PropertiesService propertiesService;

	@GetMapping("all")
	public List<PropertiesEntity> getAllProperties() {
		return propertiesService.getAllPropertiess();
	}

	@GetMapping("{id}")
	public PropertiesEntity getPropertiesById(@PathVariable long id) {
		return propertiesService.getProperties(id);
	}

	@PostMapping("add")
	public PropertiesEntity addProperties(@RequestBody PropertiesEntity propertiesEntity) {
		return propertiesService.addProperties(propertiesEntity);
	}

	@PutMapping("update/{id}")
	public PropertiesEntity updateProperties(@RequestBody PropertiesEntity propertiesEntity, @PathVariable long id) {

		PropertiesEntity _propertiesEntity = propertiesService.getProperties(id);

		_propertiesEntity.setStorage(propertiesEntity.getStorage());
		_propertiesEntity.setOperatingSystem(propertiesEntity.getOperatingSystem());
		_propertiesEntity.setColor(propertiesEntity.getColor());
		_propertiesEntity.setSpecialFeature(propertiesEntity.getSpecialFeature());
		_propertiesEntity.setManifacturer(propertiesEntity.getManifacturer());
		_propertiesEntity.setArticle(articleService.updateArticle(_propertiesEntity.getArticle()));

		return propertiesService.updateProperties(_propertiesEntity);
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Map<String, Boolean>> removeProperties(@PathVariable Long id) {

		propertiesService.deleteProperties(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("update/{ip_property}/article/{id_article}")
	public PropertiesEntity setPropertyArticle(@PathVariable long ip_property, @PathVariable long id_article) {
		ArticleEntity art = articleService.getArticle(id_article);
		PropertiesEntity ent = propertiesService.getProperties(ip_property);
		art.setPropreties(ent);
		ent.setArticle(art);
		articleService.updateArticle(art);
		propertiesService.updateProperties(ent);
		return ent;
	}

}
