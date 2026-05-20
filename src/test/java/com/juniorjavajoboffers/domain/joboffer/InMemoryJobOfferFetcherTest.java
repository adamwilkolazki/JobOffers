package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;

import java.util.List;

public class InMemoryJobOfferFetcherTest implements JobOfferFetcher{
    List<OfferResponseDto> listOfOffers;

    public InMemoryJobOfferFetcherTest(List<OfferResponseDto> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<OfferResponseDto> fetchJobOffers() {
        return listOfOffers;
    }
}
