package com.juniorjavaoffers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.juniorjavajoboffers.JobOfferSpringBootApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = JobOfferSpringBootApplication.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {
    private static final String WIRE_MOCK_HOST = "http://localhost";
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @Container //nazwa obrazu MongoDB z Docker Hub
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo"));

    @RegisterExtension //utworzenie serwera na dowolnym porcie w komputerze
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource //nadpisanie propertisow z pliku application.yml

    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("offers.fetcher.http.client.config", () -> WIRE_MOCK_HOST);
        registry.add("offers.fetcher.http.client.port", () -> wireMockServer.getPort());
    }

    @Test
    public void f() throws Exception {


    }
}
