package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.dto.CustomArticle;
import com.rentit.project.models.ArticleEntity;
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
	public List<CustomArticle> getByName(String name) {
		return articleRepository.findByName(name);
	}

//	@Transactional
//	public List<ArticleEntity> getArticleByCategory(String category) {
//		return articleRepository.findByCategory(category);
//	}
	/*
	@Transactional
	public List<ArticleEntity> getByPriceAsc() {
		return articleRepository.PriceMinToMax();
	}

	@Transactional
	public List<ArticleEntity> getByPriceDesc() {
		return articleRepository.PriceMaxToMin();
	}

	@Transactional
	public List<ArticleEntity> getByPriceAscCategory() {
		return articleRepository.PriceMinToMaxCategory();
	}

	@Transactional
	public List<ArticleEntity> getByPriceDescCategory() {
		return articleRepository.PriceMaxToMinCategory();
	}*/

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
