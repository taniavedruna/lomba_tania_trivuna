package org.vedruna.trivedruna.infrastructure.adapters.out.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;

/**
 * Implementación de {@link JWTProvider} para la gestión de JSON Web Tokens (JWT).
 *
 * <p>Utiliza la librería io.jsonwebtoken (JJWT) para firmar y validar Access y Refresh tokens.
 *
 * <p>{@code @Component}: Integración con el contexto de Spring.
 */
@Component
public class JWTProviderImpl implements JWTProvider {
  /** Clave secreta para firmar tokens de acceso. */
  @Value("${auth.access-token-secret-key}")
  private String accessTokenSecretKey;

  /** Duración de validez de los tokens de acceso. */
  @Value("${auth.access-token-expiration}")
  private Long accessTokenExpiration;

  /** Clave secreta para firmar tokens de refresco. */
  @Value("${auth.refresh-token-secret-key}")
  private String refreshTokenSecretKey;

  /** Duración de validez de los tokens de refresco. */
  @Value("${auth.refresh-token-expiration}")
  private Long refreshTokenExpiration;

  /** Conversor para transformar entre modelos de dominio y entidades de seguridad. */
  private final OutboundConverter outboundConverter;

  /**
   * Constructor para la inyección del conversor.
   *
   * @param outboundConverter El conversor outbound.
   */
  public JWTProviderImpl(OutboundConverter outboundConverter) {
    this.outboundConverter = outboundConverter;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Decodifica la clave Base64.
   */
  @Override
  public byte[] getKey(String secretKey) {
    return Decoders.BASE64.decode(secretKey);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Construye y firma el token con JJWT.
   */
  @Override
  public String buildToken(
      Map<String, Object> extraClaims, UserModel user, Long expirationTime, String secretKey) {
    return Jwts.builder()
        // 1. Agrega los claims personalizados.
        .claims(extraClaims)
        // 2. Establece el 'Subject' (identificador principal, usualmente el username).
        .subject(user.getUsername())
        // 3. Fecha de emisión ('iat').
        .issuedAt(new Date(System.currentTimeMillis()))
        // 4. Fecha de expiración ('exp').
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        // 5. Firma el token usando la clave secreta.
        .signWith(Keys.hmacShaKeyFor(getKey(secretKey)))
        // 6. Finaliza la construcción y compacta el token.
        .compact();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Genera un token de acceso con la reclamación del DNI.
   */
   public String generateAccessToken(UserModel user) {
        // Se incluyen claims específicos (ej. email o roles) en el Access Token.
        return buildToken(Map.of(
            "email", user.getEmail()
            //, se pueden añadir más atributos del usuario como los authorities...
        ), user, accessTokenExpiration, accessTokenSecretKey);
    }

  /**
   * {@inheritDoc}
   *
   * <p>Genera un token de refresco básico.
   */
  @Override
  public String generateRefreshToken(UserModel user) {
    // El Refresh Token se mantiene simple (pocos claims) para minimizar el riesgo.
    return buildToken(new HashMap<>(), user, refreshTokenExpiration, refreshTokenSecretKey);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Verifica y extrae todos los claims.
   */
  @Override
  public Map<String, Object> getAllClaims(String token, String secretKey) {
    return Jwts.parser()
        // Provee la clave secreta para verificar la firma del token.
        .verifyWith(Keys.hmacShaKeyFor(getKey(secretKey)))
        .build()
        // Parsea el token, verifica su firma y obtiene el payload.
        .parseSignedClaims(token)
        .getPayload();
  }

  /** {@inheritDoc} */
  @Override
  public <T> T getClaim(
      String token, Function<Map<String, Object>, T> claimsResolver, String secretKey) {
    // 1. Obtiene todos los claims (implica verificación de firma y expiración).
    final Claims claims = (Claims) getAllClaims(token, secretKey);
    // 2. Aplica la función resolutora para obtener el claim deseado.
    return claimsResolver.apply(claims);
  }

  /** {@inheritDoc} */
  @Override
  public String getUsernameFromAccessToken(String token) {
    // Extrae el claim 'Subject' (Claims::getSubject) usando la clave de Access
    // Token.
    return getClaim(token, claims -> ((Claims) claims).getSubject(), accessTokenSecretKey);
  }

  /** {@inheritDoc} */
  @Override
  public Long getAccessTokenExpiresIn() {
    return accessTokenExpiration / 1000;
  }

  /** {@inheritDoc} */
  @Override
  public Date getAccessTokenExpiration(String token) {
    // Extrae el claim 'Expiration' (Claims::getExpiration).
    return getClaim(token, claims -> ((Claims) claims).getExpiration(), accessTokenSecretKey);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isAccessTokenExpired(String token) {
    // Compara la fecha de expiración del token con la fecha y hora actuales.
    return getAccessTokenExpiration(token).before(new Date());
  }

  /**
   * {@inheritDoc}
   *
   * <p>Valida correspondencia de usuario y vigencia temporal.
   */
  @Override
  public boolean isAccessTokenValid(String token, UserModel user) {

    UserDetails userDetails = outboundConverter.toUserEntity(user);
    // 1. Obtiene el nombre de usuario del token.
    final String username = getUsernameFromAccessToken(token);

    // 2. Verifica que el username coincida y que el token NO haya expirado.
    return (username.equals(userDetails.getUsername()) && !isAccessTokenExpired(token));
  }

  /** {@inheritDoc} */
  @Override
  public String getUsernameFromRefreshToken(String token) {
    // Extrae el claim 'Subject' (Claims::getSubject) usando la clave de Refresh
    // Token.
    return getClaim(token, claims -> ((Claims) claims).getSubject(), refreshTokenSecretKey);
  }

  /** {@inheritDoc} */
  @Override
  public Long getRefreshTokenExpiresIn() {
    return refreshTokenExpiration / 1000;
  }

  /** {@inheritDoc} */
  @Override
  public Date getRefreshTokenExpiration(String token) {
    // Extrae el claim 'Expiration' (Claims::getExpiration) usando la clave de
    // Refresh Token.
    return getClaim(token, claims -> ((Claims) claims).getExpiration(), refreshTokenSecretKey);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isRefreshTokenExpired(String token) {
    // Compara la fecha de expiración del token con la fecha y hora actuales.
    return getRefreshTokenExpiration(token).before(new Date());
  }

  /** {@inheritDoc} */
  @Override
  public boolean isRefreshTokenValid(String token, UserModel user) {

    UserDetails userDetails = outboundConverter.toUserEntity(user);

    // 1. Obtiene el nombre de usuario del token
    final String username = getUsernameFromRefreshToken(token);

    // 2. Verifica que el username coincida y que el token NO haya expirado
    return (username.equals(userDetails.getUsername()) && !isRefreshTokenExpired(token));
  }
}
