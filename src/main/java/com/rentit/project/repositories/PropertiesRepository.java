package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.PropertiesEntity;

@Repository
public interface PropertiesRepository extends JpaRepository<PropertiesEntity, Long> {

}
