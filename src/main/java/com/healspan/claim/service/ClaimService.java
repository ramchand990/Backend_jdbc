package com.healspan.claim.service;

import com.healspan.claim.model.rpa.ResponseClaimStatusDto;

public interface ClaimService {
    String getHospitalUserDashboardData(long userId);

    String getHealspanUserDashboardData(long userId);

    String getClaimDetailAsPerClaimStageLinkID(long claimStageLinkId);

    String createOrUpdateClaimAndPatientInfo(String claimAndPatientInfo);

    String createOrUpdateMedicalInfo(String medicalInfo);

    String createOrUpdateInsuranceInfo(String insuranceInfo);

    String saveDocumentOfQuestionnaire(String documentInfo);

    String submitClaimStatus(String claimInfo);

    String changeStage(String stageDetails);

    String queryClaim(String commentInfo);

    String receiveTpaResponse(String rpaInfo);

    ResponseClaimStatusDto pushRpaClaim(String rpaInfo);

    String getOtherDocument(String otherDocumentInfo);

    String getDocumentTypeId(String getDocInfo);
}
