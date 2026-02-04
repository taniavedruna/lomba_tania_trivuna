package org.vedruna.trivedruna.infrastructure.adapters.in.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.LoginRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RefreshRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RegisterRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.AuthResponseDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Interfaz que define los endpoints para la autenticación y gestión de sesiones de usuario.
 *
 * <p>{@code @Tag}: Define la agrupación de estas operaciones para la documentación de OpenAPI.
 * {@code @Validated}: Habilita la validación de argumentos en los métodos. {@code @RequestMapping}:
 * Define la ruta base para todos los endpoints de este controlador.
 */

@Validated
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Registro, login y gestión de tokens")
public interface AuthController {

  /**
   * Endpoint para registrar un nuevo usuario en el sistema.
   *
   * @param request DTO con la información necesaria para el registro.
   * @return {@link ResponseEntity} con los datos del usuario registrado y status 201 (CREATED).
   */
  
  @PostMapping("/register")
  @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario y devuelve sus datos")
  ResponseEntity<UserRegisteredDTO> register(@Valid @RequestBody RegisterRequestDTO request);

  /**
   * Endpoint para autenticar un usuario y obtener tokens de acceso.
   *
   * @param request DTO con las credenciales (usuario y contraseña).
   * @return {@link ResponseEntity} con el token de acceso generado y status 200 (OK).
   */
  
  @PostMapping("/login")
  @Operation(summary = "Login", description = "Devuelve tokens de acceso y refresco")
  ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request);

  /**
   * Endpoint para obtener la información del usuario actualmente autenticado.
   *
   * @param userLogueado El DTO del usuario extraído del contexto de seguridad.
   * @return {@link ResponseEntity} con los detalles del usuario actual.
   */
 
  @GetMapping("/me")
  @Operation(summary = "Usuario actual", description = "Devuelve los datos del usuario autenticado")
  ResponseEntity<UserDTO> me(@AuthenticationPrincipal UserDTO userLogueado);

  /**
   * Endpoint para renovar el token de acceso utilizando un token de refresco.
   *
   * @param request DTO que contiene el Refresh Token.
   * @return {@link ResponseEntity} con el nuevo token de acceso generado.
   */
 
  @PostMapping("/refresh")
  @Operation(summary = "Renovar token", description = "Genera nuevo access token a partir de refresh token válido")
  ResponseEntity<AuthResponseDTO> refreshToken(@Valid @RequestBody RefreshRequestDTO request);
}
