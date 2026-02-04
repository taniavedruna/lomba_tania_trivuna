package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false, length = 60)
    String password;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "name", length = 45)
    String name;

    @Column(name = "surname1", length = 45)
    String surname1;

    @Column(name = "surname2", length = 45)
    String surname2;

    @Column(name = "create_date")
    LocalDateTime createDate;

    @Column(name = "user_score", nullable = false)
    Integer userScore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_rol_id", nullable = false)
    RolEntity role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courses_course_id", nullable = false)
    CourseEntity course;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si el rol es nulo (aunque esto debería ser prevenido por la lógica de negocio/BD)
        if (this.role == null || this.role.getRolName() == null) {
            return Collections.emptyList();
        }
        
        // Mapea el nombre del rol a una autoridad de Spring Security
        // Se añade el prefijo "ROLE_" como buena práctica de Spring Security.
        String authorityName = "ROLE_" + this.role.getRolName().toUpperCase();
        
        return List.of(new SimpleGrantedAuthority(authorityName));
    }


}

