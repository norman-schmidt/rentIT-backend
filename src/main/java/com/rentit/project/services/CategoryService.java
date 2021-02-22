package com.rentit.project.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.CategoryEntity;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ArticleService articleService;

	public CategoryEntity addCategory(CategoryEntity category) {
		return categoryRepository.save(category);
	}

	public CategoryEntity getCategory(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	public ResponseEntity<MessageResponse> deleteCategry(long id) {

		CategoryEntity category = getCategory(id);

		try {
			categoryRepository.delete(category);
			return ResponseEntity.ok().body(new MessageResponse("Successfully deleted"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Can't delete this category"));
		}

	}

	public CategoryEntity updateCategory(CategoryEntity categoryEntity, long id) {
		CategoryEntity _categoryEntity = getCategory(id);
		_categoryEntity = categoryEntity;
		return categoryRepository.save(_categoryEntity);
	}

	@Transactional
	public List<CustomArticle> getByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Transactional
	public HashMap<String, String> getAllName() {
		List<Object[]> categoryName = categoryRepository.findAllName();
		Map<String, String> results = new HashMap<String, String>();
		for (Object[] element : categoryName) {
			results.put((String) element[0], (String) element[1]);
		}
		// return name and description
		return (HashMap<String, String>) results;
	}

	public ResponseEntity<MessageResponse> addArticleToCategory(long id_category,
			Map<String, ArrayList<Long>> id_article) {
		ArrayList<Long> ids = id_article.get("ids");
		CategoryEntity category = getCategory(id_category);

		for (long id : ids) {
			ArticleEntity article = articleService.getArticle(id);
			article.setCategory(category);
			articleService.addArticle(article);
		}
		return ResponseEntity.ok().body(new MessageResponse("Successfully added"));
	}

	public ResponseEntity<MessageResponse> updateCategoryElement(long id, Map<String, Object> categoryEntity) {
		CategoryEntity _categoryEntity = getCategory(id);

		categoryEntity.forEach((element, value) -> {
			switch (element) {
			case "name":
				_categoryEntity.setName((String) value);
				break;
			case "description":
				_categoryEntity.setDescription((String) value);
				break;
			}
		});

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(_categoryEntity);// , OnUpdate.class);

		if (!violations.isEmpty()) {
			// When invalid
			return ResponseEntity.badRequest().body(new MessageResponse(violations.toString()));
		}

		addCategory(_categoryEntity);
		return ResponseEntity.ok().body(new MessageResponse("Successfully updated"));
	}

}
