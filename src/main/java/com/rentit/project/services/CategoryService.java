package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.CategoryEntity;
import com.rentit.project.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public CategoryEntity addCategry(CategoryEntity category) {
		return categoryRepository.save(category);
	}

	public CategoryEntity getCategry(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<CategoryEntity> getAllCategrys() {
		return categoryRepository.findAll();
	}

	public void deleteCategry(Long id) {
		CategoryEntity category = getCategry(id);
		categoryRepository.delete(category);
	}

	public CategoryEntity updateCategry(CategoryEntity category) {
		return categoryRepository.save(category);
	}

}
