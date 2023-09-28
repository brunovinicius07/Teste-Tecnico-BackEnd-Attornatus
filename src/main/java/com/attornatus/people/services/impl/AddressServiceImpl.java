package com.attornatus.people.services.impl;

import com.attornatus.people.exception.AlertException;
import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.AddressMapper;
import com.attornatus.people.repositories.AddressRepository;
import com.attornatus.people.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final PeopleServiceImpl peopleServiceimpl;

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    public AddressServiceImpl(PeopleServiceImpl peopleServiceimpl, AddressMapper addressMapper, AddressRepository addressRepository) {
        this.peopleServiceimpl = peopleServiceimpl;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponseDto registerAddress(AddressRequestDto addressRequestDto) {
        People people = peopleServiceimpl.validatePeople(addressRequestDto.getIdPeople());
        peopleServiceimpl.updateMainAddress(addressRequestDto.getIdPeople(), addressRequestDto.isMainAddress());
        Address address = addressMapper.toAddress(addressRequestDto);
        address.setPeople(people);

        return addressMapper.toAddressResponseDto(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAllAddress() {

        List<Address> addressList = validateListAddress();
        return addressList.stream().map(addressMapper::toAddressResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Address> validateListAddress(){
        List<Address>addresList = addressRepository.findAll();

        if (addresList.isEmpty()){
            throw new AlertException(
                    "warn",
                    "Nenhum enredeço encontrado!",
                    HttpStatus.NOT_FOUND
            );
        }
        return addresList;
    }


}
