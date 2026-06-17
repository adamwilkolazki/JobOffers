package com.juniorjavajoboffers.infrastructure.joboffer.scheduler;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFacade;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JobOffersScheduler {

    private final JobOfferFacade jobOfferFacade;

    //@Scheduled(cron = "0 0 /3 * * *")
    @Scheduled(cron = "${offers.fetcher.scheduler.delay}")
    public List<JobOfferResponseDto> fetchJobOffers() {
        return jobOfferFacade.fetchAllOffersAndSaveIfNotExists();
    }
}
