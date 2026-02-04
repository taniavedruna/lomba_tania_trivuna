package org.vedruna.trivedruna.domain.exceptions;

import java.util.NoSuchElementException;

public class CategoryNotFoundException extends NoSuchElementException{

    public CategoryNotFoundException(){
        super("Categoria no encontrada");
    }

}
