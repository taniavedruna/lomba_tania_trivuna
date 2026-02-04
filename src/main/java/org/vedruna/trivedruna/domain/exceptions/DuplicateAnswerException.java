package org.vedruna.trivedruna.domain.exceptions;

public class DuplicateAnswerException extends RuntimeException {
    public DuplicateAnswerException() {
        super("El usuario ya ha respondido esta pregunta");
    }
}
