package com.rentit.project.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.pojo.query.ArticleAvailable;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.services.ArticleService;

@RestController
@RequestMapping("/api/articles/")
@CrossOrigin(origins = "*")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("")
	public List<ArticleEntity> getAllArticle() {
		return articleService.getAllArticles();
	}

	@GetMapping("{id}")
	public ArticleEntity getArticleById(@PathVariable long id) {
		return articleService.getArticle(id);
	}

	// findByName
	@GetMapping("name/{name}")
	public List<CustomArticle> getArticleById(@PathVariable("name") String name) {
		return articleService.getByName(name);
	}

	// findByName
	@GetMapping("{name}/{min}/{max}")
	public List<CustomArticle> filterMinMaxPrice(@PathVariable("name") String name, @PathVariable("min") double min,
			@PathVariable("max") double max) {
		return articleService.filterNamePrice(name, min, max);
	}

	// search article
	@GetMapping("search")
	public List<CustomArticle> filterWithNameCategoryPrice(@RequestParam("name") String name,
			@RequestParam("category") String category, @RequestParam("minPrice") double minPrice,
			@RequestParam("maxPrice") double maxPrice) {
		return articleService.filterArtWithNameCategoryPrice(name, category, minPrice, maxPrice);
	}

	@GetMapping("availableQuantity")
	public ArticleAvailable availableArticleQuantity(@RequestParam("id") Long id, @RequestParam("month") int month) {
		return articleService.availableArticleQuantity(id, month);
	}

	@PostMapping("")
	public ResponseEntity<MessageResponse> addArticle(@RequestBody ArticleEntity articleEntity) {
		return articleService.addArticle(articleEntity);
	}

	// findByIDS
	@PostMapping("articlesByIds/")
	public List<CustomArticle> getArticleByIds(@RequestBody Map<String, ArrayList<Long>> data) {
		return articleService.getByIds(data);
	}

	@PutMapping("{id}")
	public ArticleEntity updateArticle(@RequestBody ArticleEntity articleEntity, @PathVariable long id) {

		return articleService.updateArticle(articleEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeArticle(@PathVariable Long id) {
		articleService.deleteArticle(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_article}/property/{id_property}")
	public ArticleEntity setArticleProperty(@PathVariable long id_article, @PathVariable long id_property) {
		return articleService.setArticleProperty(id_article, id_property);
	}

	@PutMapping("{id_article}/category/{id_category}/add")
	public ArticleEntity addArticleCategory(@PathVariable long id_article, @PathVariable long id_category) {
		return articleService.addArticleCategory(id_article, id_category);
	}

	@PutMapping("{id_article}/category/{id_category}/remove")
	public ArticleEntity removeArticleCategory(@PathVariable long id_article, @PathVariable long id_category) {
		return articleService.removeArticleCategory(id_article, id_category);
	}

	@PutMapping("{id_article}/image/{id_image}/add")
	public ArticleEntity addArticleImage(@PathVariable long id_article, @PathVariable long id_image) {
		return articleService.addArticleImage(id_article, id_image);
	}

	@PutMapping("{id_article}/image/{id_image}/remove")
	public ArticleEntity removeArticleImage(@PathVariable long id_article, @PathVariable long id_image) {
		return articleService.removeArticleImage(id_article, id_image);
	}

}
