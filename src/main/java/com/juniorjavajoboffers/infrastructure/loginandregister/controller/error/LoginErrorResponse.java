package com.juniorjavajoboffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record LoginErrorResponse(String badCredentials, HttpStatus httpStatus) {
}

