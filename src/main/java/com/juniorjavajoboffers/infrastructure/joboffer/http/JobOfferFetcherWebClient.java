package com.juniorjavajoboffers.infrastructure.joboffer.http;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

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

        WebClient testClient = WebClient.builder()
                .filter((request, next) -> {
                    log.info("URL: {}", request.url());
                    log.info("METHOD: {}", request.method());
                    log.info("HEADERS: {}", request.headers());
                    return next.exchange(request);
                })
                .build();
        log.info("Started fetching offers");
        try {
            ResponseEntity<List<OfferResponseDto>> responseEntity = webClient.get()
                    .uri(getUrlForService("/offers"))
                    .accept(MediaType.ALL)
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
        } catch (WebClientResponseException e) {
            throw new JobOfferFetchingException("Offer service returned HTTP error:" + e.getStatusCode(), e);
        } catch (WebClientRequestException e) {

            throw new JobOfferFetchingException("Cannot connect to offer service", e);

        }
    }


    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }

    @PostConstruct
    void init() {
        System.out.println(webClient);
    }
}

