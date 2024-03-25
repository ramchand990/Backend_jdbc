package com.healspan.claim.repo;

import com.healspan.claim.constant.DocumentQueries;
import com.healspan.claim.model.s3.Document;
import com.healspan.claim.model.s3.DocumentResponse;
import com.healspan.claim.model.configuration.PropertyConfig;
import com.healspan.claim.util.ClaimUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Component
public class S3Repo {

    private static final Logger logger = LoggerFactory.getLogger(S3Repo.class);

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    ClaimUtil util;

    public String uploadDocument(long id, String fileName, String filePath) {
        logger.info("S3Repo::uploadDocument::START:: id{},filaName:{},filePath:{}", id, fileName, filePath);
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String idString = String.valueOf(id);
            logger.debug("Id: {}", idString);
            String query = MessageFormat.format(DocumentQueries.UPLOAD_DOCUMENT_QUERY, idString,
                    util.singleQuotes(fileName), util.singleQuotes(filePath));
            logger.debug("S3Repo::uploadDocument:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("S3Repo::uploadDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::uploadDocument::END::{}", jsonObject);
        return jsonObject;
    }

    public DocumentResponse getDocument(long documentId) {
        logger.info("S3Repo::downloadDocument::START::{}", documentId);
        DocumentResponse documentResponse = new DocumentResponse();
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String message = String.valueOf(documentId);
            logger.debug("DocumentId: {}", message);
            String query = MessageFormat.format(DocumentQueries.DOCUMENT_FETCH_QUERY, message);
            logger.debug("S3Repo::downloadDocument:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                documentResponse.setId(resultSet.getString("stage_link_id"));
                documentResponse.setFileName(resultSet.getString("file_name"));
                documentResponse.setFilePath(resultSet.getString("file_path"));
            }
        } catch (SQLException e) {
            logger.error("S3Repo::downloadDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::downloadDocument::END::{}", documentResponse);
        return documentResponse;
    }

    public List<Long> getClaimStageLinkIds(long claimId) {
        logger.info("S3Repo::getClaimStageLinkIds::START::claimId{}", claimId);
        List<Long> claimStageLinkIds = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(DocumentQueries.OPEN_TRANSACTION_QUERY, claimId);
            logger.debug("S3Repo::getClaimStageLinkIds:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                long claimStageLinkId = resultSet.getLong("id");
                claimStageLinkIds.add(claimStageLinkId);
            }
        } catch (SQLException e) {
            logger.error("S3Repo::getClaimStageLinkIds::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::getClaimStageLinkIds::END::{}", claimStageLinkIds);
        return claimStageLinkIds;
    }

    public List<Document> fetchDocumentsAsPerStageLinkId(List<Long> claimStageLinkIds) {
        logger.info("S3Repo::fetchDocumentsAsPerStageLinkId::START::claimStageLinkIds{}", claimStageLinkIds);
        List<Document> documents = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(DocumentQueries.DOCUMENT_AS_PER_STAGE_LINK_ID_QUERY
                    , claimStageLinkIds, "'N'");
            query = query.replaceAll("\\[", "").replaceAll("\\]", "");
            logger.debug("S3Repo::fetchDocumentsAsPerStageLinkId:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Document document = new Document();
                document.setId(resultSet.getLong("id"));
                document.setDocumentName(resultSet.getString("name"));
                document.setDocumentPath(resultSet.getString("path"));
                documents.add(document);
            }
        } catch (SQLException e) {
            logger.error("S3Repo::fetchDocumentsAsPerStageLinkId::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::fetchDocumentsAsPerStageLinkId::END::{}", documents);
        return documents;
    }

    public long getClaimStageLinkIdDetail(long claimId) {
        logger.info("S3Repo::getClaimStageLinkIds::START::claimId{}", claimId);
        Long claimStageLinkId = null;
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(DocumentQueries.GET_CLAIM_STAGE_LINK_DETAILS_QUERY, claimId);
            logger.debug("S3Repo::getClaimStageLinkIdDetail:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                claimStageLinkId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error("S3Repo::getClaimStageLinkIdDetail::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::getClaimStageLinkIdDetail::END::{}", claimStageLinkId);
        return claimStageLinkId;
    }

    public String deleteDocument(long docId) {
        logger.info("S3Repo::deleteDocument::START::claimId{}", docId);
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String docMessage = String.valueOf(docId);
            logger.debug("DocumentId:{}", docMessage);
            String query = MessageFormat.format(DocumentQueries.DOCUMENT_DELETE, docMessage);
            logger.debug("S3Repo::deleteDocument:QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("S3Repo::deleteDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("S3Repo::deleteDocument::END::{}", jsonObject);
        return jsonObject;

    }
}
