package com.juniorjavajoboffers.domain.joboffer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String title, String company, String salary, String offerUrl) {
}
