package com.attornatus.people.services.impl;

import com.attornatus.people.exception.AlertException;
import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.PeopleMapper;
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

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public PeopleResponseDto registerPeople(PeopleRequestDto peopleRequestDto) {
        return peopleMapper.toPeopleResponseDto(peopleRepository.save(peopleMapper.toPeople(peopleRequestDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeopleResponseDto> getAllPeople() {

        List<People> peopleList = validateListPeople();

        return peopleList.stream().map(peopleMapper::toPeopleResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<People> validateListPeople(){

        List<People> peopleList = peopleRepository.findAll();

        if(peopleList.isEmpty()){
            throw new AlertException(
                    "warn",
                    "Nenhuma pessoa encontrado!",
                    HttpStatus.NOT_FOUND
            );
        }
        return peopleList;
    }

    @Override
    @Transactional(readOnly = true)
    public PeopleResponseDto getPeopleById(Long idPeople) {

        People people = validatePeople(idPeople);

        return peopleMapper.toPeopleResponseDto(people);
    }

    @Transactional(readOnly = true)
    public People validatePeople(Long idPeople){

        Optional<People> optionalPeople = peopleRepository.findById(idPeople);

        if(optionalPeople.isEmpty()){
            throw new AlertException(
                    "warn",
                    String.format("Pessoa com id %S não cadastrado!" , idPeople),
                    HttpStatus.NOT_FOUND
            );
        }
        return optionalPeople.get();
    }

    @Override
    @Transactional(readOnly = false)
    public PeopleResponseDto updatePeople(Long idPeople, PeopleRequestDto peopleRequestDto) {

        People people = validatePeople(idPeople);

        people.setName(peopleRequestDto.getName() != null ? peopleRequestDto.getName() : people.getName());
        people.setBirthDate(peopleRequestDto.getBirthDate() != null ? peopleRequestDto.getBirthDate() : people.getBirthDate());

        return peopleMapper.toPeopleResponseDto(peopleRepository.save(people));
    }

    @Override
    @Transactional(readOnly = false)
    public String deletePeople(Long idPerson) {

        People people = validatePeople(idPerson);

        peopleRepository.delete(people);

        return "Pessoa com ID " + idPerson + " excluído com sucesso!";
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateMainAddress(Long idPeople, boolean mainAdress){

        People people = validatePeople(idPeople);

        if (mainAdress) {
            people.getAddresses().forEach(a -> a.setMainAddress(false));
            this.peopleRepository.save(people);
        }
    }
}
