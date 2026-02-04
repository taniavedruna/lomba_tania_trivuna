package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * DTO que representa a un usuario autenticado en el sistema.
 *
 * <p>
 * Implementa {@link UserDetails} para integrarse con Spring Security.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO implements UserDetails {

  private Integer userId;
  private String username;
  @JsonIgnore
  private String password;
  private String email;
  private String role;
  private String courseName;
  private String avatarUrl;
  private String name;
  private String surname1;
  private String surname2;
  private Integer userScore;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role == null || role.isBlank()) {
      return Collections.emptyList();
    }

    String authorityName = role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
    return List.of(new SimpleGrantedAuthority(authorityName));
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
