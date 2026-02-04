package org.vedruna.trivedruna.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.vedruna.trivedruna.infrastructure.adapters.in.filters.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

  /** Filtro personalizado para procesar y validar JSON Web Tokens (JWT). */
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Proveedor de autenticación configurado para la carga de usuarios y
   * codificación de contraseñas
   * (definido en ApplicationConfig).
   */
  private final AuthenticationProvider authProvider;

  /**
   * Define la cadena de filtros de seguridad (SecurityFilterChain) que
   * interceptará todas las
   * peticiones HTTP.
   *
   * <p>
   * Esta es la configuración central de la seguridad de la aplicación.
   *
   * @param http Objeto para configurar Spring Security a nivel HTTP.
   * @return La cadena de filtros de seguridad construida.
   * @throws Exception Si ocurre un error durante la configuración.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        // 1. Habilita CORS y deshabilita la protección CSRF
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        // 2. Configura las reglas de autorización para las peticiones HTTP
        .authorizeHttpRequests(
            authReq -> authReq
                // Permite acceso sin autenticación a endpoints públicos y de
                // documentación
                .requestMatchers("/v3/api-docs")
                .permitAll() // OpenAPI/Swagger Docs
                .requestMatchers("/swagger-ui/index.html")
                .permitAll() // Swagger
                .requestMatchers("/error")
                .permitAll() // Error
                .requestMatchers("/h2-console/**")
                .permitAll() // H2 Console
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("/api/v1/courses")
                .permitAll()
                .requestMatchers("/api/v1/categories")
                .permitAll()
                .requestMatchers("/actuator/**")
                .permitAll()
                // Cualquier otra petición requiere autenticación
                .anyRequest()
                .authenticated())
        // 3. Configura la gestión de sesiones como STATELESS
        // Esto es crucial para el uso de JWT, ya que no se almacenan estados de sesión
        // en el servidor
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 4. Asigna el proveedor de autenticación personalizado
        .authenticationProvider(authProvider)
        // 5. Agrega el filtro JWT antes del filtro estándar de autenticación por nombre
        // de usuario y contraseña
        // Esto asegura que cada petición con un JWT sea autenticada antes de llegar a
        // los recursos.
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        // Permitir el uso de Frames (indispensable para ver la interfaz de H2)
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
