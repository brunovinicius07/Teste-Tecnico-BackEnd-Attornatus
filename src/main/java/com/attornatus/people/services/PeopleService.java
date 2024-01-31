package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.People;

import java.util.List;

public interface PeopleService {
    PeopleResponseDto registerPeople(PeopleRequestDto peopleRequestDto);

    List<PeopleResponseDto> getAllPeople();

    List<People> validateListPeople();

    People validatePeople(Long idPeople);

    PeopleResponseDto getPeopleById(Long idPeople);

    PeopleResponseDto updatePeople(Long idPeople, PeopleRequestDto peopleRequestDto);

    String deletePeople(Long idPerson);
}
