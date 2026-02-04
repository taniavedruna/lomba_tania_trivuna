package org.vedruna.trivedruna.infrastructure.adapters.in.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.vedruna.trivedruna.domain.exceptions.InvalidAnswerOptionException;
import org.vedruna.trivedruna.domain.exceptions.QuestionNotFoundException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
  private final HttpServletRequest request = mock(HttpServletRequest.class, invocation -> null);

  @Test
  void devuelve400ParaRespuestaInvalida() {
    var response =
        handler.handleInvalidAnswer(new InvalidAnswerOptionException(), request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().message()).contains("opción válida");
  }

  @Test
  void devuelve404ParaPreguntaNoEncontrada() {
    var response =
        handler.handleQuestionNotFound(new QuestionNotFoundException(), request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void devuelve500ParaGenericas() {
    var response = handler.handleGeneric(new RuntimeException("boom"), request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
