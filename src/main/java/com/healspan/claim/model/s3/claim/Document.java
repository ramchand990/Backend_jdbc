package com.healspan.claim.model.s3.claim;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Document {
    private long id;
    private int claimStageMstId;
    private String mandatoryDocumentName;
    private boolean status;
}
