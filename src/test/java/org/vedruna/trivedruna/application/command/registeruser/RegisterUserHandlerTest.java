package org.vedruna.trivedruna.application.command.registeruser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.domain.model.RolModel;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.PasswordEncoderI;
import org.vedruna.trivedruna.domain.ports.outbound.RolJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

class RegisterUserHandlerTest {

  @Mock private UserJpaRepository userJpaRepository;
  @Mock private CoursesJpaRepository coursesJpaRepository;
  @Mock private RolJpaRepository rolJpaRepository;
  @Mock private PasswordEncoderI passwordEncoder;
  @InjectMocks private RegisterUserHandler handler;

  private UserModel user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new UserModel();
    user.setPassword("plain");
  }

  @Test
  void registraUsuarioAsignandoRolCursoYHash() {
    RolModel rol = new RolModel();
    rol.setRolName("USER");
    CourseModel course = new CourseModel();
    course.setCourseId(5);

    when(rolJpaRepository.findByName("USER")).thenReturn(Optional.of(rol));
    when(coursesJpaRepository.findByCourseName("DAW")).thenReturn(Optional.of(course));
    when(passwordEncoder.encode("plain")).thenReturn("hashed");
    when(userJpaRepository.saveUser(any(UserModel.class))).thenAnswer(inv -> inv.getArgument(0));

    var resp = handler.handle(new RegisterUserRequest(user, "DAW"));

    assertThat(resp.getUser()).isNotNull();
    assertThat(resp.getUser().getRole()).isSameAs(rol);
    assertThat(resp.getUser().getCoursesCourseId()).isEqualTo(5);
    assertThat(resp.getUser().getPassword()).isEqualTo("hashed");
    verify(userJpaRepository).saveUser(any(UserModel.class));
  }

  @Test
  void fallaSiNoHayCurso() {
    when(rolJpaRepository.findByName("USER")).thenReturn(Optional.of(new RolModel()));
    when(coursesJpaRepository.findByCourseName("XX")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> handler.handle(new RegisterUserRequest(user, "XX")))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
