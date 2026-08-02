package com.juniorjavaoffers.controller.error;

import com.juniorjavaoffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_adding_duplicated_job_offer() throws Exception {
        //given
        ResultActions performAddingFirstJobOffer = mockMvc.perform(post("/offers/save")

                .content(("""
                        {
                          "company": "comp1",
                          "title": "title1",
                          "salary": "12345",
                          "offerUrl": "https ://newoffer.pl"
                        }
                        
                        """.trim()))
                .contentType(MediaType.APPLICATION_JSON));
        performAddingFirstJobOffer.andExpect(status().isCreated());
        ResultActions performAddingDuplicatedJobOffer = mockMvc.perform(post("/offers/save")

                .content(("""
                        {
                          "company": "comp1",
                          "title": "title1",
                          "salary": "12345",
                          "offerUrl": "https ://newoffer.pl"
                        }
                        
                        """.trim()))
                .contentType(MediaType.APPLICATION_JSON));
        performAddingDuplicatedJobOffer.andExpect(status().isConflict());

    }
}
