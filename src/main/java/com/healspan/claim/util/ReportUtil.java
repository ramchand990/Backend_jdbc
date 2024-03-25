package com.healspan.claim.util;

import com.google.gson.Gson;
import com.healspan.claim.model.s3.ReportDto;
import com.healspan.claim.model.s3.claim.*;
import com.healspan.claim.repo.ClaimRepo;
import com.healspan.claim.repo.S3Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportUtil {
    private final Logger logger = LoggerFactory.getLogger(ReportUtil.class);

    @Autowired
    private S3Repo s3Repo;

    @Autowired
    private ClaimRepo claimRepo;

    public List<ReportDto> generateReportData(Long claimInfoId) {
        logger.debug("ReportUtil::reportData Request received for patient data in report for claimInfoId: {} --Start", claimInfoId);
        List<ReportDto> data = null;
        if (claimInfoId != null) {
            logger.debug("Find claimStageLink record in db for claimId: {}", claimInfoId);
            Long id = s3Repo.getClaimStageLinkIdDetail(claimInfoId);
            if (id != null) {
                logger.debug("Find claimStageLink record in db for claimStageLinkId: {}", id);
                data = new ArrayList<>();
                String claimJson = claimRepo.getClaimDetailAsPerClaimStageLinkID(id);
                Claim claim = new Gson().fromJson(claimJson, Claim.class);
                PatientInfo patientInfo = claim.getPatientInfo();
                MedicalInfo medicalInfo = claim.getMedicalInfo();
                InsuranceInfo insuranceInfo = claim.getInsuranceInfo();
                List<Document> documentList = claim.getDocumentList();
                List<String> initialDoc = new ArrayList<>();
                List<String> enhanceDoc = new ArrayList<>();
                List<String> dischargeDoc = new ArrayList<>();
                List<String> finalDoc = new ArrayList<>();
                ReportDto reportDto = new ReportDto();
                reportDto.setName(patientInfo.getFirstName() + " " + patientInfo.getMiddleName() + " " + patientInfo.getLastname());
                reportDto.setAge(String.valueOf(patientInfo.getAge()));
                reportDto.setGender(patientInfo.getGender());
                reportDto.setDob(String.valueOf(patientInfo.getDateBirth()).split("T")[0]);
                reportDto.setProcedure(medicalInfo.getProcedureName());
                reportDto.setTreatmentType(medicalInfo.getTreatmentTypeName());
                reportDto.setSpeciality(medicalInfo.getSpecialityName());
                reportDto.setDateOfFirstDiagnosis(String.valueOf(medicalInfo.getDateOfFirstDiagnosis()).split("T")[0]);
                reportDto.setDoctorName(medicalInfo.getDoctorName());
                reportDto.setDoctorRegistrationNo(medicalInfo.getDoctorRegistrationNumber());
                reportDto.setDoctorQualification(medicalInfo.getDoctorQualification());
                reportDto.setInsuranceCompany(insuranceInfo.getInsuranceCompanyName());
                reportDto.setTpa(insuranceInfo.getTpaName());
                reportDto.setTpaIdCardNo(insuranceInfo.getTpaIdCardNumber());
                reportDto.setRelation(insuranceInfo.getRelationshipName());
                reportDto.setPolicyHolder(insuranceInfo.getPolicyHolderName());
                reportDto.setApprovedInitialAmount(insuranceInfo.getApprovedInitialAmount());
                reportDto.setApprovalAmountFinalStage(insuranceInfo.getApprovalAmountFinalStage());
                reportDto.setApprovedAmountAtDischarge(insuranceInfo.getApprovedAmountAtDischarge());
                reportDto.setApprovedEnhancementsAmount(insuranceInfo.getApprovedEnhancementsAmount());
                reportDto.setSettledAmount(insuranceInfo.getSettledAmount());
                reportDto.setInitialCostEstimate(patientInfo.getInitialCostEstimate());
                reportDto.setEnhancementEstimate(patientInfo.getEnhancementEstimate());
                reportDto.setFinalBillAmount(patientInfo.getFinalBillAmount());
                reportDto.setClaimedAmount(patientInfo.getClaimedAmount());
                for (Document document : documentList) {
                    if (document.getClaimStageMstId() == 1 && document.isStatus()==true) {
                        initialDoc.add(document.getMandatoryDocumentName());
                    } else if (document.getClaimStageMstId() == 2 && document.isStatus()==true) {
                        enhanceDoc.add(document.getMandatoryDocumentName());
                    } else if (document.getClaimStageMstId() == 3 && document.isStatus()==true) {
                        dischargeDoc.add(document.getMandatoryDocumentName());
                    } else if (document.getClaimStageMstId() == 4 && document.isStatus()==true){
                        finalDoc.add(document.getMandatoryDocumentName());
                    }
                }
                reportDto.setInitDocumentName(initialDoc);
                reportDto.setEnhDocumentName(enhanceDoc);
                reportDto.setDisDocumentName(dischargeDoc);
                reportDto.setFnlDocumentName(finalDoc);
                data.add(reportDto);
            }
        }
        logger.debug("ReportUtil::reportData responseData: {} --End", data);
        return data;
    }
}
