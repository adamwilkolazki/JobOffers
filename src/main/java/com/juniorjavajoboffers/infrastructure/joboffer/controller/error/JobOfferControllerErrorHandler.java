package com.juniorjavajoboffers.infrastructure.joboffer.controller.error;

import com.juniorjavajoboffers.domain.joboffer.OfferDuplicateException;
import com.juniorjavajoboffers.domain.joboffer.OfferNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public JobOfferNotFoundErrorResponse handleOfferNotFound(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new JobOfferNotFoundErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public JobOfferPostErrorResponse offerDuplicate(DuplicateKeyException exception) {
        String message = "Offer with given URL already exists.";
        log.error(message);
        return new JobOfferPostErrorResponse(message, HttpStatus.CONFLICT);
    }


}

