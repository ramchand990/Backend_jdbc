package com.healspan.claim.controller;

import com.healspan.claim.model.admin.MasterDetails;
import com.healspan.claim.model.admin.MasterResponse;
import com.healspan.claim.repo.InsertDetails;
import com.healspan.claim.repo.UpdateDetails;
import com.healspan.claim.service.AdminService;
import com.healspan.claim.util.ClaimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/healspan/claim")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClaimUtil claimUtil;

    @Autowired
    private InsertDetails insertDetails;

    @Autowired
    private UpdateDetails updateDetails;

    @PostMapping("/admin/masters/insert")
    public ResponseEntity<MasterResponse> createByTableName(@RequestBody MasterDetails masterDetails) {
        MasterResponse response;
        if ("user_mst".equalsIgnoreCase(masterDetails.getTableName())) {
            response = insertDetails.insertMasterDetailsForUserMst(masterDetails);
        } else if ("hospital_mst".equalsIgnoreCase(masterDetails.getTableName())) {
            response = insertDetails.insertMasterDetailsForHospitalMst(masterDetails);
        } else {
            response = insertDetails.insertMasterDetails(masterDetails);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/admin/masters/update")
    public ResponseEntity<MasterResponse> updateByTableName(@RequestBody MasterDetails masterDetails) {
        MasterResponse response;
        if ("user_mst".equalsIgnoreCase(masterDetails.getTableName())) {
            response = updateDetails.updateMasterDetailsForUserMst(masterDetails);
        } else if ("hospital_mst".equalsIgnoreCase(masterDetails.getTableName())) {
            response = updateDetails.updateMasterDetailsForHospitalMst(masterDetails);
        } else {
            response = updateDetails.updateMasterDetails(masterDetails);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/get-tpawise-master-details/{tpaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTpaWiseMasterDetails(@PathVariable long tpaId) {
        return new ResponseEntity<>(adminService.getTpaWiseMasterDetails(tpaId), HttpStatus.OK);
    }

    /**
     * @return - All the Hospital Data which are stored inside the DB.
     * If the DB return the Json data then provide the same as
     * response else returns null data with NO_CONTENT as HttpStatus.
     */
    @GetMapping(value = "/get-master-details/{hospitalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMasterDetails(@PathVariable int hospitalId) {
        return new ResponseEntity<>(adminService.getMasterDetails(hospitalId), HttpStatus.OK);
    }
}
