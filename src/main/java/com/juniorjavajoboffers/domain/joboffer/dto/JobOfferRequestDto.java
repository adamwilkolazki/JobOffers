package com.juniorjavajoboffers.domain.joboffer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Valid
@Builder
public record JobOfferRequestDto( @NotBlank(message = "{company.not.empty}")
                                 String company,
                                  @NotBlank(message = "{title.not.empty}")
                                 String title,
                                  @NotBlank(message = "{salary.not.empty}")
                                 String salary,
                                 @NotBlank(message = "{offerUrl.not.empty}")
                                 String offerUrl) {
}
