package com.rentit.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.CategoryEntity;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryEntity addCategory(CategoryEntity category) {
		return categoryRepository.save(category);
	}

	public CategoryEntity getCategory(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	public void deleteCategry(Long id) {
		CategoryEntity category = getCategory(id);
		categoryRepository.delete(category);
	}

	public CategoryEntity updateCategory(CategoryEntity category) {
		return categoryRepository.save(category);
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
		return (HashMap<String, String>) results;
	}

}
