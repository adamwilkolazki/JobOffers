package com.juniorjavajoboffers.infrastructure.joboffer.http;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.Duration;

@Log4j2
@Configuration
public class JobOfferFetcherClientConfiguration {


    @Bean
    ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response ->
        {
            if (response.statusCode().is5xxServerError()) {
                return Mono.error(new RuntimeException());
            }
            return Mono.just(response);
        });
    }

    @Bean
    WebClient webClient() {

        return WebClient.builder()
                .filter(errorHandlingFilter())
                .build();
    }


    @Bean
    JobOfferFetcher jobOfferFetcher(WebClient webClient, @Value("${offers.fetcher.http.client.config}") String uri,
                                    @Value("${offers.fetcher.http.client.port}") int port) {
        String baseUrl = uri + ":" + port;
        return new JobOfferFetcherWebClient(webClient, uri, port);
    }


}
