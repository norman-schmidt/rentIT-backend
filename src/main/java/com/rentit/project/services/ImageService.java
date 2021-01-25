package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.repositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public ImageEntity addImage(ImageEntity image) {
		return imageRepository.save(image);
	}

	public ImageEntity getImage(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<ImageEntity> getAllImages() {
		return imageRepository.findAll();
	}

	public void deleteImage(Long id) {
		ImageEntity image = getImage(id);
		imageRepository.delete(image);
	}

	public ImageEntity updateImage(ImageEntity image) {
		return imageRepository.save(image);
	}

	public List<ImageEntity> updateImage(List<ImageEntity> image) {
		return imageRepository.saveAll(image);
	}

}
