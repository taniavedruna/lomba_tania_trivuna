package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.RolEntity;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Integer> {
    Optional<RolEntity> findByRolName(String rolName);
}
