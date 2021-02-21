package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.CategoryEntity;
import com.rentit.project.pojo.query.CustomArticle;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	// Name
	@Query("SELECT new com.rentit.project.pojo.query.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im, CategoryEntity ca "
			+ "WHERE im.art = a.articleId and a.category = ca.categoryId"
			+ " and im.imageType = 'titel' and lower(ca.name) like lower(concat('%', concat(?1, '%')))")
	List<CustomArticle> findByName(String name);

	// findAllName
	@Query("SELECT c.name, c.description FROM CategoryEntity c")
	List<Object[]>  findAllName();
	// List<String> findAllName();
}
