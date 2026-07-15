package com.juniorjavajoboffers.infrastructure.joboffer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferNotFoundErrorResponse(String message, HttpStatus status) {
}
