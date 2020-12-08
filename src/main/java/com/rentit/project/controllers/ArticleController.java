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
import com.rentit.project.models.CategoryEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.models.RentalEntity;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.CategoryService;
import com.rentit.project.services.ImageService;
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

	@Autowired
	private ImageService imageService;

	@GetMapping("all")
	public List<ArticleEntity> getAllArticle() {
		return articleService.getAllArticles();
	}

	@GetMapping("{id}")
	public ArticleEntity getArticleById(@PathVariable long id) {
		return articleService.getArticle(id);
	}

	@PostMapping("")
	public ArticleEntity addArticle(@RequestBody ArticleEntity articleEntity) {
		return articleService.addArticle(articleEntity);
	}

	@PutMapping("{id}")
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
		_articleEntity.setImages(imageService.updateImage(_articleEntity.getImages()));

		return articleService.updateArticle(_articleEntity);
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
		ArticleEntity art = articleService.getArticle(id_article);
		PropertiesEntity ent = propertiesService.getProperties(id_property);
		art.setPropreties(ent);
		ent.setArticle(art);
		articleService.updateArticle(art);
		propertiesService.updateProperties(ent);
		return art;
	}

	@PutMapping("{id_article}/rental/{id_rental}/add")
	public ArticleEntity addArticleRental(@PathVariable long id_article, @PathVariable long id_rental) {
		ArticleEntity art = articleService.getArticle(id_article);
		RentalEntity rent = rentalService.getRental(id_rental);
		art.setRental(rent);
		rent.getArticles().add(art);
		articleService.updateArticle(art);
		rentalService.updateRental(rent);
		return art;
	}

	@PutMapping("{id_article}/category/{id_category}/add")
	public ArticleEntity addArticleCategory(@PathVariable long id_article, @PathVariable long id_category) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		cat.getArticles().add(art);
		art.setCategory(cat);
		articleService.updateArticle(art);
		categoryService.updateCategory(cat);
		return art;
	}

	@PutMapping("{id_article}/category/{id_category}/remove")
	public ArticleEntity removeArticleCategory(@PathVariable long id_article, @PathVariable long id_category) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		art.setCategory(cat);
		cat.getArticles().remove(art);
		articleService.updateArticle(art);
		categoryService.updateCategory(cat);
		return art;
	}

	@PutMapping("{id_article}/image/{id_image}/add")
	public ArticleEntity addArticleImage(@PathVariable long id_article, @PathVariable long id_image) {
		ArticleEntity art = articleService.getArticle(id_article);
		ImageEntity image = imageService.getImage(id_image);
		art.getImages().add(image);
		image.setArticle(art);
		articleService.updateArticle(art);
		imageService.updateImage(image);
		return art;
	}

	@PutMapping("{id_article}/image/{id_image}/remove")
	public ArticleEntity removeArticleImage(@PathVariable long id_article, @PathVariable long id_image) {
		ArticleEntity art = articleService.getArticle(id_article);
		ImageEntity image = imageService.getImage(id_image);
		art.getImages().remove(image);
		image.setArticle(null);
		articleService.updateArticle(art);
		imageService.updateImage(image);
		return art;
	}

}
