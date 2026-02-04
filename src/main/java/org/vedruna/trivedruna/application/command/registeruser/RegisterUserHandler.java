package org.vedruna.trivedruna.application.command.registeruser;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.domain.model.RolModel;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.PasswordEncoderI;
import org.vedruna.trivedruna.domain.ports.outbound.RolJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

/**
 * Manejador encargado de la logica de registro de nuevos usuarios.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * RegisterUserRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Indica que esta clase es un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para la inyeccion de
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegisterUserHandler
    implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

  /** Repositorio para la persistencia de datos de usuario. */
  private final UserJpaRepository userJpaRepository;

  private final CoursesJpaRepository coursesJpaRepository;

  private final RolJpaRepository rolJpaRepository;

  /** Codificador para asegurar las contrasenas de los usuarios. */
  private final PasswordEncoderI passwordEncoder;

  /**
   * Procesa el registro de un nuevo usuario.
   *
   * <p>1. Registra el inicio de la solicitud de registro. 2. Asigna el rol predeterminado.
   * 3. Establece fechas y valores por defecto. 4. Codifica la contrasena antes de guardar.
   * 5. Persiste el usuario y devuelve la respuesta.
   *
   * @param inputRequest El objeto de solicitud con los datos del nuevo usuario.
   * @return {@link RegisterUserResponse} con el usuario registrado.
   */
  @Override
  public RegisterUserResponse handle(RegisterUserRequest inputRequest) {
    log.info("Handling RegisterUserRequest");
    RolModel defaultRole =
        rolJpaRepository
            .findByName("USER")
            .orElseThrow(() -> new IllegalStateException("Default role USER not found"));
    inputRequest.getUser().setRole(defaultRole);
    log.info("Hashing password");
    String hashedPass = passwordEncoder.encode(inputRequest.getUser().getPassword());
    inputRequest.getUser().setPassword(hashedPass);
    if (inputRequest.getUser().getCreateDate() == null) {
      inputRequest.getUser().setCreateDate(LocalDateTime.now());
    }
    if (inputRequest.getUser().getUserScore() == null) {
      inputRequest.getUser().setUserScore(0);
    }
    if (inputRequest.getUser().getCoursesCourseId() == null) {
      String courseName = inputRequest.getCourseName();
      if (courseName == null || courseName.isBlank()) {
        throw new IllegalArgumentException("courseName is required");
      }
      CourseModel course =
          coursesJpaRepository
              .findByCourseName(courseName.trim())
              .orElseThrow(() -> new IllegalArgumentException("courseName not found"));
      inputRequest.getUser().setCoursesCourseId(course.getCourseId());
    }
    return new RegisterUserResponse(userJpaRepository.saveUser(inputRequest.getUser()));
  }

  /**
   * Indica el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link RegisterUserRequest}.
   */
  @Override
  public Class<RegisterUserRequest> getRequestType() {
    return RegisterUserRequest.class;
  }
}
