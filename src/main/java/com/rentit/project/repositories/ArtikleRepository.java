package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.Artikel;

@Repository
public interface ArtikleRepository extends JpaRepository<Artikel, Long> {

}
