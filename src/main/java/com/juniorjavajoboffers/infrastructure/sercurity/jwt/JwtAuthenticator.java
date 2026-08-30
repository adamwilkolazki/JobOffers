package com.juniorjavajoboffers.infrastructure.sercurity.jwt;

import com.juniorjavajoboffers.infrastructure.loginandregister.controller.JwtResponseDto;
import com.juniorjavajoboffers.infrastructure.loginandregister.controller.LoginRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;

  public JwtResponseDto authenticateAndGenerateToken(LoginRequestDto loginRequest){
      Authentication authenticate = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password()));
      return JwtResponseDto.builder().build();
  }
}
