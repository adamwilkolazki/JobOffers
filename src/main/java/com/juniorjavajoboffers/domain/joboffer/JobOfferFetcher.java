package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;

import java.util.List;

public interface JobOfferFetcher {

    List<OfferResponseDto> fetchJobOffers();

}
