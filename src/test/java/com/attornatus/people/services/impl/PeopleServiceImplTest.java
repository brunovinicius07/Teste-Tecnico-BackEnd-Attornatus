package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.PeopleMapper;
import com.attornatus.people.repositories.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PeopleServiceImplTest {

    public static final long ID                 = 1L;
    public static final String NAME             = "João";
    public static final LocalDate BIRTH_DATE    = LocalDate.of(1997, 7, 15);
    public static final List<Address> ADDRESSES = new ArrayList<>();
    public static final int INDEX               = 0;

    @Autowired
    private PeopleServiceImpl peopleServiceImpl;

    @MockBean
    private PeopleRepository peopleRepository;

    @MockBean
    private PeopleMapper peopleMapper;

    private PeopleResponseDto peopleResponseDto;
    private Optional<People> optionalPeople;
    private People people;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startPeople();
    }

    @Test
    void registerPeople() {
    }

    @Test
    void whenFindAllThenReturnAnListOfPeople(){
        when(peopleRepository.findAll()).thenReturn(List.of(people));
        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);

        List<PeopleResponseDto> response = peopleServiceImpl.getAllPeople();


        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PeopleResponseDto.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getIdPeople());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(BIRTH_DATE, response.get(INDEX).getBirthDate());
        assertEquals(ADDRESSES, response.get(INDEX).getAddresses());

    }

    @Test
    void validateListPeople() {
    }

    @Test
    void whenFindByIdThenReturnAnPeopleInstance() {
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(optionalPeople);
        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);

        PeopleResponseDto response = peopleServiceImpl.getPeopleById(ID);

        assertNotNull(response);
        assertEquals(PeopleResponseDto.class, response.getClass());
        assertEquals(ID, response.getIdPeople());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(ADDRESSES, response.getAddresses());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        String errorMessage = String.format("Pessoa com id %d não cadastrado!", ID);
        when(peopleRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException(errorMessage));

        try{
            peopleServiceImpl.getPeopleById(ID);
        } catch (Exception ex){
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void validatePeople() {

    }

    @Test
    void updatePeople() {
    }

    @Test
    void deletePeople() {
    }

    @Test
    void updateMainAddress() {
    }

    private void startPeople(){
        people = new People(ID, NAME, BIRTH_DATE, ADDRESSES);
        peopleResponseDto = new PeopleResponseDto(ID, NAME, BIRTH_DATE, ADDRESSES);
        optionalPeople = Optional.of(new People(ID, NAME, BIRTH_DATE, ADDRESSES));
    }
}