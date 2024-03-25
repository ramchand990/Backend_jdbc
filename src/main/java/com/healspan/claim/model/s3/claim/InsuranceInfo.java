package com.healspan.claim.model.s3.claim;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class InsuranceInfo {

    private long id;
    private String tpaIdCardNumber;
    private String policyHolderName;
    private String insuranceCompanyName;
    private String relationshipName;
    private String tpaName;
    private long approvedInitialAmount;
    private long approvalAmountFinalStage;
    private long approvedAmountAtDischarge;
    private long approvedEnhancementsAmount;
    private long settledAmount;

}
