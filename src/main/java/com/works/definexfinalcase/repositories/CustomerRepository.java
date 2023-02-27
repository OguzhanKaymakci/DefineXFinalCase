package com.works.definexfinalcase.repositories;

import com.works.definexfinalcase.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailEqualsIgnoreCase(String email);
    Optional<Customer> findById(Long id);
        /*Gerçekleştirilmiş bir kredi başvurusu sadece kimlik numarası ve doğum tarihi bilgisi ile sorgulanabilir.
     Doğum tarihi ve kimlik bilgisi eşleşmezse sorgulanamamalıdır. */


/*    Customer findByIdNoAndBirthday(String idNo, Date birthday);*/
}