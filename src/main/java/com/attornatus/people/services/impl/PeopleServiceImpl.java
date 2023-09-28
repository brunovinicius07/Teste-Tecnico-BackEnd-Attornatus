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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}