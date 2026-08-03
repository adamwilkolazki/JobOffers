package com.juniorjavaoffers.http.error;


import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherWebClient;
import com.juniorjavaoffers.features.SampleJobOffersResponse;

import org.junit.jupiter.api.extension.RegisterExtension;


import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.juniorjavaoffers.BaseIntegrationTest.wireMockServer;

public class JobOfferFetcherWebClientTest implements SampleJobOffersResponse {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort()).build();


    JobOfferFetcherWebClient jobOfferFetcher = new JobOfferFetcherWebClientTestConfiguration().remoteJobOfferTestClient(wireMockServer.getPort(), 1000, 1000);


}