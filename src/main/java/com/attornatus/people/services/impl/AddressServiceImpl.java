package com.attornatus.people.services.impl;

import com.attornatus.people.exception.Address.AddressIsPresentException;
import com.attornatus.people.exception.Address.AddressNotFoundException;
import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.AddressMapper;
import com.attornatus.people.repositories.AddressRepository;
import com.attornatus.people.repositories.PeopleRepository;
import com.attornatus.people.services.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final PeopleServiceImpl peopleServiceimpl;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    private final PeopleRepository peopleRepository;

    public AddressServiceImpl(PeopleServiceImpl peopleServiceimpl,
                              AddressMapper addressMapper,
                              AddressRepository addressRepository,
                              PeopleRepository peopleRepository) {
        this.peopleServiceimpl = peopleServiceimpl;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.peopleRepository = peopleRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponseDto registerAddress(AddressRequestDto addressRequestDto) {
        existingAddress(addressRequestDto.getPublicPlace(),
                addressRequestDto.getNumber(),
                addressRequestDto.getZipCode());

        People people = peopleServiceimpl.validatePeople(addressRequestDto.getIdPeople());
        peopleServiceimpl.updateMainAddress(addressRequestDto.getIdPeople(), addressRequestDto.isMainAddress());

        Address address = addressMapper.toAddress(addressRequestDto);
        address.setPeople(people);

        return addressMapper.toAddressResponseDto(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAllAddress(Long idPeople) {

        return addressMapper.toListAddressResponse(validateListAddress(idPeople));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> validateListAddress(Long idPeople) {
        List<Address> addresList = addressRepository.findAllAddressByPeopleIdPeople(idPeople);
        if (addresList.isEmpty())throw new AddressNotFoundException();

        return addresList;
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto getAddressById(Long idAddress) {
        return addressMapper.toAddressResponseDto(validateAddress(idAddress));
    }

    @Override
    @Transactional(readOnly = true)
    public Address validateAddress(Long cdAddress) {
        return addressRepository.findById(cdAddress).orElseThrow(AddressNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public void existingAddress(String publicPlace, String number, String zipCode) {
        addressRepository.findAddressByPublicPlaceAndNumberAndZipCode(publicPlace, number, zipCode)
                .ifPresent(a -> {
                    throw  new AddressIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponseDto updateAddress(Long idAddress, AddressRequestDto addressRequestDto) {
        existingAddress(addressRequestDto.getPublicPlace(),
                        addressRequestDto.getNumber(),
                        addressRequestDto.getZipCode());

        Address address = validateAddress(idAddress);
        People people = peopleRepository.findPeopleByIdPeople(addressRequestDto.getIdPeople());

        address.setPublicPlace(addressRequestDto.getPublicPlace());
        address.setZipCode(addressRequestDto.getZipCode());
        address.setNumber(addressRequestDto.getNumber());
        address.setCity(addressRequestDto.getCity());
        address.setPeople(people);

        peopleServiceimpl.updateMainAddress(addressRequestDto.getIdPeople(), addressRequestDto.isMainAddress());

        address.setMainAddress(addressRequestDto.isMainAddress());

        return addressMapper.toAddressResponseDto(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteAddress(Long idAddress) {

        addressRepository.delete(validateAddress(idAddress));

        return "Endereço com o ID " + idAddress + " excluído com sucesso!";
    }
}
