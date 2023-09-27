package com.attornatus.people.controllers;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "peoples")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<Object> registerPeople(@RequestBody @Valid PeopleRequestDto peopleRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(peopleService.registerPeople(peopleRequestDto));
    }
}
