package com.attornatus.people.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_address")
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;

    @Size(max = 150)
    @NotBlank
    private String publicPlace;

    @Size(max = 9)
    @NotBlank
    private String zipCode;

    @Size(max = 6)
    @NotBlank
    private String number;

    @Size(max = 50)
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
