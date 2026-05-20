package com.juniorjavajoboffers.domain.joboffer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String companyName, String position, String salary, String offerUrl) {
}
