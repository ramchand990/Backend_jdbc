package com.healspan.claim.model.s3;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class  ReportDto {

    private String name;
    private String age;
    private String gender;
    private String dob;
    private String insuranceCompany;
    private String tpa;
    private String tpaIdCardNo;
    private String relation;
    private String policyHolder;
    private String procedure;
    private String treatmentType;
    private String speciality;
    private String dateOfFirstDiagnosis;
    private String doctorName;
    private String doctorRegistrationNo;
    private String doctorQualification;
    private List<String> initDocumentName;
    private List<String> enhDocumentName;
    private List<String> disDocumentName;
    private List<String> fnlDocumentName;
    private String totalRoomCost;
    private long initialCostEstimate;
    private long enhancementEstimate;
    private long finalBillAmount;
    private long claimedAmount;
    private long approvedInitialAmount;
    private long approvalAmountFinalStage;
    private long approvedAmountAtDischarge;
    private long approvedEnhancementsAmount;
    private long settledAmount;

}