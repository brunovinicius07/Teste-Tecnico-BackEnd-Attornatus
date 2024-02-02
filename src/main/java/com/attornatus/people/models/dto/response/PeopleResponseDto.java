package com.attornatus.people.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResponseDto {

    private Long idPeople;

    private String name;

    private LocalDate birthDate;

    private String cpf;

    private List<Long> idsAddress = new ArrayList<>();
}
