package com.juniorjavajoboffers.domain.joboffer;

import java.util.List;
import java.util.Optional;

public interface JobOfferRepository {

    boolean existsByUrl(String offerUrl);

    Optional<JobOffer> findByOfferUrl(String offerUrl);

    JobOffer save(JobOffer jobOffer);

    Optional<JobOffer> findById(String id);

    List<JobOffer> saveAll(List<JobOffer> offers);

    List<JobOffer> findAll();
}
