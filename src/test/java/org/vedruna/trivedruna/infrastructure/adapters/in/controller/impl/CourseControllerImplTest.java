package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.course.GetAllCourseRequest;
import org.vedruna.trivedruna.application.query.getall.course.GetAllCourseResponse;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CourseDTO;

@ExtendWith(MockitoExtension.class)
class CourseControllerImplTest {

  @Mock private Mediator mediator;
  @Mock private InboundConverter inboundConverter;

  @InjectMocks private CourseControllerImpl controller;

  @Test
  void devuelveCursosConvertidosEnDto() {
    var courseModel = new CourseModel();
    courseModel.setCourseId(1);
    courseModel.setCourseName("Math");
    var response = new GetAllCourseResponse(List.of(courseModel));
    when(mediator.dispatch(any(GetAllCourseRequest.class))).thenReturn(response);

    var dto = new CourseDTO();
    dto.setCourseId(1);
    dto.setCourseName("Math");
    when(inboundConverter.toCourseDTO(courseModel)).thenReturn(dto);

    var httpResponse = controller.getAllCourses();

    assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(httpResponse.getBody()).containsExactly(dto);
  }
}
