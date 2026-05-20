package com.juniorjavajoboffers.domain.joboffer;

import lombok.Builder;

@Builder
public record JobOffer(String id, String companyName, String position, String salary, String offerUrl) {
}
