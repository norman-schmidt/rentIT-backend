package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.PropertiesEntity;
import com.rentit.project.repositories.PropertiesRepository;

@Service
public class PropertiesService {

	@Autowired
	private PropertiesRepository propertiesRepository;

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

	public PropertiesEntity updateProperties(PropertiesEntity properties) {
		return propertiesRepository.save(properties);
	}

}
