package com.juniorjavajoboffers.domain.joboffer.dto;

import lombok.Builder;

@Builder
public record JobOfferResponseDto(String id, String company, String title, String salary, String offerUrl) {
}
