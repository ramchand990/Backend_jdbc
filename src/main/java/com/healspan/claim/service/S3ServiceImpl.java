package com.healspan.claim.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.healspan.claim.model.s3.Document;
import com.healspan.claim.model.s3.DocumentDownloadDto;
import com.healspan.claim.model.s3.DocumentResponse;
import com.healspan.claim.model.s3.ReportResponse;
import com.healspan.claim.repo.S3Repo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Service
public class S3ServiceImpl implements S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Value("${AWSAccessKeyId}")
    private String accessKeyId;
    @Value("${AWSSecretKey}")
    private String secretKey;
    @Value("${s3bucketname}")
    private String bucketName;
    @Autowired
    private S3Repo s3Repo;
    @Autowired
    ReportService reportService;

    @Override
    public String uploadFile(long documentId, long claimInfoId, MultipartFile multipartFile) throws IOException {
        logger.debug("S3FileServiceImpl::uploadFile requested documentId: {},claimInfoId: {} and multipartFile: {} --Start", documentId, claimInfoId, multipartFile);
        File file = null;

        String jsonString = "";
        String commaDelimiter = "/";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = formatter.format(new Date());
        String path = currentDate + commaDelimiter + claimInfoId;
        try {
            //get document details
            String currentPath = new File(".").getCanonicalPath();
            logger.debug("Convert the Multipart file received in request to regular file.");
            file = convertMultipartFile(multipartFile, Paths.get(currentPath));
            long timestamp = System.currentTimeMillis();
            logger.debug("Constructed file path for claimInfoId:{}, and documentName: {}_{}", claimInfoId, timestamp, file.getName());

            String fileName = timestamp + "_" + file.getName();

            logger.debug("Access AWS S3 service and upload the document to S3 on path: {}", path);
            AmazonS3 s3client = getS3Client();
            s3client.putObject(bucketName, path + "/" + timestamp + "_" + file.getName(), file);
            logger.debug("Document uploaded to S3.");

            // update the document table with path and filename

            jsonString = s3Repo.uploadDocument(documentId, fileName, path);
            logger.debug("S3FileServiceImpl::uploadFile response Status: {} --End", ResponseEntity.ok().build());
            return jsonString;

        } catch (Exception e) {
            logger.error("S3FileServiceImpl::uploadFile::Exception: {}", ExceptionUtils.getStackTrace(e));
            return jsonString;
        } finally {
            // Delete locally created file
            if (file.delete()) {
                logger.debug("S3FileServiceImpl::uploadFile -- TEMPORARY FILE DELETED FROM PROJECT FOLDER");
            } else logger.debug("S3FileServiceImpl::uploadFile -- TEMPORARY FILE NOT DELETED FROM PROJECT FOLDER");
        }
    }

    private File convertMultipartFile(MultipartFile multipart, Path dir) throws IOException {
        logger.debug("S3FileServiceImpl::convertMultipartFile requested multipart:{} and dir:{} --Start", multipart, dir);
        Path filepath = Paths.get(dir.toString(), multipart.getOriginalFilename());
        multipart.transferTo(filepath);
        logger.debug("S3FileServiceImpl::convertMultipartFile response file:{} --End", filepath.toFile());
        return filepath.toFile();
    }

    @Override
    public DocumentDownloadDto downloadFile(long documentId) {
        logger.debug("S3BucketDownloadServiceImpl::downloadFile requested documentId: {} --Start", documentId);
        DocumentDownloadDto dto = null;
        try {
            //get document
            logger.info("Get document details for documentId:{}", documentId);
            DocumentResponse document = s3Repo.getDocument(documentId);
            logger.info("DocumentUtil::getDocumentDetails --End");
            if (document != null) {
                logger.info("Document found in db for DocumentId: {} ", document.getId());
                dto = new DocumentDownloadDto();
                String path = document.getFilePath();
                String fileName = document.getFileName();
                dto.setFileName(fileName);
                logger.info("PATH::{}{}", path, fileName);
                logger.info("Access AWS S3 service and fetch the document from S3.");
                AmazonS3 s3client = getS3Client();
                S3Object documentObject = s3client.getObject(new GetObjectRequest(bucketName, path + "/" + fileName));

                if (documentObject != null) {
                    logger.info("Document object retrieved from S3.");

                    InputStream is = documentObject.getObjectContent();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len;
                    byte[] buffer = new byte[4096];

                    logger.info("Read the file data using S3 object and return the OutputStream. ");
                    while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    dto.setByteArrayOutputStream(outputStream);
                    return dto;
                } else
                    logger.info("Document object retrieval from S3 failed.");
            } else
                logger.info("Document NOT FOUND in db for DocumentId: {} ", documentId);

        } catch (IOException ioException) {
            logger.error("IOException::{}", ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException Message::{}", serviceException.getMessage());
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message::{}", clientException.getMessage());
        }
        logger.debug("S3BucketDownloadServiceImpl::downloadFile responseData: {} --End", dto);
        return dto;
    }

    @Override
    public byte[] downloadZipFile(long claimId) throws Exception {
        final String reportName = "healspan.jrxml";
        logger.info("S3BucketDownloadServiceImpl::downloadZipFile requested claimId: {} --Start", claimId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipFiles = new ZipOutputStream(byteArrayOutputStream);
        Map<String, Document> docList = new HashMap<>();
        Map<String, String> orgFileList = new HashMap<>();
        logger.info("Find all claimStageLinkId records in db for claimId:{}", claimId);
        List<Long> claimStageLinks = s3Repo.getClaimStageLinkIds(claimId);
        List<Document> documents = s3Repo.fetchDocumentsAsPerStageLinkId(claimStageLinks);

        if (!documents.isEmpty()) {
            AmazonS3 s3client = getS3Client();
            for (Document document : documents) {
                logger.info("trying to read document:{}", document);
                String path = document.getDocumentPath() + "/" + document.getDocumentName();
                if (document.getDocumentName() != null && document.getDocumentPath() != null && exists(path)) {
                    logger.info("check whether file is present in bucket or not: {}", exists(path));
                    if (!docList.containsKey(document.getDocumentName())) {
                        orgFileList.put(document.getDocumentName(), document.getDocumentName());
                        docList.put(document.getDocumentName(), document);
                    } else {
                        long milliTime = System.currentTimeMillis();
                        orgFileList.put(milliTime + "_" + document.getDocumentName(), document.getDocumentName());
                        document.setDocumentName(milliTime + "_" + document.getDocumentName());
                        docList.put(document.getDocumentName(), document);
                    }
                }
            }
            for (Map.Entry obj : docList.entrySet()) {
                Document document = (Document) obj.getValue();

                String path = document.getDocumentPath();
                String fileName = orgFileList.get(obj.getKey());
                S3Object documentObject = s3client.getObject(new GetObjectRequest(bucketName, path + "/" + fileName));
                InputStream is = documentObject.getObjectContent();
                zipFiles.putNextEntry(new ZipEntry((String) obj.getKey()));
                int len;
                byte[] buffer = new byte[4096];
                logger.info("Read the file data using S3 object and return the OutputStream.");
                while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                    zipFiles.write(buffer, 0, len);
                }

            }
        }
        ReportResponse responseData = reportService.patientReport(reportName, claimId);
        if (responseData != null) {
            zipFiles.putNextEntry(new ZipEntry(responseData.getFileName()));
            zipFiles.write(responseData.getReportData());
        }
        zipFiles.finish();
        zipFiles.close();
        logger.debug("S3BucketDownloadServiceImpl::downloadZipFile responseData: {} --End", claimId);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String deleteDocument(long docId) {
        String jsonString = "{\"responseStatus\": \"FAILED\"}";
        logger.debug("S3FileServiceImpl::deleteDocument:: docId:{} --START", docId);
        try {
            //get document
            DocumentResponse document = s3Repo.getDocument(docId);
            if (document != null) {
                logger.debug("Document found in db for DocumentId: {} ", document.getId());
                jsonString = s3Repo.deleteDocument(docId);
            }
        } catch (Exception e) {
            logger.error("DocumentUtil::deleteDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            return jsonString;
        }
        logger.debug("DocumentUtil::deleteDocument:: docId:{} --END", jsonString);
        return jsonString;
    }

    private boolean exists(String path) {
        logger.debug("S3FileServiceImpl::exists::requestedPath:{} --Start", path);
        AmazonS3 s3client = getS3Client();
        logger.debug("S3FileServiceImpl::exists::responseData:{} --End", bucketName + "and" + path);
        return s3client.doesObjectExist(bucketName, path);
    }

    @Override
    public void deleteFile(String path) {
        logger.debug("S3FileServiceImpl::deleteFile requestedPath: {} --Start", path);
        AmazonS3 s3client = getS3Client();
        logger.info("Delete file from aws s3 for filePath:{}", path);
        s3client.deleteObject(bucketName, path);
        logger.debug("S3FileServiceImpl::deleteFile --End");
    }


    @Override
    public DocumentDownloadDto previewDocument(long docId) {
        logger.debug("S3FileServiceImpl::previewDocument::requestedPath::{} --START", docId);
        DocumentDownloadDto dto = null;
        try {
            //get document
            DocumentResponse document = s3Repo.getDocument(docId);
            if (document.getFilePath() != null) {
                String filePath = document.getFilePath();
                String fileName = document.getFileName();
                String absolutePath = document.getFilePath() + "/" + document.getFileName();
                if (exists(absolutePath)) {
                    AmazonS3 s3Client = getS3Client();
                    InputStream inputStream = s3Client.getObject(new GetObjectRequest(bucketName, filePath + "/" + fileName)).getObjectContent();
                    dto = new DocumentDownloadDto();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    int len;
                    byte[] buffer = new byte[4096];

                    logger.info("Read the file data using S3 object and return the OutputStream.");
                    while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    dto.setFileName(fileName);
                    dto.setByteArrayOutputStream(outputStream);
                }
            }
        } catch (IOException e) {
            logger.error("DocumentUtil::previewDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("S3FileServiceImpl::previewDocument requestedPath: {} --END", dto.getFileName());
        return dto;
    }

    public AmazonS3 getS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, decrypt(secretKey));
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }
}
