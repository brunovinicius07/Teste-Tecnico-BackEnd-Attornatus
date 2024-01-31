package com.attornatus.people.exception.Address;

import com.attornatus.people.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class AddressIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Endereço já existe!";

    public AddressIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}