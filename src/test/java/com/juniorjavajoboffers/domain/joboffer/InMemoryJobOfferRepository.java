package com.juniorjavajoboffers.domain.joboffer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class InMemoryJobOfferRepository implements JobOfferRepository {
    Map<String, JobOffer> database = new HashMap<>();



    @Override
    public boolean existsByOfferUrl(String offerUrl) {
        long count = database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;

    }

    @Override
    public Optional<JobOffer> findByOfferUrl(String jobOfferUrl) {
        return database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(jobOfferUrl))
                .findFirst();
//return Optional.ofNullable(database.get(jobOfferUrl));
    }

    @Override
    public <S extends JobOffer>S save(S entity) {
        if (database.values()
                .stream().anyMatch(jobOffer -> jobOffer.offerUrl().equals(entity.offerUrl()))) {
            throw new OfferDuplicateException("entity already exists in base");
        }
        String id = UUID.randomUUID().toString();
        JobOffer offer = JobOffer.builder()
                .id(id)
                .offerUrl(entity.offerUrl())
                .salary(entity.salary())
                .company(entity.company())
                .title(entity.title())
                .build();
        database.put(offer.id(), offer);
        S offer1 = (S) offer;
        return offer1;
    }


    @Override
    public Optional<JobOffer> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }




    @Override
    public boolean existsById(String s) {
        return false;
    }



    @Override
    public <S extends JobOffer> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(),false)
                .map(this::save)
                .toList();
    }

    @Override
    public List<JobOffer> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public List<JobOffer> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(JobOffer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends JobOffer> entities) {

    }

    @Override
    public void deleteAll() {

    }


    @Override
    public <S extends JobOffer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends JobOffer> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends JobOffer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends JobOffer> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends JobOffer> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends JobOffer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends JobOffer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends JobOffer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends JobOffer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<JobOffer> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<JobOffer> findAll(Pageable pageable) {
        return null;
    }
}
