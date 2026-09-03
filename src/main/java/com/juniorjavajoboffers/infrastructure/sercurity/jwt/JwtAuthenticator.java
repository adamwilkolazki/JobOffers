package com.juniorjavajoboffers.infrastructure.sercurity.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.juniorjavajoboffers.infrastructure.loginandregister.controller.JwtResponseDto;
import com.juniorjavajoboffers.infrastructure.loginandregister.controller.LoginRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
@Component
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties properties;

    public JwtResponseDto authenticateAndGenerateToken(LoginRequestDto loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        User user = (User) authenticate.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();

        return JwtResponseDto.builder().token(token).username(username).build();
    }

    private String createToken(User user) {
        String secret = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant now = Instant.now(clock);
        Instant expiresAt = now.plus(Duration.ofDays(properties.expirationDays()) );
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

    }


}
