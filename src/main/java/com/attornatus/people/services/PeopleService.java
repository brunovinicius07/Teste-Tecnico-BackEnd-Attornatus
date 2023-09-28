package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;

import java.util.List;

public interface PeopleService {
    PeopleResponseDto registerPeople(PeopleRequestDto peopleRequestDto);

    List<PeopleResponseDto> getAllPeople();

    PeopleResponseDto getPeopleById(Long idPeople);

    PeopleResponseDto updatePeople(Long idPeople, PeopleRequestDto peopleRequestDto);
}
