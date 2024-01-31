package com.attornatus.people.repositories;

import com.attornatus.people.models.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllAddressByPeopleIdPeople(Long idPeople);
}
