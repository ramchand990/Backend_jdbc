package com.healspan.claim.model.s3;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Document {

    private Long id;
    private String documentName;
    private String documentPath;
    private boolean status;
}
