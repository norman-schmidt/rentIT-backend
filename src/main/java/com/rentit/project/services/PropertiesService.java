package com.rentit.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentit.project.repositories.PropertiesRepository;

@Service
public class PropertiesService {

	@Autowired
	private PropertiesRepository propertiesRepository;

}
