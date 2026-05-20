package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferRequestDto;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;

class JobOfferMapper {

    public static JobOfferResponseDto mapFromJobOfferToJobOfferResponseDto(JobOffer jobOffer){
         return JobOfferResponseDto.builder()
                 .id(jobOffer.id())
                 .companyName(jobOffer.companyName())
                 .position(jobOffer.position())
                 .salary(jobOffer.salary())
                 .offerUrl(jobOffer.offerUrl())
                 .build();
     }
     public static JobOffer mapFromJobOfferRequestDtoToJobOffer(JobOfferRequestDto request){
        return JobOffer.builder()
                .companyName(request.companyName())
                .position(request.position())
                .salary(request.salary())
                .offerUrl(request.offerUrl())
                .build();
     }
     public static JobOffer mapFromOfferResponseToJobOffer(OfferResponseDto responseDto){
        return JobOffer.builder()
                .companyName(responseDto.companyName())
                .position(responseDto.position())
                .salary(responseDto.salary())
                .offerUrl(responseDto.offerUrl())
                .build();
     }
}
