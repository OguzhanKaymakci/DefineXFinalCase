package com.works.definexfinalcase.repositories;

import com.works.definexfinalcase.entities.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    Optional<CreditScore> findByGuarantee(Long guarantee);

    Optional<CreditScore> findByBirthDateAndIdNo(Long idNo, LocalDate birthDate );
    List<CreditScore> findByIdNo(Long idNo );

}