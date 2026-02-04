package org.vedruna.trivedruna.application.cqrs;

public interface Mediator {
    //Despacha la Request al RequestHandler correspondiente y devuelve 
    //el output.
    <O, I extends Request<O>> O dispatch(I inputRequest);
}
