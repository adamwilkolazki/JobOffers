package com.juniorjavajoboffers.infrastructure.joboffer.controller;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFacade;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobOfferRestController {

   private final JobOfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<JobOfferResponseDto>> getOffers() {
        List<JobOfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}
