package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.pojo.article.Article;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

	// Name
	@Query("SELECT new com.rentit.project.dto.CustomArticle(a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.name like %?1%")
	List<Article> findByName(String name);

	// Price
	@Query("SELECT new com.rentit.project.dto.CustomArticle(a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId "
			+ "and im.imageType = 'titel' and a.name like %?1% and a.price between ?2 and ?3")
	List<Article> filterWithNamePrice(String name, double min, double max);

	// Ids
	@Query("SELECT new com.rentit.project.dto.CustomArticle(a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.articleId=?1")
	Article findByIds(Long id);

}