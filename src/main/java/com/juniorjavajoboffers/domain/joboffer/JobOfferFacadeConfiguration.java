package com.juniorjavajoboffers.domain.joboffer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class JobOfferFacadeConfiguration {

    @Bean
    JobOfferFacade jobOfferFacade(JobOfferFetcher jobOfferFetcher,JobOfferRepository repository) {

        JobOfferService jobOfferService = new JobOfferService(repository,jobOfferFetcher);
        return new JobOfferFacade(repository,jobOfferService);
    }
}
