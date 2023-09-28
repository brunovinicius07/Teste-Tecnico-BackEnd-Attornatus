package com.attornatus.people.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_people")
public class People implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeople;

    @NotBlank
    private String name;

    @NotNull
    @Past
    private LocalDate birthDate;

    @OneToMany(mappedBy = "people", fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<>();
}
