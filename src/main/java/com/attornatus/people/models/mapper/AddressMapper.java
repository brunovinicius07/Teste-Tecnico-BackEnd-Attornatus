package com.attornatus.people.models.mapper;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "idPeople", target = "people.idPeople")
    Address toAddress(AddressRequestDto addressRequestDto);

    @Mapping(source = "people.idPeople", target = "idPeople")
    AddressResponseDto toAddressResponseDto(Address address);

    List<AddressResponseDto> toListAddressResponse(List<Address> addressList);
}
