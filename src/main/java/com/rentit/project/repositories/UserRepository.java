package com.rentit.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
