package com.juniorjavajoboffers.domain.joboffer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document("offers")
public record JobOffer(
        @Id String id,
        @Field("company")String company,
        @Field("position")String title,
        @Field("salary")String salary,
        @Field("url") @Indexed(unique = true)  String offerUrl) {
}

