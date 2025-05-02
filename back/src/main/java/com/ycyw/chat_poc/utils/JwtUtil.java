package com.ycyw.chat_poc.utils;

import com.ycyw.chat_poc.models.User;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtEncoder jwtEncoder;

  @Value("${jwt.accesstoken.validity}")
  private int accessTokenValidity;

  @Value("${spring.application.name}")
  private String appName;

  public String generateAccessToken(User user) {
    Instant now = Instant.now();

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(appName)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(accessTokenValidity))
            .subject(user.getEmail())
            .claim("roles", List.of("ROLE_" + user.getRole().name()))
            .build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
