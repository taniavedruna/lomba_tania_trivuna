package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Query("update UserEntity u set u.userScore = u.userScore + :score where u.userId = :userId")
    int incrementScore(@Param("userId") Integer userId, @Param("score") Integer score);
}
