package com.healspan.claim.model.s3;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.ByteArrayOutputStream;

@Data
@NoArgsConstructor
@ToString
public class DocumentDownloadDto {

    private ByteArrayOutputStream byteArrayOutputStream;
    private String fileName;
}
