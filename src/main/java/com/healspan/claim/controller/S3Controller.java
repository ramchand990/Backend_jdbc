package com.healspan.claim.controller;

import com.healspan.claim.constant.Constant;
import com.healspan.claim.model.s3.DocumentDownloadDto;
import com.healspan.claim.service.S3Service;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/healspan/claim")
public class S3Controller {

    private static final Logger logger = LoggerFactory.getLogger(S3Controller.class);

    @Autowired
    private S3Service service;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam String inputDocId, @RequestParam String claimInfoId, @RequestParam("file") MultipartFile file) {
        String jsonObject = "";
        try {
            logger.info("Upload document request received for DocumentId:{},claimInfoId:{} and file:{}", inputDocId, claimInfoId, file);
            if (inputDocId != null && !inputDocId.isEmpty() &&
                    claimInfoId != null && !claimInfoId.isEmpty() &&
                    !file.isEmpty()) {
                long documentId = Long.parseLong(inputDocId);
                long claimId = Long.parseLong(claimInfoId);

                jsonObject = service.uploadFile(documentId, claimId, file);
                if ("Success".equalsIgnoreCase(jsonObject)) {
                    logger.info("Request to upload document processed successfully.");
                } else {
                    logger.info("Request processed but file not Uploaded.");
                }
            } else {
                logger.info("Request is not processed also file is not Uploaded.");
            }
        } catch (Exception e) {
            logger.error("S3Controller::uploadDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }


    @GetMapping(value = "/download/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity downloadFile(@PathVariable long documentId) {
        logger.info("Download file request received for DocumentId:{}", documentId);
        DocumentDownloadDto downloadFile = service.downloadFile(documentId);

        if (downloadFile == null) {
            logger.info("Request processed but file not downloaded. ");
            return ResponseEntity.notFound().build();
        }
        logger.info("Request to download document processed successfully.");
        return ResponseEntity.ok()
                .contentType(contentType(downloadFile.getFileName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFile.getFileName() + "\"")
                .header("Access-Control-Expose-Headers","*")
                .body(downloadFile.getByteArrayOutputStream().toByteArray());
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping(value = "/downloadZip/{claimId}")
    public ResponseEntity downloadFileZip(@PathVariable Long claimId) {
        logger.debug("Request received to download zip file for claimId:{}", claimId);
        try {
            byte[] data = service.downloadZipFile(claimId);
            if (data != null && data.length > 0) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/zip"))
                        .header("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis() + ".zip" + "\"")
                        .header("Access-Control-Expose-Headers","*")
                        .body(data);
            } else {
                return new ResponseEntity(Constant.ResponseStatus.FAILED, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("S3Controller::downloadFileZip::Exception::{}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-documents/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable long documentId) {
        String jsonObject = "";
        try {
            jsonObject = service.deleteDocument(documentId);
        } catch (Exception e) {
            logger.error("S3Controller::deleteDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/preview-document/{documentId}")
    public ResponseEntity<byte[]> previewDocument(@PathVariable long documentId) {
        try {
            DocumentDownloadDto previewData = service.previewDocument(documentId);
            if(previewData != null){
                return ResponseEntity.ok()
                        .contentType(contentType(previewData.getFileName()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + previewData.getFileName() + "\"")
                        .body(previewData.getByteArrayOutputStream().toByteArray());
            } else {
                return new ResponseEntity(Constant.ResponseStatus.FAILED, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("S3Controller::previewDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.notFound().build();
        }
    }
}
