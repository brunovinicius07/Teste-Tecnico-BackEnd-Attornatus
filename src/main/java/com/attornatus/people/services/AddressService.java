package com.attornatus.people.services;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;

public interface AddressService {
    AddressResponseDto registerAddress(AddressRequestDto addressRequestDto);
}
