package com.juniorjavajoboffers.domain.joboffer;


import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferRequestDto;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import com.juniorjavajoboffers.domain.joboffer.dto.OfferResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class JobOfferFacadeTest {


    @Test
    public void should_fetch_job_offers_from_remote_and_save_all_offers_when_repository_is_emtpty() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(jobOfferFacade.findAllOffers()).isEmpty();

        //when
        List<JobOfferResponseDto> result = jobOfferFacade.fetchAllOffersAndSaveIfNotExists();

        //then
        assertThat(result).hasSize(5);
    }

    @Test
    public void should_fetch_only_2_new_offers_when_repository_have_4_saved_offers_with_same_urls() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration(
                List.of(
                        new OfferResponseDto("company1", "position1", "salary1", "url1"),
                        new OfferResponseDto("company2", "position2", "salary2", "url2"),
                        new OfferResponseDto("company3", "position3", "salary3", "url3"),
                        new OfferResponseDto("company4", "position4", "salary4", "url4"),
                        new OfferResponseDto("company5", "position5", "salary5", "url5"),
                        new OfferResponseDto("company6", "position6", "salary6", "url6")
                )

        ).offerFacadeForTests();
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company1", "position1", "salary1", "url1"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company2", "position2", "salary2", "url2"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company3", "position3", "salary3", "url3"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company4", "position4", "salary4", "url4"));

        //when
        List<JobOfferResponseDto> jobOfferResponseList = jobOfferFacade.fetchAllOffersAndSaveIfNotExists();

        //then
        assertThat(List.of(
                jobOfferResponseList.get(0).offerUrl(),
                jobOfferResponseList.get(1).offerUrl()
        )).containsExactlyInAnyOrder("url5", "url6");
    }

    @Test
    public void should_save_4_job_offer_when_database_is_empty() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(jobOfferFacade.findAllOffers()).isEmpty();

        //when
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company1", "position1", "salary1", "url1"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company2", "position2", "salary2", "url2"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company3", "position3", "salary3", "url3"));
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company4", "position4", "salary4", "url4"));

        //then
        assertThat(jobOfferFacade.findAllOffers()).hasSize(4);


    }

    @Test
    public void should_throw_offer_not_found_exception_when_given_offer_not_found() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(jobOfferFacade.findAllOffers()).isEmpty();

        //when
        Throwable throwable = catchThrowable(() -> jobOfferFacade.findOfferById("1"));

        //then
        assertThat(throwable).isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id: 1 not found");
    }

    @Test
    public void should_throw_offer_duplicate_exception_when_offer_with_given_url_already_exists_in_database() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        jobOfferFacade.saveOffer(new JobOfferRequestDto("company1", "position1", "salary1", "url1"));
        assertThat(jobOfferFacade.findAllOffers()).hasSize(1);

        //when
        Throwable throwable = catchThrowable(() -> jobOfferFacade.saveOffer(new JobOfferRequestDto("company2", "position2", "salary2", "url1")));
        //then
        assertThat(throwable).isInstanceOf(OfferDuplicateException.class);


    }

    @Test
    public void should_find_offer_by_id_when_offer_is_in_database() {
        //given
        JobOfferFacade jobOfferFacade = new JobOfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        JobOfferResponseDto savedOffer = jobOfferFacade.saveOffer(new JobOfferRequestDto("company1", "position1", "salary1", "url1"));


        //when
        JobOfferResponseDto offerById = jobOfferFacade.findOfferById(savedOffer.id());

        //then
        assertThat(offerById).isEqualTo(
                JobOfferResponseDto.builder()
                        .id(savedOffer.id())
                        .companyName("company1")
                        .position("position1")
                        .salary("salary1")
                        .offerUrl("url1")
                        .build()
        );
    }
}

