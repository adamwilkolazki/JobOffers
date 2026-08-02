package com.juniorjavajoboffers.infrastructure.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ArgumentValidationErrorResponse(List<String> messages, HttpStatus status) {
}
