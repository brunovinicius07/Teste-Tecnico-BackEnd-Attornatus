package com.attornatus.people.services.impl;

import com.attornatus.people.exception.People.PeopleNotFoundException;
import com.attornatus.people.exception.People.PeoplePresentException;
import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.PeopleMapper;
import com.attornatus.people.repositories.AddressRepository;
import com.attornatus.people.repositories.PeopleRepository;
import com.attornatus.people.services.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {

    public final PeopleRepository peopleRepository;
    public final PeopleMapper peopleMapper;

    public final AddressRepository addressRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository,
                             PeopleMapper peopleMapper,
                             AddressRepository addressRepository) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public PeopleResponseDto registerPeople(PeopleRequestDto peopleRequestDto) {
        existingPeople(peopleRequestDto.getCpf());
        return peopleMapper.toPeopleResponseDto(peopleRepository.save(peopleMapper.toPeople(peopleRequestDto)));
    }

    public void existingPeople(String cpf){
        peopleRepository.findPeopleByCpf(cpf).ifPresent(people -> {
            throw new PeoplePresentException();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeopleResponseDto> getAllPeople() {
        List<People> peopleList = validateListPeople();

        return peopleList.stream().map(peopleMapper::toPeopleResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<People> validateListPeople() {
        List<People> peopleList = peopleRepository.findAll();
        if (peopleList.isEmpty()) throw new PeopleNotFoundException();

        return peopleList;
    }

    @Override
    @Transactional(readOnly = true)
    public PeopleResponseDto getPeopleById(Long idPeople) {
        People people = validatePeople(idPeople);

        return peopleMapper.toPeopleResponseDto(people);
    }

    @Override
    @Transactional(readOnly = true)
    public People validatePeople(Long idPeople) {
        return peopleRepository.findById(idPeople).orElseThrow(PeopleNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = false)
    public PeopleResponseDto updatePeople(Long idPeople, PeopleRequestDto peopleRequestDto) {
        existingPeople(peopleRequestDto.getCpf());
        People people = validatePeople(idPeople);

        people.setName(peopleRequestDto.getName());
        people.setBirthDate(peopleRequestDto.getBirthDate());
        people.setCpf(peopleRequestDto.getCpf());

        return peopleMapper.toPeopleResponseDto(peopleRepository.save(people));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateMainAddress(Long idPeople, boolean mainAddress) {
        People people = validatePeople(idPeople);

        if (mainAddress) {
            people.getAddresses().forEach(a -> a.setMainAddress(false));
            this.peopleRepository.save(people);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public String deletePeople(Long idPerson) {
        People people = validatePeople(idPerson);
        people.getAddresses().forEach(a -> {
            a.setPeople(null);
            addressRepository.delete(a);
        });
        peopleRepository.delete(people);

        return "Pessoa com ID " + idPerson + " excluído com sucesso!";
    }
}
