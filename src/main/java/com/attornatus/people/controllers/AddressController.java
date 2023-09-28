package com.attornatus.people.controllers;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Object> getAllAddress(){
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/{idAddress}")
    public ResponseEntity<Object> getAddressById(@PathVariable Long idAddress){
        return ResponseEntity.ok(addressService.getAddresById(idAddress));
    }

    @PutMapping("/{idAddress}")
    public ResponseEntity<Object> updateAddress(@PathVariable Long idAddress,
                                                @RequestBody @Valid AddressRequestDto addressRequestDto) {
        return ResponseEntity.ok(addressService.updateAddress(idAddress, addressRequestDto));
    }

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<Object> deleteAddres(@PathVariable Long idAddress) {
        return ResponseEntity.ok(addressService.deleteAddres(idAddress));
    }
}
