package com.healspan.claim.model.s3.claim;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Claim {
    private int id;
    private PatientInfo patientInfo;
    private MedicalInfo medicalInfo;
    private InsuranceInfo insuranceInfo;
    private List<Document> documentList;
}
