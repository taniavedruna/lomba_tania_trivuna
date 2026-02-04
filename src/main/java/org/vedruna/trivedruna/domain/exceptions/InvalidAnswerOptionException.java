package org.vedruna.trivedruna.domain.exceptions;

/**
 * Se lanza cuando el usuario intenta responder con una opción que no pertenece a la pregunta.
 */
public class InvalidAnswerOptionException extends IllegalArgumentException {

    public InvalidAnswerOptionException() {
        super("La respuesta seleccionada no es una opción válida para esta pregunta");
    }
}
