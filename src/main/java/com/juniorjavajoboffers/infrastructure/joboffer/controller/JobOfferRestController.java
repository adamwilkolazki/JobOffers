package com.juniorjavajoboffers.infrastructure.joboffer.controller;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFacade;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class JobOfferRestController {

    private final JobOfferFacade offerFacade;

    @GetMapping("/offers")
    @Operation(summary = "getting all job offers") //swagger
    @ApiResponse(responseCode = "200") //swagger
    public ResponseEntity<List<JobOfferResponseDto>> getOffers() {


        List<JobOfferResponseDto> allOffers = offerFacade.findAllOffers();

        return ResponseEntity.ok(allOffers);
    }

    @Operation(summary = "finding offer by id")
    @ApiResponse(responseCode = "200")
    @GetMapping("/offers/{id}")
    public ResponseEntity<JobOfferResponseDto> findOfferById(@PathVariable String id) {
        JobOfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }

}
