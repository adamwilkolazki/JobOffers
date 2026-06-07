package com.juniorjavajoboffers.infrastructure.joboffer.http;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class JobOfferFetcherClientConfiguration {
    @Value("${offers.fetcher.http.client.connectionTimeout}")
    private int connectionTimeout;
    @Value("${offers.fetcher.http.client.responseTimeout}")
    private long responseTimeout;

    @Bean
    ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response ->
        {
            if (response.statusCode().is5xxServerError())
            {
                return Mono.error(new RuntimeException());
            }
            return Mono.just(response);
        });


    }

    @Bean
    WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .responseTimeout(Duration.ofSeconds(responseTimeout));
        return WebClient.builder()
                .filter(errorHandlingFilter())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }


    @Bean
    JobOfferFetcher jobOfferFetcher(WebClient webClient, @Value("${offers.fetcher.http.client.config}") String uri,
                                    @Value("${offers.fetcher.http.client.port}") int port) {
        return new JobOfferFetcherWebClient(webClient, uri, port);
    }


}
