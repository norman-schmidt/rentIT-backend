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
import com.rentit.project.models.ImageEntity;
import com.rentit.project.pojo.response.MessageResponse;
import com.rentit.project.repositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public ImageEntity saveImage(ImageEntity image) {
		return imageRepository.save(image);
	}

	public ImageEntity getImage(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<ImageEntity> getAllImages() {
		return imageRepository.findAll();
	}

	public void deleteImage(Long id) {
		imageRepository.delete(getImage(id));
	}

	public ImageEntity updateImage(ImageEntity imageEntity, long id) {
		ImageEntity _imageEntity = getImage(id);
		_imageEntity = imageEntity;
		return imageRepository.save(_imageEntity);
	}

	// delete foreign keys article
	public ResponseEntity<MessageResponse> removeArticle(long id_image) {
		ImageEntity image = getImage(id_image);
		image.setArt(null);
		saveImage(image);
		return ResponseEntity.ok().body(new MessageResponse("Successfully deleted"));
	}

	public ResponseEntity<MessageResponse> updateImageElement(long id, Map<String, Object> imageEntity) {
		ImageEntity image = getImage(id);

		imageEntity.forEach((element, value) -> {
			switch (element) {
			case "imageLink":
				image.setImageLink((String) value);
				break;
			case "imageType":
				image.setImageType((String) value);
				break;
			case "art":
				ObjectMapper mapper = new ObjectMapper();
				ArticleEntity article = mapper.convertValue(value, ArticleEntity.class);
				image.setArt(article);
				break;
			}
		});

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ImageEntity>> violations = validator.validate(image);// , OnUpdate.class);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse(violations.toString()));
		}

		saveImage(image);
		return ResponseEntity.ok().body(new MessageResponse("Successfully updated"));
	}

}
