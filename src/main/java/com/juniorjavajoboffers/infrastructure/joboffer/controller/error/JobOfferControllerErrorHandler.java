package com.juniorjavajoboffers.infrastructure.joboffer.controller.error;

import com.juniorjavajoboffers.domain.joboffer.OfferNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class JobOfferControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferNotFoundErrorResponse handleOfferNotFound(OfferNotFoundException exception){
    String message = exception.getMessage();
    log.error(message);
    return new OfferNotFoundErrorResponse(message, HttpStatus.NOT_FOUND);

}
}
