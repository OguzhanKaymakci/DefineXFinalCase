package com.works.definexfinalcase.repositories;

import com.works.definexfinalcase.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailEqualsIgnoreCase(String email);
    Optional<Customer> findById(Long id);
}