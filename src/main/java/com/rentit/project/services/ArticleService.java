package com.rentit.project.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.CategoryEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.pojo.query.CustomAAvailableQuantity;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.repositories.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private ArticleQuantityService articleQuatityService;

	@Autowired
	private ImageService imageService;

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

	@Transactional
	public CustomAAvailableQuantity getAAvailabityQuantity(Long id, LocalDateTime dateBegin, LocalDateTime dateEnd) {
		return articleRepository.getAvailabityQuantity(id, dateBegin, dateEnd);
	}

	public List<ArticleEntity> getAllArticles() {
		return articleRepository.findAll();
	}

	public void deleteArticle(Long id) {
		ArticleEntity article = getArticle(id);
		articleRepository.delete(article);
	}

	public ArticleEntity updateArticle(ArticleEntity articleEntity, long id) {

		ArticleEntity _articleEntity = getArticle(id);
		_articleEntity.setName(articleEntity.getName());
		_articleEntity.setSerialNumber(articleEntity.getSerialNumber());
		_articleEntity.setModel(articleEntity.getModel());
		_articleEntity.setStockLevel(articleEntity.getStockLevel());
		_articleEntity.setPrice(articleEntity.getPrice());
		_articleEntity.setDescription(articleEntity.getDescription());
		_articleEntity.setProperties(propertiesService.updateProperties(_articleEntity.getProperties(),
				_articleEntity.getProperties().getPropertiesId()));
		_articleEntity.setArticleQuantity(
				(articleQuatityService.updateArticleQuantities(_articleEntity.getArticleQuantity())));
		_articleEntity.setCategory(categoryService.updateCategory(_articleEntity.getCategory(),
				_articleEntity.getCategory().getCategoryId()));
		_articleEntity.setImages(imageService.updateImage(_articleEntity.getImages()));

		return articleRepository.save(articleEntity);
	}

	public List<ArticleEntity> updateArticle(List<ArticleEntity> article) {
		return articleRepository.saveAll(article);
	}

	public ArticleEntity setArticleProperty(long id_article, long id_property) {
		ArticleEntity art = articleService.getArticle(id_article);
		PropertiesEntity ent = propertiesService.getProperties(id_property);
		art.setProperties(ent);
		ent.setArticle(art);
		updateArticle(art, id_article);
		propertiesService.updateProperties(ent, ent.getPropertiesId());
		return art;
	}

	public ArticleEntity addArticleCategory(long id_article, long id_category) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		cat.getArticles().add(art);
		art.setCategory(cat);
		articleService.updateArticle(art, id_article);
		categoryService.updateCategory(cat, cat.getCategoryId());
		return art;
	}

	public ArticleEntity removeArticleCategory(long id_article, long id_category) {
		ArticleEntity art = articleService.getArticle(id_article);
		CategoryEntity cat = categoryService.getCategory(id_category);
		art.setCategory(cat);
		cat.getArticles().remove(art);
		articleService.updateArticle(art, id_article);
		categoryService.updateCategory(cat, cat.getCategoryId());
		return art;
	}

	public ArticleEntity addArticleImage(long id_article, long id_image) {
		ArticleEntity art = articleService.getArticle(id_article);
		ImageEntity image = imageService.getImage(id_image);
		art.getImages().add(image);
		image.setArt(art);
		articleService.updateArticle(art, id_article);
		imageService.updateImage(image, image.getImageId());
		return art;
	}

	public ArticleEntity removeArticleImage(long id_article, long id_image) {
		ArticleEntity art = articleService.getArticle(id_article);
		ImageEntity image = imageService.getImage(id_image);
		art.getImages().remove(image);
		image.setArt(null);
		articleService.updateArticle(art, id_article);
		imageService.updateImage(image, image.getImageId());
		return art;
	}

}
