package com.juniorjavajoboffers.domain.joboffer;

import lombok.Getter;

@Getter
class OfferNotFoundException extends RuntimeException {
    private final String offerId;

    public OfferNotFoundException(String offerId) {
        super(String.format("Offer with id: %s not found", offerId));
        this.offerId = offerId;
    }
}
