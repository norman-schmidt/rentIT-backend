package com.rentit.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.services.ArticleService;
import com.rentit.project.services.ImageService;
import com.rentit.project.services.UserService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@GetMapping("all")
	public List<ImageEntity> getAllImage() {
		return imageService.getAllImages();
	}

	@GetMapping("{id}")
	public ImageEntity getImageById(@PathVariable long id) {
		return imageService.getImage(id);
	}

	@PostMapping("add")
	public ImageEntity addImage(@RequestBody ImageEntity imageEntity) {
		return imageService.addImage(imageEntity);
	}

	@PutMapping("update/{id}")
	public ImageEntity updateImage(@RequestBody ImageEntity imageEntity, @PathVariable long id) {

		ImageEntity _imageEntity = imageService.getImage(id);

		_imageEntity.setImageLink(imageEntity.getImageLink());
		_imageEntity.setImageType(imageEntity.getImageType());
		_imageEntity.setUser(userService.updateUser(_imageEntity.getUser()));
		_imageEntity.setArticle(articleService.updateArticle(_imageEntity.getArticle()));

		return imageService.updateImage(_imageEntity);
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<Map<String, Boolean>> removeImage(@PathVariable Long id) {

		imageService.deleteImage(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Successfully deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("update/{id_image}/user/{id_user}")
	public ImageEntity setImageUser(@PathVariable long id_image, @PathVariable long id_user) {
		ImageEntity image = imageService.getImage(id_image);
		UserEntity user = userService.getUser(id_user);
		image.setUser(user);
		user.setImage(image);
		imageService.updateImage(image);
		userService.updateUser(user);
		return image;
	}

//	@PutMapping("update/{id_image}/article/{id_article}")
//	public ImageEntity setImageArticle(@PathVariable long id_image, @PathVariable long id_article) {
//		ImageEntity image = imageService.getImage(id_image);
//		ArticleEntity article = articleService.getArticle(id_article);
//		List<ImageEntity> list = new ArrayList<ImageEntity>();
//		list.add(image);
//		image.setArticle(article);
//		article.setImages(list);
//		imageService.updateImage(image);
//		articleService.updateArticle(article);
//		return image;
//	}

	@PutMapping("update/{id_image}/article/{id_article}/add")
	public ImageEntity addImageArticle(@PathVariable long id_image, @PathVariable long id_article) {
		ImageEntity image = imageService.getImage(id_image);
		ArticleEntity article = articleService.getArticle(id_article);
		image.setArticle(article);
		article.getImages().add(image);
		imageService.updateImage(image);
		articleService.updateArticle(article);
		return image;
	}

	@PutMapping("update/{id_image}/article/{id_article}/remove")
	public ImageEntity removeImageArticle(@PathVariable long id_image, @PathVariable long id_article) {
		ImageEntity image = imageService.getImage(id_image);
		ArticleEntity article = articleService.getArticle(id_article);
		image.setArticle(article);
		article.getImages().remove(image);
		imageService.updateImage(image);
		articleService.updateArticle(article);
		return image;
	}

}
