package com.juniorjavajoboffers.infrastructure.joboffer.controller.error;

import org.springframework.http.HttpStatus;

public record JobOfferPostErrorResponse(String message, HttpStatus status) {
}
