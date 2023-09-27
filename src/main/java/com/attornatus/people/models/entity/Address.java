package com.attornatus.people.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;

    @NotBlank
    private String publicPlace;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String number;

    @NotBlank
    private String city;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_people")
    @NotNull
    private People people;

    @NotNull
    private boolean mainAddress;
}
