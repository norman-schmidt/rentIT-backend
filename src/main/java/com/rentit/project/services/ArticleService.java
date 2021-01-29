package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.pojos.CustomArticle;
import com.rentit.project.repositories.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleEntity addArticle(ArticleEntity article) {
		return articleRepository.save(article);
	}

	public ArticleEntity getArticle(Long id) {
		return articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public CustomArticle getByIds(Long id) {
		return articleRepository.findByIds(id);
	}

	@Transactional
	public List<CustomArticle> getByName(String name) {
		return articleRepository.findByName(name);
	}

	@Transactional
	public List<CustomArticle> filterNamePrice(String name, double min, double max) {
		return articleRepository.filterWithNamePrice(name, min, max);
	}

	@Transactional
	public List<CustomArticle> filterWithNameCategoryPrice(String name, String category, double min, double max) {
		return articleRepository.filterWithNameCategoryPrice(name, category, min, max);
	}

	public List<ArticleEntity> getAllArticles() {
		return articleRepository.findAll();
	}

	public void deleteArticle(Long id) {
		ArticleEntity article = getArticle(id);
		articleRepository.delete(article);
	}

	public ArticleEntity updateArticle(ArticleEntity article) {
		return articleRepository.save(article);
	}

	public List<ArticleEntity> updateArticle(List<ArticleEntity> article) {
		return articleRepository.saveAll(article);
	}

}
