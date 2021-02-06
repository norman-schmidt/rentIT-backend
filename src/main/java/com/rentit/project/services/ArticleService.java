package com.rentit.project.services;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.CategoryEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.pojo.query.ArticleAvailable;
import com.rentit.project.pojo.query.CustomAAvailableQuantity;
import com.rentit.project.pojo.query.CustomArticle;
import com.rentit.project.pojo.response.MessageResponse;
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
	private ImageService imageService;
	
	@Autowired
	private ArticleQuantityService articleQuantityService;

	public ResponseEntity<MessageResponse> addArticle(ArticleEntity articleEntity) {
		// CategoryEntity
		CategoryEntity category = categoryService.getCategory(articleEntity.getCategory().getCategoryId());
		articleEntity.setCategory(category);

		// add article with the images (without article in image)
		articleRepository.save(articleEntity);

		// get id of new article and set in help article obj
		ArticleEntity articleEntity_ = new ArticleEntity();
		articleEntity_.setArticleId(articleEntity.getArticleId());

		// update images with the id of article
		for (ImageEntity im : articleEntity.getImages()) {
			im.setArt(articleEntity_);
			imageService.addImage(im);
		}

		return ResponseEntity.ok().body(new MessageResponse("Successfully Added"));

	}

	public ArticleEntity getArticle(Long id) {
		return articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public List<CustomArticle> getByIds(Map<String, ArrayList<Long>> data) {
		ArrayList<Long> ids = data.get("ids");
		List<CustomArticle> articles = new ArrayList<CustomArticle>();
		for (Long id : ids) {
			articles.add(articleRepository.findByIds((Long) id));
		}
		return articles;
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
	public List<CustomArticle> filterArtWithNameCategoryPrice(String name, String category, double min, double max) {

		List<CustomArticle> articles = new ArrayList<CustomArticle>();
		if (category.isEmpty()) {
			category = "_";
		}
		articles.addAll(articleRepository.filterWithNameCategoryPrice(name, category, min, max));
		return articles;
	}

	@Transactional
	public CustomAAvailableQuantity getAAvailabityQuantity(Long id, LocalDateTime dateBegin, LocalDateTime dateEnd) {
		return articleRepository.getAvailabityQuantity(id, dateBegin, dateEnd);
	}

	public ArticleAvailable availableArticleQuantity(Long id, int month) {
		ArticleAvailable articleAvailable = new ArticleAvailable();
		articleAvailable.setArticelId(id);
		ArrayList<Long> listQuanntity = new ArrayList<>();

		List<CustomAAvailableQuantity> CustomAAvailableQuantity = new ArrayList<CustomAAvailableQuantity>();

		int daysInMonth = YearMonth.of(LocalDateTime.now().getYear(), month).lengthOfMonth();

		int i = 1;
		// ab heute
		// if (month == LocalDateTime.now().getMonthValue()) {
		// i = LocalDateTime.now().getDayOfMonth();
		// }

		while (i <= daysInMonth) {
			CustomAAvailableQuantity caq = new CustomAAvailableQuantity();
			// von heute bis Ende des Monats
			caq = getAAvailabityQuantity(id, LocalDateTime.now(),
					LocalDateTime.of(LocalDateTime.now().getYear(), month, (i), 23, 59));
			// wenn noch keinen Ausleih gemacht wurde getStockLevel
			if (caq == null) {
				caq = new CustomAAvailableQuantity();
				caq.setArticelId(id);
				caq.setAvailable((long) getArticle(id).getStockLevel());
			}
			listQuanntity.add(caq.getAvailable());
			CustomAAvailableQuantity.add(caq);
			i++;
		}
		articleAvailable.setAvailable(listQuanntity.toArray(new Long[listQuanntity.size()]));
		return articleAvailable;
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
				(articleQuantityService.updateArticleQuantities(_articleEntity.getArticleQuantity())));
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
		imageService.updateImage(image, id_image);
		return art;
	}

	public ArticleEntity removeArticleImage(long id_article, long id_image) {
		ArticleEntity art = articleService.getArticle(id_article);
		ImageEntity image = imageService.getImage(id_image);
		art.getImages().remove(image);
		image.setArt(null);
		articleService.updateArticle(art, id_article);
		imageService.updateImage(image, id_image);
		return art;
	}

}
