package com.healspan.claim.model.rpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class SpecificContent {
    private String iHave;
    private String healsanClaimId;
    private String tpaClaimNumber;
    private String hsClaimNumber;
    private String tpaName;
    private String memberId;
    private String fullName;
    private String mobileNo;
    private String dateOfAdmission;
    private String dateOfDischarge;
    private String doctorName;
    private String doctorRegNumber;
    private String roomType;
    private String proposedLineOfTreatment;
    private String treatment;
    private String diagnosis;
    private String diagnosisMultipleOption;
    private String packageName;
    private String roomRentAndNursingCharges;
    private long packageAmount;
    private String stageName;
    private String isDischargeToday;
    private String consultation;
    private String documentList;
}
