package com.healspan.claim.model.s3;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DocumentResponse {

    private String id;
    private String fileName;
    private String filePath;
    private String stageLinkId;
}
