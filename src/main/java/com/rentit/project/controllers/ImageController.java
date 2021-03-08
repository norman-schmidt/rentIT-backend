package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ImageEntity;
import com.rentit.project.pojo.response.MessageResponse;
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
		return imageService.saveImage(imageEntity);
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

	@PutMapping("{id_image}/removeArticle")
	public ResponseEntity<MessageResponse> removeArticle(@PathVariable long id_image) {
		return imageService.removeArticle(id_image);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<MessageResponse> updateImageElement(@PathVariable long id, @RequestBody Map<String, Object> imageEntity) {
		return imageService.updateImageElement(id, imageEntity);
	}

}
