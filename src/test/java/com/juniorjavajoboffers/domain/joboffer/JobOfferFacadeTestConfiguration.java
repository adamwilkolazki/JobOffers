package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;

import java.util.List;

public class JobOfferFacadeTestConfiguration {
private final InMemoryJobOfferFetcherTest inMemoryFetcherTest;
private final InMemoryJobOfferRepository jobOfferRepository;

    public JobOfferFacadeTestConfiguration(List<OfferResponseDto> jobOffersList) {
        this.inMemoryFetcherTest = new InMemoryJobOfferFetcherTest(jobOffersList);
        this.jobOfferRepository = new InMemoryJobOfferRepository();
    }

    public JobOfferFacadeTestConfiguration() {
        this.inMemoryFetcherTest = new InMemoryJobOfferFetcherTest(
                List.of(
                        new OfferResponseDto("company1","position1","salary1", "url1"),
                        new OfferResponseDto("company2","position2","salary2", "url2"),
                        new OfferResponseDto("company3","position3","salary3", "url3"),
                        new OfferResponseDto("company4","position4","salary4", "url4"),
                        new OfferResponseDto("company5","position5","salary5", "url5")
                )
        );

        this.jobOfferRepository = new InMemoryJobOfferRepository();
    }
    JobOfferFacade offerFacadeForTests(){
        return new JobOfferFacade(jobOfferRepository,new JobOfferService(jobOfferRepository,inMemoryFetcherTest));
    }
}
