package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.request.AddressRequestDto;
import com.attornatus.people.models.dto.response.AddressResponseDto;
import com.attornatus.people.models.entity.Address;
import com.attornatus.people.models.entity.People;
import com.attornatus.people.models.mapper.AddressMapper;
import com.attornatus.people.repositories.AddressRepository;
import com.attornatus.people.repositories.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceImplTest {

    public static final long ID_PEOPLE = 1L;
    public static final String NAME = "João";
    public static final LocalDate DATE = LocalDate.of(1997, 7, 15);
    public static final List<Address> ADDRESSES = new ArrayList<>();
    public static final People PEOPLE = (new People(ID_PEOPLE, NAME, DATE, ADDRESSES));

    public static final long ID_ADDRESS = 1L;
    public static final String PUBLIC_PLACE = "Rua 1";
    public static final String ZIP_CODE = "55818-585";
    public static final String NUMBER = "22-A";
    public static final String CITY = "Carpina";
    public static final boolean MAIN_ADDRESS = true;
    public static final int INDEX = 0;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Autowired
    private PeopleServiceImpl peopleServiceImpl;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AddressMapper addressMapper;

    @MockBean
    private PeopleRepository peopleRepository;

    private Address address;
    private AddressResponseDto addressResponseDto;
    private AddressRequestDto addressRequestDto;
    private Optional<Address> optionalAddress;

    private People people;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPeople();
    }

    @Test
    void whenCreateThenReturnSuccess() {


        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(people));
        when(addressMapper.toAddress(Mockito.any())).thenReturn(address);
        when(addressRepository.save(Mockito.any())).thenReturn(address);
        when(addressMapper.toAddressResponseDto(Mockito.any())).thenReturn(addressResponseDto);

        AddressResponseDto response = addressServiceImpl.registerAddress(addressRequestDto);

        assertNotNull(response);
        assertEquals(AddressResponseDto.class, response.getClass());
        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PEOPLE, response.getIdPeople());
    }

    @Test
    void whenFindAllThenReturnAnListOfAddress() {
        when(addressRepository.findAll()).thenReturn(List.of(address));
        when(addressMapper.toAddressResponseDto(Mockito.any())).thenReturn(addressResponseDto);

        List<AddressResponseDto> response = addressServiceImpl.getAllAddress();


        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(AddressResponseDto.class, response.get(INDEX).getClass());

        assertEquals(ID_ADDRESS, response.get(INDEX).getIdAddress());
        assertEquals(PUBLIC_PLACE, response.get(INDEX).getPublicPlace());
        assertEquals(ZIP_CODE, response.get(INDEX).getZipCode());
        assertEquals(NUMBER, response.get(INDEX).getNumber());
        assertEquals(CITY, response.get(INDEX).getCity());
        assertEquals(MAIN_ADDRESS, response.get(INDEX).isMainAddress());
        assertEquals(ID_PEOPLE, response.get(INDEX).getIdPeople());
    }

    @Test
    void whenValidateListAddressSuccess() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        when(addressRepository.findAll()).thenReturn(addressList);

        when(addressMapper.toAddressResponseDto(Mockito.any())).thenReturn(addressResponseDto);
        List<Address> response = addressServiceImpl.validateListAddress();

        assertEquals(ID_ADDRESS, response.get(INDEX).getIdAddress());
        assertEquals(PUBLIC_PLACE, response.get(INDEX).getPublicPlace());
        assertEquals(ZIP_CODE, response.get(INDEX).getZipCode());
        assertEquals(NUMBER, response.get(INDEX).getNumber());
        assertEquals(CITY, response.get(INDEX).getCity());
        assertEquals(MAIN_ADDRESS, response.get(INDEX).isMainAddress());
        assertEquals(PEOPLE, response.get(INDEX).getPeople());

        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void whenValidateListThenReturnAnListOfAddress() {
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());

        try {
            addressServiceImpl.validateListAddress();
        } catch (Exception ex) {
            assertEquals("Nenhum endereço encontrado!", ex.getMessage());
        }
    }

    @Test
    void whenFindByIdThenReturnAnAddressInstance() {
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(optionalAddress);
        when(addressMapper.toAddressResponseDto(Mockito.any())).thenReturn(addressResponseDto);

        AddressResponseDto response = addressServiceImpl.getAddresById(ID_ADDRESS);

        assertNotNull(response);

        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PEOPLE, response.getIdPeople());

        verify(addressRepository, times(1)).findById(ID_ADDRESS);

    }

    @Test
    void whenFindByIdThenRuntimeException() {
        when(peopleRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Endereço com id "
                + ID_ADDRESS
                + " não cadastrado!"));

        try {
            peopleServiceImpl.getPeopleById(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenValidatePeopleWithSuccess() {
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));
        Address response = addressServiceImpl.validateAddres(ID_ADDRESS);

        assertNotNull(response);
        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(PEOPLE, response.getPeople());

        verify(addressRepository, times(1)).findById(ID_ADDRESS);
    }

    @Test
    void whenValidateAddressThenRuntimeException() {
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);

        try {
            addressServiceImpl.validateAddres(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(people));
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);
        when(addressRepository.save(Mockito.any())).thenReturn(address);
        when(addressMapper.toAddressResponseDto(address)).thenReturn(addressResponseDto);

        AddressResponseDto response = addressServiceImpl.updateAddress(addressResponseDto.getIdAddress(),
                new AddressRequestDto(PUBLIC_PLACE, ZIP_CODE,
                        NUMBER, CITY, MAIN_ADDRESS,
                        ID_PEOPLE));

        assertNotNull(response);
        assertEquals(AddressResponseDto.class, response.getClass());
        assertEquals(ID_ADDRESS, response.getIdAddress());
        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PEOPLE, response.getIdPeople());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(people));
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(optionalAddress);
        when(addressMapper.toAddressResponseDto(Mockito.any())).thenReturn(addressResponseDto);

        try {
            optionalAddress.get().setIdAddress(2L);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getMessage());
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);
        String result = addressServiceImpl.deleteAddres(ID_ADDRESS);
        assertEquals("Endereço com o ID " + ID_ADDRESS + " excluído com sucesso!", result);
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    void whenDeleteWithAlertException() {
        when(addressRepository.findById(anyLong())).thenThrow(new RuntimeException("Endereço com id "
                + ID_ADDRESS
                + " não cadastrado!"));

        try {
            addressServiceImpl.deleteAddres(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    private void startPeople() {
        people = new People(ID_PEOPLE, NAME, DATE, ADDRESSES);
        address = new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, MAIN_ADDRESS, people);
        addressResponseDto = new AddressResponseDto(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY,
                MAIN_ADDRESS, ID_PEOPLE);
        addressRequestDto = new AddressRequestDto(PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, MAIN_ADDRESS, ID_PEOPLE);
        optionalAddress = Optional.of(new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY,
                MAIN_ADDRESS, people));
    }
}
