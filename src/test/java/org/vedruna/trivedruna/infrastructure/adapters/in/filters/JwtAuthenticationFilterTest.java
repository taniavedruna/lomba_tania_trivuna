package org.vedruna.trivedruna.infrastructure.adapters.in.filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Mock private JWTProvider jwtProvider;
  @Mock private UserDetailsService userDetailsService;
  @Mock private InboundConverter inboundConverter;
  @Mock private OutboundConverter outboundConverter;

  @AfterEach
  void cleanup() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void continuesChainWhenNoAuthorizationHeader() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    filter.doFilter(request, response, chain);

    verify(chain).doFilter(request, response);
    verifyNoInteractions(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void continuesChainWhenTokenInvalid() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer bad-token");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    when(jwtProvider.getUsernameFromAccessToken("bad-token")).thenThrow(new JwtException("invalid"));

    filter.doFilter(request, response, chain);

    verify(chain).doFilter(request, response);
    verifyNoInteractions(userDetailsService, inboundConverter, outboundConverter);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void continuesChainWhenAuthorizationNotBearer() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Basic abc");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    filter.doFilter(request, response, chain);

    verify(chain).doFilter(request, response);
    verifyNoInteractions(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void continuesChainWhenUnexpectedException() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer boom");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    when(jwtProvider.getUsernameFromAccessToken("boom")).thenThrow(new IllegalStateException("boom"));

    filter.doFilter(request, response, chain);

    verify(chain).doFilter(request, response);
    verifyNoInteractions(userDetailsService, inboundConverter, outboundConverter);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void authenticatesWhenTokenValid() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer good-token");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    var userEntity = new UserEntity();
    userEntity.setUsername("john");
    var userModel = new UserModel();
    userModel.setUsername("john");
    var userDTO = new UserDTO();
    userDTO.setUsername("john");
    userDTO.setRole("USER");

    when(jwtProvider.getUsernameFromAccessToken("good-token")).thenReturn("john");
    when(userDetailsService.loadUserByUsername("john")).thenReturn(userEntity);
    when(outboundConverter.toUserModel(userEntity)).thenReturn(userModel);
    when(inboundConverter.toUserDTO(userModel)).thenReturn(userDTO);
    when(jwtProvider.isAccessTokenValid("good-token", userModel)).thenReturn(true);

    filter.doFilter(request, response, chain);

    var authentication = SecurityContextHolder.getContext().getAuthentication();
    assertThat(authentication).isInstanceOf(UsernamePasswordAuthenticationToken.class);
    assertThat(authentication.getPrincipal()).isSameAs(userDTO);
    verify(chain).doFilter(request, response);
  }

  @Test
  void doesNotAuthenticateWhenTokenInvalidForUser() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer bad-user-token");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    var userEntity = new UserEntity();
    userEntity.setUsername("john");
    var userModel = new UserModel();
    userModel.setUsername("john");
    var userDTO = new UserDTO();
    userDTO.setUsername("john");
    userDTO.setRole("USER");

    when(jwtProvider.getUsernameFromAccessToken("bad-user-token")).thenReturn("john");
    when(userDetailsService.loadUserByUsername("john")).thenReturn(userEntity);
    when(outboundConverter.toUserModel(userEntity)).thenReturn(userModel);
    when(inboundConverter.toUserDTO(userModel)).thenReturn(userDTO);
    when(jwtProvider.isAccessTokenValid("bad-user-token", userModel)).thenReturn(false);

    filter.doFilter(request, response, chain);

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    verify(chain).doFilter(request, response);
  }

  @Test
  void doesNotAuthenticateWhenTokenValidButAlreadyAuthenticated() throws Exception {
    var filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService, inboundConverter, outboundConverter);
    var request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer good-token");
    var response = mock(HttpServletResponse.class);
    var chain = mock(FilterChain.class);

    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("existing", null));

    when(jwtProvider.getUsernameFromAccessToken("good-token")).thenReturn("john");

    filter.doFilter(request, response, chain);

    verify(chain).doFilter(request, response);
    verifyNoInteractions(userDetailsService, inboundConverter, outboundConverter);
  }
}
