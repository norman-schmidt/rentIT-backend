package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.services.ImageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@GetMapping("")
	public List<ImageEntity> getAllImage() {
		return imageService.getAllImages();
	}

	@GetMapping("{id}")
	public ImageEntity getImageById(@PathVariable long id) {
		return imageService.getImage(id);
	}

	@PostMapping("")
	public ImageEntity addImage(@RequestBody ImageEntity imageEntity) {
		return imageService.addImage(imageEntity);
	}

	@PutMapping("{id}")
	public ImageEntity updateImage(@RequestBody ImageEntity imageEntity, @PathVariable long id) {
		return imageService.updateImage(imageEntity, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Boolean>> removeImage(@PathVariable Long id) {
		imageService.deleteImage(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id_image}/user/{id_user}")
	public ImageEntity setImageUser(@PathVariable long id_image, @PathVariable long id_user) {
		return imageService.setImageUser(id_image, id_user);
	}

	@PutMapping("{id_image}/article/{id_article}/add")
	public ImageEntity addImageArticle(@PathVariable long id_image, @PathVariable long id_article) {
		return imageService.addImageArticle(id_image, id_article);
	}

	@PutMapping("{id_image}/article/{id_article}/remove")
	public ImageEntity removeImageArticle(@PathVariable long id_image, @PathVariable long id_article) {
		return imageService.removeImageArticle(id_image, id_article);
	}

}
