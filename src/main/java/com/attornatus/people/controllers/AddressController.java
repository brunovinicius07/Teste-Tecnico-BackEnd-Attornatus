package com.attornatus.people.controllers;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Object> registerAddress(@RequestBody @Valid AddressRequestDto addressRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.registerAddress(addressRequestDto));
    }
}
