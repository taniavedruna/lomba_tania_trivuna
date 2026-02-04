package org.vedruna.trivedruna.domain.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

    public UserNotFoundException() {
        super("Usuario no encontrado");
    }

}
