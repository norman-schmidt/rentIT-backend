package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;

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

import com.rentit.project.models.CategoryEntity;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

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

	// Return the Category and its articles
	@GetMapping("name/{name}")
	public List<CustomArticle> getCategoryByName(@PathVariable("name") String name) {
		return categoryService.getByName(name);
	}

	// List Name of Category
	@GetMapping("name")
	public HashMap<String, String> getAllCategoryName() {
		return categoryService.getAllName();
	}

	@PostMapping("")
	public CategoryEntity addCategory(@RequestBody CategoryEntity categoryEntity) {
		return categoryService.addCategory(categoryEntity);
	}

	@PutMapping("{id}")
	public CategoryEntity updateCategory(@RequestBody CategoryEntity categoryEntity, @PathVariable long id) {

		return categoryService.updateCategory(categoryEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<MessageResponse> removeCategory(@PathVariable Long id) {
		return categoryService.deleteCategry(id);
	}

	@PutMapping("{id_category}/article/{id_article}/add")
	public CategoryEntity addCategoryImage(@PathVariable long id_category, @PathVariable long id_article) {
		return categoryService.addCategoryImage(id_category, id_article);
	}

	@PutMapping("{id_category}/article/{id_article}/remove")
	public CategoryEntity removeCategoryImage(@PathVariable long id_category, @PathVariable long id_article) {
		return categoryService.removeCategoryImage(id_category, id_article);
	}

}
