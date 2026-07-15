package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferFetcher jobOfferFetcher;

    List<JobOffer> fetchAllOffersAndSaveAllIfNotExists() {
        List<JobOffer> jobOffers = fetchAllJobOffers();
        List<JobOffer> jobOffersWithoutDuplicates = filterNotExistingJobOffers(jobOffers);
        try {
            return jobOfferRepository.saveAll(jobOffersWithoutDuplicates);
        } catch (OfferDuplicateException offerDuplicateException) {
            throw new RuntimeException();
        }

    }

    private List<JobOffer> filterNotExistingJobOffers(List<JobOffer> jobOffers) {
       return jobOffers.stream()
                .filter(jobOffer -> !jobOffer.offerUrl().isEmpty())
                .filter(jobOffer-> !jobOfferRepository.existsByOfferUrl(jobOffer.offerUrl()))
                .toList();
    }

    private  List<JobOffer>fetchAllJobOffers(){

        return jobOfferFetcher.fetchJobOffers()
                .stream()
                .map(offerDto -> JobOfferMapper.mapFromOfferResponseToJobOffer(offerDto))
                .toList();

    }



}
