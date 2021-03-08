package com.rentit.project.services;

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
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.repositories.PropertiesRepository;

@Service
public class PropertiesService {

	@Autowired
	private PropertiesRepository propertiesRepository;

	@Autowired
	private ArticleService articleService;

	public PropertiesEntity addProperties(PropertiesEntity properties) {
		return propertiesRepository.save(properties);
	}

	public PropertiesEntity getProperties(Long id) {
		return propertiesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<PropertiesEntity> getAllPropertiess() {
		return propertiesRepository.findAll();
	}

	public void deleteProperties(Long id) {
		PropertiesEntity properties = getProperties(id);
		propertiesRepository.delete(properties);
	}

	public PropertiesEntity updateProperties(PropertiesEntity propertiesEntity, long id) {
		PropertiesEntity _propertiesEntity = getProperties(id);
		_propertiesEntity = propertiesEntity;
		return propertiesRepository.save(_propertiesEntity);
	}

	public ResponseEntity<MessageResponse> updatePropertiesElement(long id, Map<String, Object> propertiesEntity) {
		PropertiesEntity properties = getProperties(id);

		propertiesEntity.forEach((element, value) -> {
			switch (element) {
			case "storage":
				properties.setStorage((Integer) value);
				break;
			case "operatingSystem":
				properties.setOperatingSystem((String) value);
				break;
			case "color":
				properties.setColor((String) value);
				break;
			case "specialFeature":
				properties.setSpecialFeature((String) value);
				break;
			case "manifacturer":
				properties.setManifacturer((String) value);
				break;
			case "article": // change article
				ObjectMapper mapper = new ObjectMapper();
				ArticleEntity _article = mapper.convertValue(value, ArticleEntity.class);

				_article = articleService.getArticle(_article.getArticleId()); // get new article
				_article.setProperties(properties);

				// old_article
				ArticleEntity old_article = properties.getArticle();
				old_article.setProperties(null);

				break;
			}
		});

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<PropertiesEntity>> violations = validator.validate(properties);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse(violations.toString()));
		}

		addProperties(properties);
		return ResponseEntity.ok().body(new MessageResponse("Successfully updated"));
	}

}
