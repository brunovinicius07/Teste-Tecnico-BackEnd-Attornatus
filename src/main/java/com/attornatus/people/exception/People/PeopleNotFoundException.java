package com.attornatus.people.exception.People;

import com.attornatus.people.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class PeopleNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Pessoa não localizada";
    public PeopleNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
