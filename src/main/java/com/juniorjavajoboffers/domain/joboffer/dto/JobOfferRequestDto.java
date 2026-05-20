package com.juniorjavajoboffers.domain.joboffer.dto;

import lombok.Builder;

@Builder
public record JobOfferRequestDto(String companyName, String position, String salary, String offerUrl) {
}
