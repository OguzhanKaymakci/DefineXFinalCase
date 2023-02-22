package com.works.definexfinalcase.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "NotBlank")
    @Length(message = "max:50", max = 50)
    private String firstName;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String lastName;
    @Email(message = "e mail format error")
    @NotBlank(message = "please not blank ")
    private String email;
    @Column(unique = true)
    @NotBlank(message = "Not blank")
    @Length(message = "max:11", max = 11)
    private String idNo;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String salary;
    @Range(message = "Have to be 10", min = 10, max = 10)
    @Column(nullable = false)
    private int phone;

    private String birthDate;
    private String Guarantee;
    @NotBlank(message = "please not blank password")
    @Pattern(message = "Password must contain min one upper,lower letter and 0-9 digit number ",
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).{6,}")
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "role_Id",referencedColumnName = "id")
    private Role roles;


}
