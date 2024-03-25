package com.healspan.claim.service;

import com.healspan.claim.model.s3.DocumentDownloadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    String uploadFile(long documentId, long claimInfoId, MultipartFile multipartFile) throws IOException;

    DocumentDownloadDto downloadFile(long documentId);

    byte[] downloadZipFile(long claimId) throws Exception;

    public void deleteFile(String path);

    String deleteDocument(long docId);

    DocumentDownloadDto previewDocument(long documentId);
}
