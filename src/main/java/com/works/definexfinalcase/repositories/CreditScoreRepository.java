package com.works.definexfinalcase.repositories;

import com.works.definexfinalcase.entities.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    Optional<CreditScore> findByBirthDateAndIdNo(String idNo, Date birthDate );
    List<CreditScore> findByIdNo(String idNo );

}