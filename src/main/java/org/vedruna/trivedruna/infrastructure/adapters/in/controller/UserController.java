package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.UpdateUserRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Interfaz que define los endpoints de usuario.
 */
@Validated
@RequestMapping("/api/v1/users")
@Tag(name = "Usuarios", description = "Gestión del perfil y consulta de usuarios")
public interface UserController {

  /**
   * Endpoint para editar los datos del usuario autenticado.
   *
   * @param userLogueado usuario autenticado.
   * @param request campos editables del usuario.
   * @return datos del usuario actualizados.
   */
  @PatchMapping("/me")
  @Operation(summary = "Actualizar mis datos", description = "Edita los datos del usuario autenticado")
  ResponseEntity<UserRegisteredDTO> updateMe(
      @AuthenticationPrincipal UserDTO userLogueado, @Valid @RequestBody UpdateUserRequestDTO request);

  @Operation(summary = "Obtener usuario por id", description = "Devuelve datos básicos del usuario")
  @GetMapping("/{id}")
  ResponseEntity<UserDTO> getUserById(@PathVariable Integer id);
}
