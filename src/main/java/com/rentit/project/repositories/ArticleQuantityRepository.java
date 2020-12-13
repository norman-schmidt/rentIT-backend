package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ArticleQuantityEntity;

@Repository
public interface ArticleQuantityRepository extends JpaRepository<ArticleQuantityEntity, Long> {

}
