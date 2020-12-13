package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
