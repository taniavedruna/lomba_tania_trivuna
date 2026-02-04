package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de renovación de tokens (refresh token). */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequestDTO {
  /** El Refresh Token que se desea canjear por un nuevo Access Token. */
  
  @NotBlank(message = "refreshToken is required and must not be blank")
  String refreshToken;
}
