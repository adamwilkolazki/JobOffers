package com.juniorjavajoboffers.domain.joboffer;

import java.util.*;

public class InMemoryJobOfferRepository implements JobOfferRepository {
    Map<String, JobOffer> database = new HashMap<>();

    @Override
    public boolean existsByUrl(String offerUrl) {
        long count = database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public Optional<JobOffer> findByOfferUrl(String jobOfferUrl) {
/*
        return database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(jobOfferUrl))
                .findFirst();
*/
return Optional.of(database.get(jobOfferUrl));
    }

    @Override
    public JobOffer save(JobOffer entity) {
        if (database.values()
                .stream().anyMatch(jobOffer -> jobOffer.offerUrl().equals(entity.offerUrl()))) {
            throw new OfferDuplicateException("entity already exists in base");
        }
        String id = UUID.randomUUID().toString();
        JobOffer offer = JobOffer.builder()
                .id(id)
                .offerUrl(entity.offerUrl())
                .salary(entity.salary())
                .companyName(entity.companyName())
                .position(entity.position())
                .build();
        database.put(offer.id(), offer);
        return offer;
    }

    @Override
    public Optional<JobOffer> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<JobOffer> saveAll(List<JobOffer> offers) {
        return offers.stream()
                .map(offer -> this.save(offer))
                .toList();
    }

    @Override
    public List<JobOffer> findAll() {
        return database.values().stream().toList();
    }
}
