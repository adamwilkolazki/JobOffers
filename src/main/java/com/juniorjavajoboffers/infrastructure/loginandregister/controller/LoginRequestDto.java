package com.juniorjavajoboffers.infrastructure.loginandregister.controller;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String username,
        @NotBlank String password
) {
}
