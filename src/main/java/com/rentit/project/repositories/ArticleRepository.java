package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleEntity;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

	// Names
	@Query("SELECT a FROM ArticleEntity a WHERE a.name like %?1%")
	List<ArticleEntity> findByName(String name);

	// Category
	//@Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category = c.categoryId and c.name like %?1%")
	//List<ArticleEntity> findByCategory(String category);
	/*
	// Price von min to max
	@Query("SELECT a FROM ArticleEntity a ORDER BY a.price asc")
	List<ArticleEntity> PriceMinToMax();

	// Price von max to min
	@Query("SELECT a FROM ArticleEntity a ORDER BY a.price desc")
	List<ArticleEntity> PriceMaxToMin();

	// Price von min to max and category
	@Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category = c.categoryId and c.name like %?1% ORDER BY a.price asc")
	List<ArticleEntity> PriceMinToMaxCategory();

	// Price von max to min and category
	@Query("SELECT a FROM ArticleEntity a, CategoryEntity c WHERE a.category = c.categoryId and c.name like %?1% ORDER BY a.price desc")
	List<ArticleEntity> PriceMaxToMinCategory();

	// Properties
	// @Query("SELECT c FROM ArticleEntity c WHERE c.name like %?1%")
	// List<ArticleEntity> findCategoryByName(String categoryName);*/
}