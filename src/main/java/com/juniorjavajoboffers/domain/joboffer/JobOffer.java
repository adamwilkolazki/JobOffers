package com.juniorjavajoboffers.domain.joboffer;

import lombok.Builder;

@Builder
public record JobOffer(String id, String company, String title, String salary, String offerUrl) {
}

