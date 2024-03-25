package com.healspan.claim.model.s3;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ReportResponse {
    String fileName;
    byte[] reportData;
}
