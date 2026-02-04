package org.vedruna.trivedruna.infrastructure.adapters.in.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vedruna.trivedruna.domain.model.AccessToken;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.CreateQuestionRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.UpdateUserRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CourseDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.QuestionDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.LoginRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RefreshRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RegisterRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.AuthResponseDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;

/**
 * Interfaz Mapper para la conversion entre DTOs de entrada y modelos de
 * dominio.
 */
@Mapper(componentModel = "spring")
public interface InboundConverter {

  /**
   * Convierte un DTO de registro en un modelo de usuario para ser procesado por
   * el dominio.
   */
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "userScore", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "coursesCourseId", ignore = true)
  @Mapping(target = "courseName", source = "courseName")
  @Mapping(target = "avatarUrl", ignore = true)
  UserModel registerToUserModel(RegisterRequestDTO userDTO);

  /** Convierte un DTO de inicio de sesion en un modelo de usuario. */
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "avatarUrl", ignore = true)
  @Mapping(target = "name", ignore = true)
  @Mapping(target = "surname1", ignore = true)
  @Mapping(target = "surname2", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "userScore", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "coursesCourseId", ignore = true)
  @Mapping(target = "courseName", ignore = true)
  UserModel loginToUserModel(LoginRequestDTO userDTO);

  /** Convierte un DTO de actualizacion en un modelo de usuario. */
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "userScore", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "coursesCourseId", ignore = true)
  @Mapping(target = "courseName", ignore = true)
  UserModel toUserModel(UpdateUserRequestDTO request);

  /** Convierte un DTO de creación de pregunta a su modelo de dominio. */
  @Mapping(target = "questionId", ignore = true)
  @Mapping(target = "categoryName", ignore = true)
  @Mapping(target = "isApproved", ignore = true)
  QuestionModel toQuestionModel(CreateQuestionRequestDTO dto);

  @Mapping(target = "role", source = "role.rolName")
  UserDTO toUserDTO(UserModel userModel);

  /** Convierte un modelo de pregunta en su DTO de salida. */
  QuestionDTO toQuestionDTO(QuestionModel questionModel);

  /** Convierte un modelo de curso en su DTO de salida. */
  CourseDTO toCourseDTO(CourseModel model);

  /** Convierte un modelo de usuario recien registrado al DTO de salida. */
  @Mapping(target = "role", source = "role.rolName")
  UserRegisteredDTO toUserRegisteredDTO(UserModel userModel);

  /**
   * Convierte un objeto de token de dominio a su DTO de respuesta para la API.
   */
  AuthResponseDTO toAuthResponseDTO(AccessToken accessToken);

  /**
   * Extrae el Refresh Token de una solicitud para convertirlo en un objeto de
   * dominio.
   */
  @Mapping(target = "accessToken", ignore = true)
  @Mapping(target = "expiresIn", ignore = true)
  @Mapping(target = "scope", ignore = true)
  AccessToken toAccessToken(RefreshRequestDTO refreshTokenRequest);
}
