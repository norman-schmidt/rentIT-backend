package com.rentit.project.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentit.project.models.ERole;
import com.rentit.project.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
	
	Optional<Role> findByName(ERole name);

}
