package com.juniorjavajoboffers.domain.joboffer;

import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferRequestDto;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;

class JobOfferMapper {

    public static JobOfferResponseDto mapFromJobOfferToJobOfferResponseDto(JobOffer jobOffer){
         return JobOfferResponseDto.builder()
                 .id(jobOffer.id())
                 .company(jobOffer.company())
                 .title(jobOffer.title())
                 .salary(jobOffer.salary())
                 .offerUrl(jobOffer.offerUrl())
                 .build();
     }
     public static JobOffer mapFromJobOfferRequestDtoToJobOffer(JobOfferRequestDto request){
        return JobOffer.builder()
                .company(request.company())
                .title(request.title())
                .salary(request.salary())
                .offerUrl(request.offerUrl())
                .build();
     }
     public static JobOffer mapFromOfferResponseToJobOffer(OfferResponseDto responseDto){
        return JobOffer.builder()
                .company(responseDto.company())
                .title(responseDto.title())
                .salary(responseDto.salary())
                .offerUrl(responseDto.offerUrl())
                .build();
     }
}
