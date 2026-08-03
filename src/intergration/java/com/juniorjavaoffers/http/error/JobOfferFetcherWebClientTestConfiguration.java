package com.juniorjavaoffers.http.error;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherClientConfiguration;
import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherWebClient;
import com.juniorjavaoffers.BaseIntegrationTest;
import org.springframework.web.reactive.function.client.WebClient;

import static com.juniorjavaoffers.BaseIntegrationTest.WIRE_MOCK_HOST;

public class JobOfferFetcherWebClientTestConfiguration {

    public JobOfferFetcherWebClient remoteJobOfferTestClient(int port, int connectionTimeout, int readTimeout) {

        WebClient webClient = WebClient.builder()
                .build();

        return new JobOfferFetcherWebClient(
                webClient,
                WIRE_MOCK_HOST,
                port
        );
    }

}
