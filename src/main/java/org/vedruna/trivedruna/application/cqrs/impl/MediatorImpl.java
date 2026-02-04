package org.vedruna.trivedruna.application.cqrs.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.cqrs.Request;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;

import lombok.Data;

@Component
@Data
public class MediatorImpl implements Mediator {

    //Mapa que asocia cada tipo de Request con su RequestHandler correspondiente.
    final Map<Class<?>, RequestHandler<?, ?>> requestHandlerMap;

    //El constructor recibe una lista de RequestHandlers y construye el mapa.
    public MediatorImpl(List<RequestHandler<?, ?>> requestHandlers) {
        requestHandlerMap = requestHandlers.stream()
                .collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
    }


    @Override
    public <O, I extends Request<O>> O dispatch(I inputRequest) {
        RequestHandler<I, O> requestHandler = (RequestHandler<I, O>) requestHandlerMap.get(inputRequest.getClass());
        if (requestHandler == null) {
            throw new UnsupportedOperationException("No handler found for request type: " + inputRequest.getClass());

        }
        return requestHandler.handle(inputRequest);
    }
    
}
