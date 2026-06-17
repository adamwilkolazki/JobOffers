package com.juniorjavajoboffers.domain.joboffer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class JobOfferFacadeConfiguration {

    @Bean
    JobOfferFacade jobOfferFacade(JobOfferFetcher jobOfferFetcher) {
        JobOfferRepository repository = new JobOfferRepository() {
            @Override
            public boolean existsByUrl(String offerUrl) {
                return false;
            }

            @Override
            public Optional<JobOffer> findByOfferUrl(String offerUrl) {
                return Optional.empty();
            }

            @Override
            public JobOffer save(JobOffer jobOffer) {
                return null;
            }

            @Override
            public Optional<JobOffer> findById(String id) {
                return Optional.empty();
            }

            @Override
            public List<JobOffer> saveAll(List<JobOffer> offers) {
                return List.of();
            }

            @Override
            public List<JobOffer> findAll() {
                return List.of();
            }
        };
        JobOfferService jobOfferService = new JobOfferService(repository, jobOfferFetcher);
        return new JobOfferFacade(repository,jobOfferService);
    }
}
