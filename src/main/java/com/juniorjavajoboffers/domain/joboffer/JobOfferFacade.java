package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferRequestDto;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Log4j2
public class JobOfferFacade {

    private final JobOfferRepository offerRepository;
    private final JobOfferService offerService;

    public List<JobOfferResponseDto> findAllOffers(){

        return offerRepository.findAll()
                .stream()
                .map(JobOfferMapper::mapFromJobOfferToJobOfferResponseDto)
                .toList();
    }
    public List<JobOfferResponseDto> fetchAllOffersAndSaveIfNotExists(){

        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(JobOfferMapper::mapFromJobOfferToJobOfferResponseDto)
                .toList();
    }
    public JobOfferResponseDto findOfferById(String id){
        return offerRepository.findById(id)
                .map(JobOfferMapper::mapFromJobOfferToJobOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }
    public JobOfferResponseDto saveOffer(JobOfferRequestDto offerRequestDto){
        JobOffer jobOffer = JobOfferMapper.mapFromJobOfferRequestDtoToJobOffer(offerRequestDto);
        JobOffer savedOffer = offerRepository.save(jobOffer);
        return JobOfferMapper.mapFromJobOfferToJobOfferResponseDto(savedOffer);

    }
}
