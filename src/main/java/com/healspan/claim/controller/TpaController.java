package com.healspan.claim.controller;

import com.healspan.claim.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/tpa/claim")
public class TpaController {

    @Autowired
    private ClaimService claimService;

    @PostMapping(value = "/tparesponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receiveTpaResponse(@RequestBody String rpaInfo) {
        return new ResponseEntity<>(claimService.receiveTpaResponse(rpaInfo), HttpStatus.OK);
    }

}
