package com.rentit.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleQuantityEntity;
import com.rentit.project.pojos.CustomPojoReturn;

@Repository
public interface ArticleQuantityRepository extends JpaRepository<ArticleQuantityEntity, Long> {

	// Liste von ausgeliehenen Article eines Users
	@Query("SELECT new com.rentit.project.pojos.CustomPojoReturn(aq.articleQuantityId, ar.articleId, ar.name, "
			+ "ar.description, im.imageLink, aq.quantity, re.rentDate, aq.ReturnedDate) "
			+ "FROM ArticleEntity ar, ArticleQuantityEntity aq, ImageEntity im , RentalEntity re, UserEntity us "
			+ "WHERE re.users = us.userId AND aq.rental = re.rentalId AND aq.article = ar.articleId "
			+ "AND im.art = ar.articleId AND im.imageType = 'titel' AND aq.returned = false "
			+ "AND us.userId = ?1 ")
	List<CustomPojoReturn> getListRentalUser(Long userid);

}
