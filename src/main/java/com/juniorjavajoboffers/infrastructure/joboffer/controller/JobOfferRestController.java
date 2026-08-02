package com.juniorjavajoboffers.infrastructure.joboffer.controller;

import com.juniorjavajoboffers.domain.joboffer.JobOfferFacade;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferRequestDto;
import com.juniorjavajoboffers.domain.joboffer.dto.JobOfferResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<JobOfferResponseDto> findOfferById( @PathVariable String id) {
        JobOfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }
    @Operation(summary = "adding new job offer")
    @ApiResponse(responseCode = "201")
    @PostMapping("/offers/save")

    public ResponseEntity<JobOfferResponseDto> saveOffer(@RequestBody @Valid JobOfferRequestDto jobOfer){
        JobOfferResponseDto savedOffer = offerFacade.saveOffer(jobOfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }

}
