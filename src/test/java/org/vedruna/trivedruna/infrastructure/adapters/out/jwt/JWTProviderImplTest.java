package org.vedruna.trivedruna.infrastructure.adapters.out.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

@ExtendWith(MockitoExtension.class)
class JWTProviderImplTest {

  private static final String ACCESS_KEY = base64Key("access-secret-32-bytes-long-123456");
  private static final String REFRESH_KEY = base64Key("refresh-secret-32-bytes-long-1234");

  @Mock private OutboundConverter outboundConverter;

  private JWTProviderImpl provider;

  @BeforeEach
  void setUp() {
    provider = new JWTProviderImpl(outboundConverter);
    ReflectionTestUtils.setField(provider, "accessTokenSecretKey", ACCESS_KEY);
    ReflectionTestUtils.setField(provider, "refreshTokenSecretKey", REFRESH_KEY);
    ReflectionTestUtils.setField(provider, "accessTokenExpiration", 5_000L);
    ReflectionTestUtils.setField(provider, "refreshTokenExpiration", 10_000L);
  }

  @Test
  void generatesAccessTokenAndValidates() {
    var user = userModel("john", "john@example.com");
    var userEntity = new UserEntity();
    userEntity.setUsername("john");

    when(outboundConverter.toUserEntity(user)).thenReturn(userEntity);

    var token = provider.generateAccessToken(user);

    assertThat(provider.getUsernameFromAccessToken(token)).isEqualTo("john");
    assertThat(provider.isAccessTokenExpired(token)).isFalse();
    assertThat(provider.isAccessTokenValid(token, user)).isTrue();
    assertThat(provider.getAccessTokenExpiresIn()).isEqualTo(5L);
  }

  @Test
  void accessTokenInvalidWhenUsernameMismatch() {
    var tokenUser = userModel("john", "john@example.com");
    var otherUser = userModel("maria", "maria@example.com");
    var otherEntity = new UserEntity();
    otherEntity.setUsername("maria");

    when(outboundConverter.toUserEntity(otherUser)).thenReturn(otherEntity);

    var token = provider.generateAccessToken(tokenUser);

    assertThat(provider.isAccessTokenValid(token, otherUser)).isFalse();
  }

  @Test
  void refreshTokenInvalidWhenUsernameMismatch() {
    var tokenUser = userModel("john", "john@example.com");
    var otherUser = userModel("maria", "maria@example.com");
    var otherEntity = new UserEntity();
    otherEntity.setUsername("maria");

    when(outboundConverter.toUserEntity(otherUser)).thenReturn(otherEntity);

    var token = provider.generateRefreshToken(tokenUser);

    assertThat(provider.isRefreshTokenValid(token, otherUser)).isFalse();
    assertThat(provider.getRefreshTokenExpiresIn()).isEqualTo(10L);
  }

  private static UserModel userModel(String username, String email) {
    var user = new UserModel();
    user.setUsername(username);
    user.setEmail(email);
    return user;
  }

  private static String base64Key(String value) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }
}
