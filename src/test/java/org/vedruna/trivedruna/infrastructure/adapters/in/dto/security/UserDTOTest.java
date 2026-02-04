package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserDTOTest {

  @Test
  void authoritiesEmptyWhenRoleMissing() {
    var dto = new UserDTO();
    dto.setRole(null);

    assertThat(dto.getAuthorities()).isEmpty();

    dto.setRole("   ");
    assertThat(dto.getAuthorities()).isEmpty();
  }

  @Test
  void authoritiesAddsRolePrefix() {
    var dto = new UserDTO();
    dto.setRole("user");

    assertThat(dto.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
  }

  @Test
  void authoritiesKeepsExistingRolePrefix() {
    var dto = new UserDTO();
    dto.setRole("ROLE_ADMIN");

    assertThat(dto.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
  }
}
