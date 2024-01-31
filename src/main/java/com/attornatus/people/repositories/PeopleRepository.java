package com.attornatus.people.repositories;

import com.attornatus.people.models.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    People findPeopleByIdPeople(Long idPeople);

    Optional<People> findPeopleByCpf(String cpf);
}
