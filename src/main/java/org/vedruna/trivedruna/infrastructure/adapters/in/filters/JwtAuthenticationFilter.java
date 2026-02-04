package org.vedruna.trivedruna.infrastructure.adapters.in.filters;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

/**
 * Filtro de seguridad que intercepta todas las peticiones para validar la presencia y validez de un
 * JSON Web Token (JWT) de ACCESO en la cabecera Authorization.
 */
@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JWTProvider jwtProvider;
  private final UserDetailsService userDetailsService;
  private final InboundConverter inboundConverter;
  private final OutboundConverter outboundConverter;

  private static final String BEARER_PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String token = getTokenFromRequest(request);
    final String username;

    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      username = jwtProvider.getUsernameFromAccessToken(token);
    } catch (JwtException e) {
      log.warn(
          "Error de token JWT de Acceso (invalido/expirado) en {}: {}",
          request.getRequestURI(),
          e.getMessage());
      filterChain.doFilter(request, response);
      return;
    } catch (Exception e) {
      log.error("Error inesperado durante el procesamiento del token: {}", e.getMessage());
      filterChain.doFilter(request, response);
      return;
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UserModel userModel = outboundConverter.toUserModel((UserEntity) userDetails);
      UserDTO principal = inboundConverter.toUserDTO(userModel);

      if (jwtProvider.isAccessTokenValid(token, userModel)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Usuario autenticado exitosamente: {}", username);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
      return authHeader.substring(BEARER_PREFIX.length());
    }
    return null;
  }
}
