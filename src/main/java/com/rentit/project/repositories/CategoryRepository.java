package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	// Name
	//@Query("SELECT a.articleId, a.name, a.description, a.stockLevel, a.price FROM CategoryEntity c, ArticleEntity a "
	//		+ "WHERE a.category = c.categoryId AND c.name like %?1%")
	@Query("SELECT c.name FROM CategoryEntity c WHERE c.name like %?1%")
	List<CategoryEntity> findByName(String name);

	// findAllName
	@Query("SELECT c.name FROM CategoryEntity c")
	List<String> findAllName();
}
