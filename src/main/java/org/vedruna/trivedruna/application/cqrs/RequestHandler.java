package org.vedruna.trivedruna.application.cqrs;

//El tipo generico I es una Request (input) y el O es el output.
public interface RequestHandler<I extends Request<O>, O> {
    //El metodo handle recibe una Request, se encarga de ejecutarla
    //y devuelve el output.
    O handle(I inputRequest);

    //Obtiene el tipo de Request que maneja este handler.
    Class<I> getRequestType();
}
