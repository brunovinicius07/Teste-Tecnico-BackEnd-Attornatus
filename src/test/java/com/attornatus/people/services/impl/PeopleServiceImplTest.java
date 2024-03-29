package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.PeopleMapper;
import com.attornatus.people.repositories.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PeopleServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "João";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1997, 7, 15);
    public static final String CPF = "111.222.333-44";
    public static final List<Address> ADDRESSES = new ArrayList<>();
    public static final List<Long> IDS_ADDRESS = new ArrayList<>();
    public static final int INDEX = 0;

    static {
        Long idAddress = 1L;
        IDS_ADDRESS.add(idAddress);
    }

    @Autowired
    private PeopleServiceImpl peopleServiceImpl;

    @MockBean
    private PeopleRepository peopleRepository;

    @MockBean
    private PeopleMapper peopleMapper;

    private PeopleResponseDto peopleResponseDto;
    private PeopleRequestDto peopleRequestDto;
    private Optional<People> optionalPeople;
    private People people;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPeople();
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);
        when(peopleRepository.save(Mockito.any())).thenReturn(people);

        peopleServiceImpl.existingPeople(peopleRequestDto.getCpf());
        PeopleResponseDto response = peopleServiceImpl.registerPeople(peopleRequestDto);

        assertNotNull(response);
        assertEquals(PeopleResponseDto.class, response.getClass());
        assertEquals(ID, response.getIdPeople());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(CPF, response.getCpf());
        assertEquals(IDS_ADDRESS, response.getIdsAddress());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(optionalPeople);
        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);

        try {
            optionalPeople.get().setIdPeople(2L);
            peopleServiceImpl.registerPeople(peopleRequestDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfPeople() {
        when(peopleRepository.findAll()).thenReturn(List.of(people));
        when(peopleMapper.toListPeopleResponse(Mockito.any())).thenReturn(List.of(peopleResponseDto));

        List<PeopleResponseDto> response = peopleServiceImpl.getAllPeople();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PeopleResponseDto.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getIdPeople());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(BIRTH_DATE, response.get(INDEX).getBirthDate());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(IDS_ADDRESS, response.get(INDEX).getIdsAddress());
    }

    @Test
    void whenValidateListPeopleSuccess() {
        List<People> peopleList = new ArrayList<>();
        peopleList.add(people);
        when(peopleRepository.findAll()).thenReturn(peopleList);

        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);
        List<People> response = peopleServiceImpl.validateListPeople();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getIdPeople());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(BIRTH_DATE, response.get(0).getBirthDate());
        assertEquals(ADDRESSES, response.get(0).getAddresses());

        verify(peopleRepository, times(1)).findAll();
    }

    @Test
    void whenValidateListPeopleThenReturnAnListOfPeople() {
        when(peopleRepository.findAll()).thenReturn(Collections.emptyList());

        try {
            peopleServiceImpl.validateListPeople();
        } catch (Exception ex) {
            assertEquals("Pessoa não localizada", ex.getMessage());
        }
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
        assertEquals(CPF, response.getCpf());
        assertEquals(IDS_ADDRESS, response.getIdsAddress());
    }

    @Test
    void whenFindByIdThenRuntimeException() {
        String errorMessage = String.format("Pessoa com id %d não cadastrado!", ID);
        when(peopleRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException(errorMessage));

        try {
            peopleServiceImpl.getPeopleById(ID);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void whenValidatePeopleWithSuccess() {
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(people));
        People response = peopleServiceImpl.validatePeople(ID);

        assertNotNull(response);
        assertEquals(ID, response.getIdPeople());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(ADDRESSES, response.getAddresses());

        verify(peopleRepository, times(1)).findById(ID);
    }

    @Test
    void whenValidatePeopleThenRuntimeException() {
        when(peopleRepository.findById(ID)).thenReturn(optionalPeople);

        try {
            peopleServiceImpl.validatePeople(ID);
        } catch (Exception ex) {
            assertEquals("Pessoa com id " + ID + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(peopleRepository.findById(ID)).thenReturn(optionalPeople);
        when(peopleRepository.save(Mockito.any())).thenReturn(people);
        when(peopleMapper.toPeopleResponseDto(people)).thenReturn(peopleResponseDto);


        PeopleResponseDto response = peopleServiceImpl.updatePeople(peopleResponseDto.getIdPeople(), peopleRequestDto);

        assertNotNull(response);
        assertEquals(PeopleResponseDto.class, response.getClass());
        assertEquals(ID, response.getIdPeople());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(CPF, response.getCpf());
        assertEquals(IDS_ADDRESS, response.getIdsAddress());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        String errorMessage = String.format("Pessoa com id %d não cadastrado!", ID);
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(optionalPeople);
        when(peopleMapper.toPeopleResponseDto(Mockito.any())).thenReturn(peopleResponseDto);

        try {
            optionalPeople.get().setIdPeople(2L);
            peopleServiceImpl.registerPeople(peopleRequestDto);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getMessage());
            assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        when(peopleRepository.findById(ID)).thenReturn(optionalPeople);
        String result = peopleServiceImpl.deletePeople(ID);
        assertEquals("Pessoa com ID " + ID + " excluído com sucesso!", result);
        verify(peopleRepository, times(1)).delete(people);
    }

    @Test
    void whenDeleteWithAlertException() {
        when(peopleRepository.findById(anyLong())).thenThrow(new RuntimeException("Pessoa com id "
                + ID
                + " não cadastrada!"));
        try {
            peopleServiceImpl.deletePeople(ID);
        } catch (Exception ex) {
            assertEquals("Pessoa com id " + ID + " não cadastrada!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateMainAddressSuccess() {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        Address mainAddress = new Address();
        mainAddress.setMainAddress(true);
        people.getAddresses().add(mainAddress);

        People pesponse = peopleServiceImpl.validatePeople(ID);

        assertNotNull(pesponse);

        assertEquals(ID, pesponse.getIdPeople());
        assertEquals(NAME, pesponse.getName());
        assertEquals(BIRTH_DATE, pesponse.getBirthDate());
        assertEquals(ADDRESSES, pesponse.getAddresses());

        verify(peopleRepository, times(1)).findById(ID);
    }

    private void startPeople() {
        people = new People(ID, NAME, BIRTH_DATE,CPF , ADDRESSES);
        peopleResponseDto = new PeopleResponseDto(ID, NAME, BIRTH_DATE,CPF , IDS_ADDRESS);
        peopleRequestDto = new PeopleRequestDto(NAME, BIRTH_DATE,CPF);
        optionalPeople = Optional.of(new People(ID, NAME, BIRTH_DATE,CPF ,ADDRESSES));
    }
}
