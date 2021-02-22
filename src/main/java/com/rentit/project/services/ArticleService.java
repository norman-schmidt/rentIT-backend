package com.rentit.project.services;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;
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
	private ImageService imageService;

	@Autowired
	private PropertiesService propertiesService;

	public ResponseEntity<MessageResponse> addArticle(ArticleEntity articleEntity) {
		// CategoryEntity
		if (articleEntity.getCategory() != null) {
			CategoryEntity category = categoryService.getCategory(articleEntity.getCategory().getCategoryId());
			articleEntity.setCategory(category);
		}

		// add article with the images (without article in image)
		articleRepository.save(articleEntity);

		// get id of new article and set in help variable article
		ArticleEntity articleEntity_ = new ArticleEntity();
		articleEntity_.setArticleId(articleEntity.getArticleId());

		// update images with the id of article
		if (articleEntity.getImages() != null) {
			for (ImageEntity im : articleEntity.getImages()) {
				im.setArt(articleEntity_);
				imageService.saveImage(im);
			}
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

		while (i <= daysInMonth) {
			CustomAAvailableQuantity caq = new CustomAAvailableQuantity();
			// bis Ende des Monats
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

	// everything will be overwritten
	public ArticleEntity updateArticle(ArticleEntity articleEntity, long id) {
		ArticleEntity _articleEntity = getArticle(id);
		_articleEntity = articleEntity;
		return articleRepository.save(_articleEntity);
	}

	// remove Foreign keys category // usage??
	public ResponseEntity<MessageResponse> removeCategory(long id_article) {
		ArticleEntity article = articleService.getArticle(id_article);
		article.setCategory(null);
		addArticle(article);
		return ResponseEntity.ok().body(new MessageResponse("Successfully removed"));
	}

	// patch article without image
	public ResponseEntity<MessageResponse> updateArticleElement(long id, Map<String, Object> articleEntity) {
		ArticleEntity _articleEntity = getArticle(id);
		ObjectMapper mapper = new ObjectMapper();

		articleEntity.forEach((element, value) -> {
			switch (element) {
			case "name":
				_articleEntity.setName((String) value);
				break;
			case "description":
				_articleEntity.setDescription((String) value);
				break;
			case "serialNumber":
				_articleEntity.setSerialNumber((String) value);
				break;
			case "model":
				_articleEntity.setModel((String) value);
				break;
			case "stockLevel":
				_articleEntity.setStockLevel((Integer) value);
				break;
			case "price":
				_articleEntity.setPrice((Double) value);
				break;
			case "category":
				CategoryEntity category = mapper.convertValue(value, CategoryEntity.class);
				_articleEntity.setCategory(category);
				break;
			case "properties":
				PropertiesEntity properties = mapper.convertValue(value, PropertiesEntity.class);
				if (properties.getPropertiesId() == 0) {// properties must be complete
					_articleEntity.setProperties(properties);
				} else {// when we send only PropertiesId, when properties exist
					PropertiesEntity _properties = propertiesService.getProperties(properties.getPropertiesId());
					_articleEntity.setProperties(_properties);
				}

				break;
			case "images":
				@SuppressWarnings("unchecked")
				ArrayList<Object> images = (ArrayList<Object>) value;

				for (Object obj : images) {
					ImageEntity image = mapper.convertValue(obj, ImageEntity.class);
					if (image.getImageId() == 0) {
						image.setArt(_articleEntity);
						imageService.saveImage(image);
					} else { // when image exist
						image = imageService.getImage(image.getImageId());
						image.setArt(getArticle(id));
					}
				}
			}
		});

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ArticleEntity>> violations = validator.validate(_articleEntity);// , OnUpdate.class);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse(violations.toString()));
		}

		addArticle(_articleEntity);
		return ResponseEntity.ok().body(new MessageResponse("Successfully updated"));
	}

}
