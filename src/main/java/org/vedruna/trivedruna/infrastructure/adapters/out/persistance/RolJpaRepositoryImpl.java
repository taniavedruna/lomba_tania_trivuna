package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.RolModel;
import org.vedruna.trivedruna.domain.ports.outbound.RolJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.RolRepository;

@Service
@AllArgsConstructor
public class RolJpaRepositoryImpl implements RolJpaRepository {

  private final RolRepository rolRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public Optional<RolModel> findByName(String name) {
    return rolRepository.findByRolName(name).map(outboundConverter::toRolModel);
  }
}
