package com.healspan.claim.controller;

import com.healspan.claim.model.rpa.ResponseClaimStatusDto;
import com.healspan.claim.service.ClaimService;
import com.healspan.claim.util.ClaimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/healspan/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private ClaimUtil claimUtil;

    /**
     * @param userId
     * @return Hospital user dashboard details in a json format which gets generated from db based on userId passed.
     */
    @GetMapping(value = "/hospitaluserdashboarddata/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalUserDashboardData(@PathVariable long userId) {
        if(!claimUtil.emptyJsonStringCheck(claimService.getHospitalUserDashboardData(userId))){
            return new ResponseEntity<>(claimService.getHospitalUserDashboardData(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(claimService.getHospitalUserDashboardData(userId), HttpStatus.NO_CONTENT);
    }

    /**
     * @param userId
     * @return Healspan user dashboard details in a json format which gets generated from db based on userId passed.
     */
    @GetMapping(value = "/healspanuserdashboarddata/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealspanUserDashboardData(@PathVariable long userId) {
        if(!claimUtil.emptyJsonStringCheck(claimService.getHealspanUserDashboardData(userId))){
            return new ResponseEntity<>(claimService.getHealspanUserDashboardData(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(claimService.getHealspanUserDashboardData(userId), HttpStatus.NO_CONTENT);
    }

    /**
     * @param claimStageLinkId The person who is Logged in the system.
     * @return Claim details in a json format which gets generated from db based on passed Claim_Stage_Link_ID.
     */
    @GetMapping(value ="/retrieveclaim/{claimStageLinkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getClaimDetailAsPerClaimStageLinkID (@PathVariable long claimStageLinkId){
        if(!claimUtil.emptyJsonStringCheck(claimService.getClaimDetailAsPerClaimStageLinkID(claimStageLinkId))){
            return new ResponseEntity<>(claimService.getClaimDetailAsPerClaimStageLinkID(claimStageLinkId),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(claimService.getClaimDetailAsPerClaimStageLinkID(claimStageLinkId),
                HttpStatus.NO_CONTENT);
    }


    /**
     * @param claimAndPatientInfo - String data Contains the Claim And Patient information details along with its ID.
     * @return Claim ID and Patient Info ID details fetched from DB which provide the detail information of creation or
     *         updation of the claim and Patient info.
     */
    @PostMapping(value ="/createorupdateclaimandpatientinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrUpdateClaimAndPatientInfo(@RequestBody String claimAndPatientInfo){

        return new ResponseEntity<>(claimService.createOrUpdateClaimAndPatientInfo(claimAndPatientInfo), HttpStatus.OK);
    }


    /**
     * @param medicalInfo - String data Contains the Medical information details along with its ID.
     * @return Medical Info details fetched from DB which provide the detail information of creation or
     * updation of the Medical info.
     */
    @PostMapping(value ="/createorupdatemedicalinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrUpdateMedicalInfo(@RequestBody String medicalInfo){

        return new ResponseEntity<>(claimService.createOrUpdateMedicalInfo(medicalInfo), HttpStatus.OK);
    }

    /**
     * @param insuranceInfo - String data Contains the Insurance information details along with its ID.
     * @return Insurance Info details fetched from DB which provide the detail information of creation or
     * updation of the Insurance info.
     */
    @PostMapping(value ="/createorupdateinsuranceinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrUpdateInsuranceInfo(@RequestBody String insuranceInfo){

        return new ResponseEntity<>(claimService.createOrUpdateInsuranceInfo(insuranceInfo), HttpStatus.OK);
    }

    /**
     * @param documentInfo - String data Contains the Question Answer along with its ID.
     * @return Question Answer details fetched from DB which provide the information of creation
     * of the Question Answers.
     */
    @PostMapping(value ="/savequestionnairesanddocument", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveDocumentOfQuestionnaire(@RequestBody String documentInfo){

        return new ResponseEntity<>(claimService.saveDocumentOfQuestionnaire(documentInfo), HttpStatus.OK);
    }

    /**
     * @param claimInfo
     * @return
     */
    @PostMapping(value ="/updateclaimstatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitClaimStatus(@RequestBody String claimInfo){

        return new ResponseEntity<>(claimService.submitClaimStatus(claimInfo), HttpStatus.OK);
    }

    /**
     * @param stageDetails
     * @return
     */
    @PostMapping(value ="/updatestage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeStage(@RequestBody String stageDetails){

        return new ResponseEntity<>(claimService.changeStage(stageDetails), HttpStatus.OK);
    }

    /**
     * @param commentInfo
     * @return
     */
    @PostMapping(value ="/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> queryClaim(@RequestBody String commentInfo){

        return new ResponseEntity<>(claimService.queryClaim(commentInfo), HttpStatus.OK);
    }

    /**
     * @param rpaInfo
     * @return
     */
    @PostMapping("/pushclaimdatatorpa")
    public ResponseEntity<ResponseClaimStatusDto> pushRpaClaim(@RequestBody String rpaInfo) {
        return new ResponseEntity<>(claimService.pushRpaClaim(rpaInfo), HttpStatus.OK);
    }

    /**
     * @param otherDocumentInfo
     * @return
     */
    @GetMapping("/otherdiagnosisdocument/{otherDocumentInfo}")
    public ResponseEntity<String> getOtherDocument(@PathVariable String otherDocumentInfo) {
        return new ResponseEntity<>(claimService.getOtherDocument(otherDocumentInfo), HttpStatus.OK);
    }

    /**
     * @param getDocInfo -
     * @return
     */
    @PostMapping("/getdocument")
    public ResponseEntity<String> getDocumentTypeId(@RequestBody String getDocInfo) {
        return new ResponseEntity<>(claimService.getDocumentTypeId(getDocInfo), HttpStatus.OK);
    }
}
