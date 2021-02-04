package com.rentit.project.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleEntity;
import com.rentit.project.pojo.query.CustomAAvailableQuantity;
import com.rentit.project.pojo.query.CustomArticle;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

	// Name
	@Query("SELECT new com.rentit.project.pojo.query.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId "
			+ "and im.imageType = 'titel' and lower(a.name) like lower(concat('%', concat(?1, '%')))")
	List<CustomArticle> findByName(String name);

	// Price
	@Query("SELECT new com.rentit.project.pojo.query.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId "
			+ "and im.imageType = 'titel' and lower(a.name) like lower(concat('%', concat(?1, '%'))) and a.price between ?2 and ?3")
	List<CustomArticle> filterWithNamePrice(String name, double min, double max);

	// Name-Category-Price
	@Query("SELECT new com.rentit.project.pojo.query.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im ,CategoryEntity ca "
			+ "WHERE im.art = a.articleId and a.category = ca.categoryId "
			+ "and im.imageType = 'titel' and lower(a.name) like lower(concat('%', concat(?1, '%'))) "
			+ "and lower(ca.name) like lower(concat('%', concat(?2, '%'))) and a.price between ?3 and ?4")
	List<CustomArticle> filterWithNameCategoryPrice(String name, String category, double min, double max);

	// Ids
	@Query("SELECT new com.rentit.project.pojo.query.CustomArticle(a.articleId, a.name, a.description, a.stockLevel, a.price, im.imageLink) "
			+ "FROM ArticleEntity a, ImageEntity im WHERE im.art = a.articleId and im.imageType = 'titel' and a.articleId=?1")
	CustomArticle findByIds(Long id);

	// get available quantity at date (stock + quantity at returnDate)
	@Query("SELECT new com.rentit.project.pojo.query.CustomAAvailableQuantity(ar.articleId, SUM(aq.quantity) + ar.stockLevel as verfugbar) "
			+ "FROM ArticleEntity ar, ArticleQuantityEntity aq WHERE ar.articleId = ?1 AND aq.article = ar.articleId "
			+ "AND aq.returnDate BETWEEN ?2 AND ?3 AND aq.returned = false GROUP BY ar.articleId , ar.stockLevel")
	CustomAAvailableQuantity getAvailabityQuantity(Long id, LocalDateTime dateBegin, LocalDateTime dateEnd);

}