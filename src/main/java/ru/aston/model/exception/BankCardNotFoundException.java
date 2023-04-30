package ru.aston.model.exception;

import java.text.MessageFormat;

public class BankCardNotFoundException extends RuntimeException {

    public BankCardNotFoundException(final Long id){
        super(MessageFormat.format("Could not find item with id: {0}", id));
    }
}
