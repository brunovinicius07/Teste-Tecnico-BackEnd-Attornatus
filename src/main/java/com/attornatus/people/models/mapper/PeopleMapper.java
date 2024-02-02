package com.attornatus.people.models.mapper;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    People toPeople(PeopleRequestDto peopleRequestDto);

    @Mapping(target = "idsAddress", expression = "java(mapAddress(people.getAddresses()))")
    PeopleResponseDto toPeopleResponseDto(People people);

    default List<Long> mapAddress(List<Address> addressList){
        if (addressList != null){
            return addressList.stream()
                    .map(Address::getIdAddress)
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }

    List<PeopleResponseDto> toListPeopleResponse(List<People> peopleList);




}
