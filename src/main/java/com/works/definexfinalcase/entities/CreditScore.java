package com.works.definexfinalcase.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String creditLimit;
    private Long salary;
    private Long guarantee;
    private String phone;
    private Date birthDate;
    private String idNo;
}
