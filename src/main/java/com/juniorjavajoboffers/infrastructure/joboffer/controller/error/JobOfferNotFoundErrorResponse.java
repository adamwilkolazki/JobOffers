package com.juniorjavajoboffers.infrastructure.joboffer.controller.error;

import org.springframework.http.HttpStatus;

public record JobOfferNotFoundErrorResponse(String message, HttpStatus status) {
}
