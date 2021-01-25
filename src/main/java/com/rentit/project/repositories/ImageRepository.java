package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rentit.project.models.ImageEntity;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
