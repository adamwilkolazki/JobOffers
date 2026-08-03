package com.juniorjavajoboffers.infrastructure.joboffer.http;

public class JobOfferFetchingException extends RuntimeException{
    public JobOfferFetchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
