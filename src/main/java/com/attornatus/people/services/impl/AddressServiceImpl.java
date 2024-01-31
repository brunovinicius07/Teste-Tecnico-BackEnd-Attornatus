package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.AddressMapper;
import com.attornatus.people.repositories.AddressRepository;
import com.attornatus.people.repositories.PeopleRepository;
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
        People people = peopleServiceimpl.validatePeople(addressRequestDto.getIdPeople());
        peopleServiceimpl.updateMainAddress(addressRequestDto.getIdPeople(), addressRequestDto.isMainAddress());

        Address address = addressMapper.toAddress(addressRequestDto);
        address.setPeople(people);

        return addressMapper.toAddressResponseDto(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAllAddress(Long idPeople) {
        List<Address> addressList = validateListAddress(idPeople);

        return addressList.stream().map(addressMapper::toAddressResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> validateListAddress(Long idPeople) {
        List<Address> addresList = addressRepository.findAllAddressByPeopleIdPeople(idPeople);

        if (addresList.isEmpty()) {
            throw new AlertException(
                    "warn",
                    "Nenhum endereço encontrado!",
                    HttpStatus.NOT_FOUND
            );
        }
        return addresList;
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto getAddressById(Long idAddress) {
        Address address = validateAddress(idAddress);

        return addressMapper.toAddressResponseDto(address);
    }

    @Override
    @Transactional(readOnly = true)
    public Address validateAddress(Long cdAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(cdAddress);

        if (optionalAddress.isEmpty()) {
            throw new AlertException(
                    "warn",
                    String.format("Endereço com id %S não cadastrado!", cdAddress),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalAddress.get();
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponseDto updateAddress(Long idAddress, AddressRequestDto addressRequestDto) {
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
        Address address = validateAddress(idAddress);

        addressRepository.delete(address);

        return "Endereço com o ID " + idAddress + " excluído com sucesso!";
    }
}
