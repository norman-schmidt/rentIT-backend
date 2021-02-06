package com.rentit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.models.ImageEntity;
import com.rentit.project.models.UserEntity;
import com.rentit.project.repositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

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

	public ImageEntity updateImage(ImageEntity imageEntity, long id) {

		ImageEntity _imageEntity = getImage(id);

		_imageEntity.setImageLink(imageEntity.getImageLink());
		_imageEntity.setImageType(imageEntity.getImageType());
		_imageEntity.setUser(userService.updateUser(_imageEntity.getUser(), _imageEntity.getUser().getUserId()));
		_imageEntity.setArt(articleService.updateArticle(_imageEntity.getArt(), _imageEntity.getArt().getArticleId()));

		return imageRepository.save(imageEntity);
	}

	public List<ImageEntity> updateImage(List<ImageEntity> image) {
		return imageRepository.saveAll(image);
	}

	public ImageEntity setImageUser(long id_image, long id_user) {
		ImageEntity image = getImage(id_image);
		UserEntity user = userService.getUser(id_user);
		image.setUser(user);
		user.setImage(image);
		updateImage(image, id_image);
		userService.updateUser(user, id_user);
		return image;
	}

	public ImageEntity addImageArticle(long id_image, long id_article) {
		ImageEntity image = getImage(id_image);
		ArticleEntity article = articleService.getArticle(id_article);
		image.setArt(article);
		article.getImages().add(image);
		updateImage(image, id_image);
		articleService.updateArticle(article, id_article);
		return image;
	}

	public ImageEntity removeImageArticle(long id_image, long id_article) {
		ImageEntity image = getImage(id_image);
		ArticleEntity article = articleService.getArticle(id_article);
		image.setArt(article);
		article.getImages().remove(image);
		updateImage(image, id_image);
		articleService.updateArticle(article, id_article);
		return image;
	}

}
