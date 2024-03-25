package com.healspan.claim.model.s3.claim;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class PatientInfo {

    private int id;
    private String firstName;
    private String middleName;
    private String lastname;
    private int age;
    private String gender;
    private String dateBirth;
    private long initialCostEstimate;
    private long enhancementEstimate;
    private long finalBillAmount;
    private long claimedAmount;


}
