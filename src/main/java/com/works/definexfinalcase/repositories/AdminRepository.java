package com.works.definexfinalcase.repositories;

import com.works.definexfinalcase.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByEmailEqualsIgnoreCase(String email);
}
