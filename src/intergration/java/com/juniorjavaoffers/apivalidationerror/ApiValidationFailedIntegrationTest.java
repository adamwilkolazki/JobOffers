package com.juniorjavaoffers.apivalidationerror;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juniorjavajoboffers.infrastructure.apivalidation.ArgumentValidationErrorResponse;
import com.juniorjavaoffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void f() throws Exception {
        //given&when
        ResultActions performPostOfferWithEmptyProperties = mockMvc.perform(post("/offers/save")
                .content("""
                         {
                           "company": "",
                           "title": "",
                           "offerUrl": ""
                           }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON +";charset=UTF-8"));
        //then
        String contentAsString = performPostOfferWithEmptyProperties.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        ArgumentValidationErrorResponse argumentValidationErrorResponse = objectMapper.readValue(contentAsString, ArgumentValidationErrorResponse.class);
        assertThat(argumentValidationErrorResponse.messages()).containsExactlyInAnyOrder(
                "company can't be empty or null",
                "title can't be empty or null",
                "salary can't be empty or null",
                "offer url can't be empty or null");


    }
}
