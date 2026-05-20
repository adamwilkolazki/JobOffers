package com.juniorjavajoboffers.domain.joboffer.dto;

import lombok.Builder;

@Builder
public record JobOfferResponseDto(String id,String companyName,String position,String salary, String offerUrl) {
}
