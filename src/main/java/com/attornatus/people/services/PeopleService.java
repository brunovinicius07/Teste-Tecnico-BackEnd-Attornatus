package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;

public interface PeopleService {
    PeopleResponseDto registerPeople(PeopleRequestDto peopleRequestDto);
}
