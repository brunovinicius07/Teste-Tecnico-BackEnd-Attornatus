package com.attornatus.people.models.mapper;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.People;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    People toPeople(PeopleRequestDto peopleRequestDto);

    PeopleResponseDto toPeopleResponseDto(People people);
}
