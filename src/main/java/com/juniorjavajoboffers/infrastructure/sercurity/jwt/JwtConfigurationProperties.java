package com.juniorjavajoboffers.infrastructure.sercurity.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JwtConfigurationProperties(
        String secret,
        int expirationDays,
        String issuer

) {
}
