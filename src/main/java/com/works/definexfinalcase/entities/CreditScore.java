package com.works.definexfinalcase.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class CreditScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surName;
    private String creditScoreResult;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String creditLimit;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String salary;
    private Long guarantee;
    /*@Range(message = "Have to be 10", min = 10)*/
    @Column(nullable = false)
    private int phone;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @NotBlank(message = "Not blank")
    @Length(message = "max:11", max = 11)
    private String idNo;
}
