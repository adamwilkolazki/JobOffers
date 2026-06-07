package com.juniorjavajoboffers.infrastructure.joboffer.http;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class JobOfferFetcherWebClient implements JobOfferFetcher {

    private final WebClient webClient;
    private final String uri;
    private final int port;

    @Override
    public List<OfferResponseDto> fetchJobOffers() {
        log.info("Started fetching offers");
        try {
            ResponseEntity<List<OfferResponseDto>> responseEntity = webClient.get()
                    .uri(getUrlForService("/offers"))
//                  .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<OfferResponseDto>>() {
                    })
                    .block();

            List<OfferResponseDto> body = responseEntity.getBody();
            if (body == null) {
                log.info("Response body was null, returning empty list");
                return Collections.emptyList();
            }
            log.info("Response body returned: " + body);
            return body;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers");
            return Collections.emptyList();
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
