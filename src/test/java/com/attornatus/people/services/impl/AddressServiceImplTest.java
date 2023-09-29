package com.attornatus.people.services.impl;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceImplTest {

    public static final long ID_PEOPLE          = 1L;
    public static final String NAME             = "João";
    public static final LocalDate DATE          = LocalDate.of(1997, 7, 15);
    public static final List<Address> ADDRESSES = new ArrayList<>();

    public static final long ID_ADDRESS         = 1L;
    public static final String PUBLIC_PLACE     = "Rua 1";
    public static final String ZIP_CODE         = "55818-585";
    public static final String NUMBER           = "22-A";
    public static final String CITY             = "Carpina";
    public static final boolean MAIN_ADDRESS    = true;
    public static final int INDEX               = 0;

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
    private Optional<Address> optionalAddress;

    private People people;
    private Optional<People> peopleOptional;
    private PeopleRequestDto peopleRequestDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startPeople();
    }

    @Test
    void whenCreateThenReturnSuccess() {

    }

    @Test
    void getAllAddress() {
    }

    @Test
    void validateListAddress() {
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
    void validateAddres() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddres() {
    }

    private void startPeople(){
        peopleOptional = Optional.of(new People(ID_PEOPLE, NAME, DATE, ADDRESSES));
        people = new People(ID_PEOPLE, NAME, DATE, ADDRESSES);
        address = new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, MAIN_ADDRESS, people );
        addressResponseDto = new AddressResponseDto(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, MAIN_ADDRESS, ID_PEOPLE);
        optionalAddress = Optional.of(new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, MAIN_ADDRESS, people));
    }
}