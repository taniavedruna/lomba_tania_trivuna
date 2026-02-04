package org.vedruna.trivedruna.application.cqrs.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.vedruna.trivedruna.application.cqrs.Request;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;

class MediatorImplTest {

  @Test
  void despachaAlHandlerRegistrado() {
    var handler = new EchoHandler();
    var mediator = new MediatorImpl(List.of(handler));

    var result = mediator.dispatch(new EchoRequest("ping"));

    assertThat(result).isEqualTo("ping");
  }

  @Test
  void lanzaCuandoNoExisteHandler() {
    var mediator = new MediatorImpl(List.of(new EchoHandler()));

    assertThatThrownBy(() -> mediator.dispatch(new UnknownRequest()))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  private static class EchoRequest implements Request<String> {
    private final String value;

    EchoRequest(String value) {
      this.value = value;
    }

    String value() {
      return value;
    }
  }

  private static class UnknownRequest implements Request<String> {}

  private static class EchoHandler implements RequestHandler<EchoRequest, String> {
    @Override
    public String handle(EchoRequest inputRequest) {
      return inputRequest.value();
    }

    @Override
    public Class<EchoRequest> getRequestType() {
      return EchoRequest.class;
    }
  }
}
