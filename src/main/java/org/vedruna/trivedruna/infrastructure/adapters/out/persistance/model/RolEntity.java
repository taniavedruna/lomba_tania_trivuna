package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    Integer rolId;

    @Column(name = "rol_name", nullable = false, unique = true, length = 20)
    String rolName;
}

