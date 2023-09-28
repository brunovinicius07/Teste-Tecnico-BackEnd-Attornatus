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
import java.util.Optional;
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

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto getAddresById(Long idAddress) {

        Address address = validateAddres(idAddress);

        return addressMapper.toAddressResponseDto(address);
    }

    @Transactional(readOnly = true)
    public Address validateAddres(Long cdAddres){
        Optional<Address> optionalAddres = addressRepository.findById(cdAddres);

        if(optionalAddres.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Endereço com id %S não cadastrado!" , cdAddres),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalAddres.get();
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponseDto updateAddress(Long idAddress, AddressRequestDto addressRequestDto) {
        Address address = validateAddres(idAddress);
        address.setPublicPlace(addressRequestDto.getPublicPlace() != null ? addressRequestDto.getPublicPlace() : address.getPublicPlace());
        address.setZipCode(addressRequestDto.getZipCode() != null ? addressRequestDto.getZipCode() : address.getZipCode());
        address.setNumber(addressRequestDto.getNumber() != null ? addressRequestDto.getNumber() : address.getNumber());
        address.setCity(addressRequestDto.getCity() != null ? addressRequestDto.getCity() : address.getCity());
        address.getPeople().setIdPeople(addressRequestDto.getIdPeople() != null ? addressRequestDto.getIdPeople() : address.getPeople().getIdPeople());

        peopleServiceimpl.updateMainAddress(addressRequestDto.getIdPeople(), addressRequestDto.isMainAddress());

        address.setMainAddress(addressRequestDto.isMainAddress());

        return addressMapper.toAddressResponseDto(addressRepository.save(address));
    }


}
