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
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.CategoryService;
import com.rentit.project.services.PropertiesService;
import com.rentit.project.services.RentalService;

@RestController
@RequestMapping("/api/articles/")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private RentalService rentalService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PropertiesService propertiesService;

	@GetMapping("all")
	public List<ArticleEntity> getAllArticle() {
		return articleService.getAllArticles();
	}

	@GetMapping("{id}")
	public ArticleEntity getArticleById(@PathVariable long id) {
		return articleService.getArticle(id);
	}

	@PostMapping("add")
	public ArticleEntity addArticle(@RequestBody ArticleEntity articleEntity) {
		return articleService.addArticle(articleEntity);
	}

	@PutMapping("update/{id}")
	public ArticleEntity updateArticle(@RequestBody ArticleEntity articleEntity, @PathVariable long id) {

		ArticleEntity _articleEntity = articleService.getArticle(id);

		_articleEntity.setName(articleEntity.getName());
		_articleEntity.setSerialNumber(articleEntity.getSerialNumber());
		_articleEntity.setModel(articleEntity.getModel());
		_articleEntity.setStockLevel(articleEntity.getStockLevel());
		_articleEntity.setPrice(articleEntity.getPrice());
		_articleEntity.setPropreties(propertiesService.updateProperties(_articleEntity.getPropreties()));
		_articleEntity.setRental(rentalService.updateRental(_articleEntity.getRental()));
		_articleEntity.setCategory(categoryService.updateCategory(_articleEntity.getCategory()));
		_articleEntity.setImages(articleEntity.getImages());

		return articleService.updateArticle(_articleEntity);
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Map<String, Boolean>> removeArticle(@PathVariable Long id) {

		articleService.deleteArticle(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
