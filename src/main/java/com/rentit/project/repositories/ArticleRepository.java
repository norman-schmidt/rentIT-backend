package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.pojos.CustomArticle;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

	// Name
	@Query("SELECT new com.rentit.project.pojos.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.name like %?1%")
	List<CustomArticle> findByName(String name);

	// Price
	@Query("SELECT new com.rentit.project.pojos.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId "
			+ "and im.imageType = 'titel' and a.name like %?1% and a.price between ?2 and ?3")
	List<CustomArticle> filterWithNamePrice(String name, double min, double max);

	// Name-Category-Price
	@Query("SELECT new com.rentit.project.pojos.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im ,CategoryEntity ca "
			+ "WHERE im.art = a.articleId and a.category = ca.categoryId "
			+ "and im.imageType = 'titel' and a.name like %?1% " + "and ca.name like %?2% and a.price between ?3 and ?4")
	List<CustomArticle> filterWithNameCategoryPrice(String name, String category, double min, double max);

	// Ids
	@Query("SELECT new com.rentit.project.pojos.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.articleId=?1")
	CustomArticle findByIds(Long id);

}