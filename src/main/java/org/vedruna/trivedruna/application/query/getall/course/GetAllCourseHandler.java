package org.vedruna.trivedruna.application.query.getall.course;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllCourseHandler implements RequestHandler<GetAllCourseRequest, GetAllCourseResponse> {

    CoursesJpaRepository coursesJpaRepository;

    @Override
    public GetAllCourseResponse handle(GetAllCourseRequest inputRequest) {
        log.info("Obtener todos los cursos");
        return new GetAllCourseResponse(coursesJpaRepository.findAll());
    }

    @Override
    public Class<GetAllCourseRequest> getRequestType() {
        return GetAllCourseRequest.class;
    }
}
