package com.juniorjavaoffers.http.error;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherWebClient;
import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetchingException;
import com.juniorjavaoffers.features.SampleJobOffersResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.juniorjavaoffers.BaseIntegrationTest.wireMockServer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;


public class JobOfferFetcherWebClientTest implements SampleJobOffersResponse {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort()).build();


    JobOfferFetcherWebClient jobOfferFetcher = new JobOfferFetcherWebClientTestConfiguration().remoteJobOfferTestClient(wireMockServer.getPort(), 1000, 1000);

    @Test
    void should_throw_exception_500_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));
        //when

        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());

        //then

        assertThat(throwable).isInstanceOf(JobOfferFetchingException.class);
        assertThat(throwable.getMessage()).isEqualTo("Cannot connect to offer service");


    }

    @Test
    void should_throw_exception_500_when_fault_empty_response() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE)));
        //when
        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());
        //then
        assertThat(throwable).isInstanceOf(JobOfferFetchingException.class);
        assertThat(throwable.getMessage()).isEqualTo("Cannot connect to offer service");
    }

    @Test
    void should_throw_exception_204_when_status_is_204_no_content() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody(("""
                                []
                                """.trim()
                        ))));
        //when
        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    void should_throw_exception_500_when_response_delay_is_5000_ms_and_client_has_1000ms_read_timeout() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody(bodyWithTwoOffersJson())
                        .withFixedDelay(5000)));

        //when
        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());

        //then
        assertThat(throwable).isInstanceOf(JobOfferFetchingException.class);
        assertThat(throwable.getMessage()).isEqualTo("Cannot connect to offer service");

    }

    @Test
    void should_throw_exception_404_when_http_service_returning_not_found_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value()))
        );

        // when
        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());

        // then
        assertThat(throwable).isInstanceOf(JobOfferFetchingException.class);
        assertThat(throwable.getMessage()).isEqualTo("Offer service returned HTTP error: 404 NOT_FOUND");
    }

    @Test
    void should_throw_exception_401_when_http_service_returning_unauthorized_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.UNAUTHORIZED.value()))
        );

        // when
        Throwable throwable = catchThrowable(() -> jobOfferFetcher.fetchJobOffers());

        // then
        assertThat(throwable).isInstanceOf(JobOfferFetchingException.class);
        assertThat(throwable.getMessage()).isEqualTo("Offer service returned HTTP error: 401 UNAUTHORIZED");

    }
}