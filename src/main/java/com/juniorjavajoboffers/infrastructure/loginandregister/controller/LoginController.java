package com.juniorjavajoboffers.infrastructure.loginandregister.controller;

import com.juniorjavajoboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.juniorjavajoboffers.domain.loginandregister.RegisterUserDto;
import com.juniorjavajoboffers.domain.loginandregister.RegistrationResultDto;
import com.juniorjavajoboffers.domain.loginandregister.UserDto;
import com.juniorjavajoboffers.infrastructure.sercurity.jwt.JwtAuthenticator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController {

    private final JwtAuthenticator jwtAuthenticator;


    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateJwtToken(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        final JwtResponseDto jwtResponse = jwtAuthenticator.authenticateAndGenerateToken(loginRequestDto);
        return ResponseEntity.ok(jwtResponse);
    }







}
