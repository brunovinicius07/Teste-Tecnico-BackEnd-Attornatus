package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.mapper.PeopleMapper;
import com.attornatus.people.repositories.PeopleRepository;
import com.attornatus.people.services.PeopleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
