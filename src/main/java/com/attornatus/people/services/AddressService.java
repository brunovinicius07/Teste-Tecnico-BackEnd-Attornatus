package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto registerAddress(AddressRequestDto addressRequestDto);

    List<AddressResponseDto> getAllAddress(Long idPeople);

    AddressResponseDto getAddresById(Long idAddress);

    AddressResponseDto updateAddress(Long idAddress, AddressRequestDto addressRequestDto);

    Object deleteAddres(Long idAddress);
}
