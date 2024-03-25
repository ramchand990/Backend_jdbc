package com.healspan.claim.model.s3.claim;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MedicalInfo {

    private String dateOfFirstDiagnosis;
    private String doctorName;
    private String doctorRegistrationNumber;
    private String doctorQualification;
    private String procedureName;
    private String treatmentTypeName;
    private String specialityName;
}
