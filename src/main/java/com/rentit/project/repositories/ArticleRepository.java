package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.dto.CustomArticle;
import com.rentit.project.models.ArticleEntity;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

	// Name
	@Query("SELECT new com.rentit.project.dto.CustomArticle(a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.name like %?1%")
	List<CustomArticle> findByName(String name);

	// Price
	@Query("SELECT new com.rentit.project.dto.CustomArticle(a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId "
			+ "and im.imageType = 'titel' and a.name like %?1% and a.price between ?2 and ?3")
	List<CustomArticle> filterWithNamePrice(String name, double min, double max);

	// Category
	// @Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category =
	// c.categoryId and c.name like %?1%")
	// List<ArticleEntity> findByCategory(String category);
	/*
	 * // Price von min to max
	 * 
	 * @Query("SELECT a FROM ArticleEntity a ORDER BY a.price asc")
	 * List<ArticleEntity> PriceMinToMax();
	 * 
	 * // Price von max to min
	 * 
	 * @Query("SELECT a FROM ArticleEntity a ORDER BY a.price desc")
	 * List<ArticleEntity> PriceMaxToMin();
	 * 
	 * // Price von min to max and category
	 * 
	 * @Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category = c.categoryId and c.name like %?1% ORDER BY a.price asc"
	 * ) List<ArticleEntity> PriceMinToMaxCategory();
	 * 
	 * // Price von max to min and category
	 * 
	 * @Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category = c.categoryId and c.name like %?1% ORDER BY a.price desc"
	 * ) List<ArticleEntity> PriceMaxToMinCategory();
	 * 
	 * // Properties
	 * // @Query("SELECT c FROM ArticleEntity c WHERE c.name like %?1%") //
	 * List<ArticleEntity> findCategoryByName(String categoryName);
	 */
}