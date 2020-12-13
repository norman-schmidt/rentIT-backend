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
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public List<CategoryEntity> getAllCategory() {
		return categoryService.getAllCategories();
	}

	@GetMapping("{id}")
	public CategoryEntity getCategoryById(@PathVariable long id) {
		return categoryService.getCategory(id);
	}

	@PostMapping("")
	public CategoryEntity addCategory(@RequestBody CategoryEntity categoryEntity) {
		return categoryService.addCategory(categoryEntity);
	}

	@PutMapping("{id}")
	public CategoryEntity updateCategory(@RequestBody CategoryEntity categoryEntity, @PathVariable long id) {

		CategoryEntity _categoryEntity = categoryService.getCategory(id);

		_categoryEntity.setName(categoryEntity.getName());
		_categoryEntity.setArticles(articleService.updateArticle(_categoryEntity.getArticles()));

		return categoryService.updateCategory(_categoryEntity);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeCategory(@PathVariable Long id) {

		categoryService.deleteCategry(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_category}/article/{id_article}/add")
	public CategoryEntity addCategoryImage(@PathVariable long id_category, @PathVariable long id_article) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		cat.getArticles().add(art);
		art.setCategory(cat);
		articleService.updateArticle(art);
		categoryService.updateCategory(cat);
		return cat;
	}

	@PutMapping("{id_category}/article/{id_article}/remove")
	public CategoryEntity removeCategoryImage(@PathVariable long id_category, @PathVariable long id_article) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		cat.getArticles().remove(art);
		art.setCategory(null);
		articleService.updateArticle(art);
		categoryService.updateCategory(cat);
		return cat;
	}

}
