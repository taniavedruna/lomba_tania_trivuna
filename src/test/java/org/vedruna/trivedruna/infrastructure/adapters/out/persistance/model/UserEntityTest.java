package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserEntityTest {

  @Test
  void authoritiesEmptyWhenRoleMissing() {
    var entity = new UserEntity();
    entity.setRole(null);

    assertThat(entity.getAuthorities()).isEmpty();

    var role = new RolEntity();
    role.setRolName(null);
    entity.setRole(role);

    assertThat(entity.getAuthorities()).isEmpty();
  }

  @Test
  void authoritiesAddsRolePrefix() {
    var entity = new UserEntity();
    var role = new RolEntity();
    role.setRolName("admin");
    entity.setRole(role);

    assertThat(entity.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
  }
}
