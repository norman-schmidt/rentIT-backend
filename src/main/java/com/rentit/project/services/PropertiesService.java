package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.PropertiesEntity;
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

		_propertiesEntity.setStorage(propertiesEntity.getStorage());
		_propertiesEntity.setOperatingSystem(propertiesEntity.getOperatingSystem());
		_propertiesEntity.setColor(propertiesEntity.getColor());
		_propertiesEntity.setSpecialFeature(propertiesEntity.getSpecialFeature());
		_propertiesEntity.setManifacturer(propertiesEntity.getManifacturer());
		_propertiesEntity.setArticle(articleService.updateArticle(_propertiesEntity.getArticle(),
				_propertiesEntity.getArticle().getArticleId()));

		return propertiesRepository.save(propertiesEntity);
	}

	public PropertiesEntity setPropertyArticle(long id_property, long id_article) {
		ArticleEntity art = articleService.getArticle(id_article);
		PropertiesEntity ent = getProperties(id_property);
		art.setProperties(ent);
		ent.setArticle(art);
		articleService.updateArticle(art, art.getArticleId());
		updateProperties(ent, ent.getPropertiesId());
		return ent;
	}

}
