package com.works.definexfinalcase.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Customer extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "NotBlank")
    @Length(message = "max:50", max = 50)
    private String firstName;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String lastName;
    @Column(unique = true)
    @Email(message = "e mail format error")
    @NotBlank(message = "please not blank ")
    private String email;
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
