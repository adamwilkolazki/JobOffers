package com.juniorjavaoffers.scheduler;

import com.juniorjavajoboffers.JobOfferSpringBootApplication;
import com.juniorjavajoboffers.domain.joboffer.JobOfferFetcher;
import com.juniorjavaoffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
@SpringBootTest(classes = JobOfferSpringBootApplication.class,properties = "scheduling.enabled=true")
public class JobOfferSchedulerTest extends BaseIntegrationTest {

   @MockitoSpyBean//adnotacja potrzebna do użycia metody verify
    JobOfferFetcher offerFetcher;

    @Test
    public void should_run_http_client_offers_fetching_specified_number_of_times() {
        await().
                atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> verify(offerFetcher, times(2)).fetchJobOffers());
    }
}
