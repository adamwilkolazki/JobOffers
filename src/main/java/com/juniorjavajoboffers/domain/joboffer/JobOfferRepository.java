package com.juniorjavajoboffers.domain.joboffer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JobOfferRepository extends MongoRepository<JobOffer, String> {

    boolean existsByOfferUrl(String offerUrl);

}
