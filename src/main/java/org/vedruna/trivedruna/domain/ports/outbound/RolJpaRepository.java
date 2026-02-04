package org.vedruna.trivedruna.domain.ports.outbound;

import java.util.Optional;

import org.vedruna.trivedruna.domain.model.RolModel;

public interface RolJpaRepository {

    Optional<RolModel> findByName(String name);
    
}
