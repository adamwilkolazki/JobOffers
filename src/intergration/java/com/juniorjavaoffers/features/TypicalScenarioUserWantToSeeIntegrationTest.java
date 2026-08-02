package com.juniorjavaoffers.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.infrastructure.joboffer.scheduler.JobOffersScheduler;
import com.juniorjavaoffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TypicalScenarioUserWantToSeeIntegrationTest extends BaseIntegrationTest implements SampleJobOffersResponse {

    @Autowired
    JobOffersScheduler scheduler;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void f() throws Exception {

        // step 1: there are no offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/offers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOfferJson())));


        // step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given && when
        List<JobOfferResponseDto> newOffers = scheduler.fetchJobOffers();
        //then
        assertThat(newOffers).isEmpty();


// step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
// step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
// step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
// step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
// step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given
        String offerURL = "/offers";
        //when
        ResultActions perform = mockMvc.perform(get(offerURL)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<JobOfferResponseDto> offers = objectMapper.readValue(jsonResponse, new TypeReference<List<JobOfferResponseDto>>() {
        });
        assertThat(offers.isEmpty());

        // step 8: there are 2 new offers in external HTTP server

        //given && when && then
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/offers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithTwoOffersJson())
                ));

// step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database

        //given && when

        List<JobOfferResponseDto> twoNewJobOffers = scheduler.fetchJobOffers();

        //then
        assertThat(twoNewJobOffers.size()).isEqualTo(2);


        // step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000

        //given

        JobOfferResponseDto firstExpectedJobOffer = twoNewJobOffers.get(0);
        JobOfferResponseDto secondExpectedJobOffer = twoNewJobOffers.get(1);

        //when

        ResultActions performGetWithTwoJobOffers = mockMvc.perform(get("/offers"));

        //then
        String twoJobOfferAsString = performGetWithTwoJobOffers.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<JobOfferResponseDto> twoExpectedJobOffers = objectMapper.readValue(twoJobOfferAsString, new TypeReference<List<JobOfferResponseDto>>() {
        });

        assertAll(
                () -> assertThat(twoExpectedJobOffers.size()).isEqualTo(2),
                ()->assertThat(twoExpectedJobOffers).containsExactlyInAnyOrder(
                        new JobOfferResponseDto(firstExpectedJobOffer.id(),firstExpectedJobOffer.company(),firstExpectedJobOffer.title(),firstExpectedJobOffer.salary(),firstExpectedJobOffer.offerUrl()),
                        new JobOfferResponseDto(secondExpectedJobOffer.id(),secondExpectedJobOffer.company(),secondExpectedJobOffer.title(),secondExpectedJobOffer.salary(),secondExpectedJobOffer.offerUrl())
                ));


// step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        ResultActions getResultWithNonExistingId = mockMvc.perform(get("/offers/991"));
        getResultWithNonExistingId.andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                {
                                "message": "Offer with id: 991 not found",
                                "status": "NOT_FOUND"
                                }
                                """.trim()));


// step 12: user made GET /offers/1000 and system returned OK(200) with offer

        //given
        String expectedOfferId = firstExpectedJobOffer.id();

        //when
        ResultActions getOfferById = mockMvc.perform(get("/offers/" + expectedOfferId).contentType(MediaType.APPLICATION_JSON));

        //then
        String expectedOfferAsString = getOfferById.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JobOfferResponseDto expectedJobOfferResponseDto = objectMapper.readValue(expectedOfferAsString, JobOfferResponseDto.class);

        assertThat(expectedJobOfferResponseDto.id()).isEqualTo(expectedOfferId);

// step 13: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/offers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithFourOffersJson())
                ));
// step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database

    //given && when

        List<JobOfferResponseDto> nextTwoNewOffers = scheduler.fetchJobOffers();
    //then
        assertThat(nextTwoNewOffers.size()).isEqualTo(2);

// step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000

        //given&&when

        ResultActions performGetAllJobOffers = mockMvc.perform(get("/offers").contentType(MediaType.APPLICATION_JSON));

        //then

        String allJobOffers = performGetAllJobOffers.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
       List<JobOfferResponseDto> allOffers = objectMapper.readValue(allJobOffers, new TypeReference<>() {
        });
        System.out.println(allOffers.size());
       assertThat(allOffers).hasSize(4);




//step 16:  user made POST /offers/save and offer as body and system returned 201 (CREATED)
        //given
        //when

        ResultActions performPostWithOneOffer = mockMvc.perform(post("/offers/save")
                .content("""
                        {
                          "company": "comp1",
                          "title": "title1",
                          "salary": "12345",
                          "offerUrl": "https ://newoffer.pl"
                        }
                        
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));  /* + "MediaType.APPLICATION_JSON;charset=UTF-8"*/

        //then

        String createdOfferJson = performPostWithOneOffer.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        JobOfferResponseDto jobOfferResponse = objectMapper.readValue(createdOfferJson, JobOfferResponseDto.class);
        String id = jobOfferResponse.id();
        assertAll(
                () -> assertThat(jobOfferResponse.offerUrl()).isEqualTo("https ://newoffer.pl"),
                () -> assertThat(jobOfferResponse.company()).isEqualTo("comp1"),
                () -> assertThat(jobOfferResponse.title()).isEqualTo("title1"),
                () -> assertThat(jobOfferResponse.salary()).isEqualTo("12345"),
                () -> assertThat(jobOfferResponse.id()).isNotNull()

        );

        //step 17 user made GET /offers/id and system returned offer added by user

        //given & when

        ResultActions getOneSavedOffer = mockMvc.perform(get("/offers/" + id).contentType(MediaType.APPLICATION_JSON));
        //then

        String oneSavedOfferAsString = getOneSavedOffer.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JobOfferResponseDto oneSavedOffer = objectMapper.readValue(oneSavedOfferAsString, JobOfferResponseDto.class);

        assertThat(oneSavedOffer.id()).isEqualTo(id);


    }


}
