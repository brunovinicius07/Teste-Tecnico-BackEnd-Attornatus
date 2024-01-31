package com.attornatus.people.exception.People;

import com.attornatus.people.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class PeoplePresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "cpf já está cadastrado!";

    public PeoplePresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}