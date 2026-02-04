package org.vedruna.trivedruna.domain.exceptions;

import java.util.NoSuchElementException;

public class QuestionNotFoundException extends NoSuchElementException{
    
    public QuestionNotFoundException(){
        super("Pregunta no encontrada");
    }

}
