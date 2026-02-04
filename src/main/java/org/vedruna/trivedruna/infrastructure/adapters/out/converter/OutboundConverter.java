package org.vedruna.trivedruna.infrastructure.adapters.out.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vedruna.trivedruna.domain.model.CategoryModel;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.model.RolModel;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.CategoryEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.CourseEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.QuestionEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.RolEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserAnswerQuestionEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

/**
 * Interfaz Mapper para la conversión entre modelos de dominio y entidades de persistencia
 * (outbound).
 *
 * <p>{@code @Mapper}: MapStruct mapper.
 */
@Mapper(componentModel = "spring")
public interface OutboundConverter {

  /** Convierte una entidad de usuario en un modelo de dominio UserModel. */
  @Mapping(target = "coursesCourseId", source = "course.courseId")
  @Mapping(target = "courseName", source = "course.courseName")
  UserModel toUserModel(UserEntity entity);

  /** Convierte el modelo de dominio UserModel en su entidad de persistencia. */
  @Mapping(target = "course.courseId", source = "coursesCourseId")
  UserEntity toUserEntity(UserModel model);

  /** Convierte una entidad de rol en un modelo de dominio RolModel. */
  RolModel toRolModel(RolEntity entity);

  /** Convierte un modelo de dominio RolModel en entidad de rol. */
  RolEntity toRolEntity(RolModel model);

  /** Convierte una entidad de curso en un modelo de dominio CourseModel. */
  CourseModel toCourseModel(CourseEntity entity);

  /** Convierte un modelo de dominio CourseModel en entidad de curso. */
  CourseEntity toCourseEntity(CourseModel model);

  /** Convierte una entidad de categoría en un modelo de dominio CategoryModel. */
  CategoryModel toCategoryModel(CategoryEntity entity);

  /** Convierte un modelo de dominio CategoryModel en entidad de categoría. */
  CategoryEntity toCategoryEntity(CategoryModel model);

  /** Convierte una entidad de pregunta en un modelo de dominio QuestionModel. */
  @Mapping(target = "correctAnswer", source = "respuestaCorrecta")
  @Mapping(target = "categoryId", source = "category.categoryId")
  @Mapping(target = "categoryName", source = "category.categoryName")
  @Mapping(target = "courseId", ignore = true)
  @Mapping(target = "userId", source = "user.userId")
  QuestionModel toQuestionModel(QuestionEntity entity);

  /** Convierte un modelo de dominio QuestionModel en entidad de pregunta. */
  @Mapping(target = "respuestaCorrecta", source = "correctAnswer")
  @Mapping(target = "category.categoryId", source = "categoryId")
  @Mapping(target = "user.userId", source = "userId")
  QuestionEntity toQuestionEntity(QuestionModel model);

  /** Convierte una entidad de respuesta de usuario en modelo de dominio. */
  @Mapping(target = "usersUserId", source = "id.usersUserId")
  @Mapping(target = "questionsQuestionId", source = "id.questionsQuestionId")
  UserAnswerQuestionModel toUserAnswerQuestionModel(UserAnswerQuestionEntity entity);

  /** Convierte un modelo de dominio de respuesta en entidad de persistencia. */
  @Mapping(target = "id.usersUserId", source = "usersUserId")
  @Mapping(target = "id.questionsQuestionId", source = "questionsQuestionId")
  @Mapping(target = "user.userId", source = "usersUserId")
  @Mapping(target = "question.questionId", source = "questionsQuestionId")
  UserAnswerQuestionEntity toUserAnswerQuestionEntity(UserAnswerQuestionModel model);
}
