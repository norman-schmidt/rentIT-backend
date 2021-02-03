package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.pojos.CustomPojoReturn;
import com.rentit.project.repositories.ArticleQuantityRepository;

@Service
public class ArticleQuantityService {

	@Autowired
	private ArticleQuantityRepository articleQuantityRepository;

	public ArticleQuantityEntity addArticleQuantity(ArticleQuantityEntity articleQuantity) {
		return articleQuantityRepository.save(articleQuantity);
	}

	public ArticleQuantityEntity getArticleQuantity(Long id) {
		return articleQuantityRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<ArticleQuantityEntity> getAllArticleQuantitys() {
		return articleQuantityRepository.findAll();
	}

	public void deleteArticleQuantity(Long id) {
		ArticleQuantityEntity articleQuantity = getArticleQuantity(id);
		articleQuantityRepository.delete(articleQuantity);
	}

	public ArticleQuantityEntity updateArticleQuantities(ArticleQuantityEntity list) {
		return articleQuantityRepository.save(list);
	}

	public List<ArticleQuantityEntity> updateArticleQuantities(List<ArticleQuantityEntity> list) {
		return articleQuantityRepository.saveAll(list);
	}

	@Transactional
	public List<CustomPojoReturn> getListRentalUser(Long userId) {
		return articleQuantityRepository.getListRentalUser(userId);
	}

}
