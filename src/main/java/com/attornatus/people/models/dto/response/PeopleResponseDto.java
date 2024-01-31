package com.attornatus.people.models.dto.response;

import com.attornatus.people.models.entity.Address;
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

    private List<Address> addresses = new ArrayList<>();
}
