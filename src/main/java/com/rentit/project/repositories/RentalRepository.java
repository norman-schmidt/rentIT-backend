package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.RentalEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

}
