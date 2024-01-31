package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;

import java.util.List;

public interface AddressService {
    AddressResponseDto registerAddress(AddressRequestDto addressRequestDto);

    List<AddressResponseDto> getAllAddress(Long idPeople);

    List<Address> validateListAddress(Long idPeople);

    AddressResponseDto getAddressById(Long idAddress);

    Address validateAddress(Long cdAddress);

    AddressResponseDto updateAddress(Long idAddress, AddressRequestDto addressRequestDto);

    Object deleteAddress(Long idAddress);
}
