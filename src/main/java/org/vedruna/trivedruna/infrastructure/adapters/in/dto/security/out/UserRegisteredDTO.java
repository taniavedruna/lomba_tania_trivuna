package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la respuesta tras el registro exitoso de un usuario.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredDTO {

  private Integer userId;
  private String username;
  private String email;
  private String role;
  private String avatarUrl;
  private String name;
  private String surname1;
  private String surname2;
  private String courseName;
}
