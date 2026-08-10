package com.juniorjavaoffers.http.error;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherClientConfiguration;
import com.juniorjavajoboffers.infrastructure.joboffer.http.JobOfferFetcherWebClient;
import com.juniorjavaoffers.BaseIntegrationTest;
import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static com.juniorjavaoffers.BaseIntegrationTest.WIRE_MOCK_HOST;

public class JobOfferFetcherWebClientTestConfiguration {

    public JobOfferFetcherWebClient remoteJobOfferTestClient(int port, int connectionTimeout, int readTimeout) {

        HttpClient httpClient = HttpClient.create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        connectionTimeout
                )
                .responseTimeout(
                        Duration.ofMillis(readTimeout)
                );

        WebClient webClient = WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(httpClient)
                )
                .build();


        return new JobOfferFetcherWebClient(
                webClient,
                WIRE_MOCK_HOST,
                port
        );
    }

}
