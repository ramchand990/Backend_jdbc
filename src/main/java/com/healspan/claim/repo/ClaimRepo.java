package com.healspan.claim.repo;

import com.healspan.claim.constant.ClaimQueries;
import com.healspan.claim.constant.DocumentQueries;
import com.healspan.claim.model.configuration.PropertyConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;

import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Component
public class ClaimRepo {

    private static final Logger logger = LoggerFactory.getLogger(ClaimRepo.class);

    @Autowired
    private PropertyConfig propertyConfig;

    private String dbCalling(String queryName, String info) {
        logger.debug("ClaimRepo::dbCalling::queryName: {},info: {} --Start", queryName, info);
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(queryName, info);
            logger.debug("dbCalling QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("ClaimRepo::dbCalling::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimRepo::dbCalling::jsonObject: {} --End", jsonObject);
        return jsonObject;
    }

    private String dbCalling(String queryName, long info) {
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String infoString = String.valueOf(info);
            logger.debug("infoId:{}", infoString);
            String query = MessageFormat.format(queryName, infoString);
            logger.debug("dbCalling QUERY::{}", query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("ClaimRepo::dbCalling::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return jsonObject;
    }

    public String getHospitalUserDashboardDataFromDb(long loggerInUserId) {
        logger.debug("ClaimRepo::getHospitalUserDashboardDataFromDb::START::{}", loggerInUserId);
        String jsonObject = dbCalling(ClaimQueries.HOSPITAL_DASHBOARD_QUERY, loggerInUserId);
        logger.debug("ClaimRepo::getHospitalUserDashboardDataFromDb::END::{}", jsonObject);
        return jsonObject;
    }

    public String getHealspanUserDashboardDataFromDb(long loggerInUserId) {
        logger.debug("ClaimRepo::getHealspanUserDashboardDataFromDb::START::{}", loggerInUserId);
        String jsonObject = dbCalling(ClaimQueries.HEALSPAN_DASHBOARD_QUERY, loggerInUserId);
        logger.debug("ClaimRepo::getHealspanUserDashboardDataFromDb::END::{}", jsonObject);
        return jsonObject;
    }

    public String getClaimDetailAsPerClaimStageLinkID(long claimStageLinkId) {
        logger.debug("ClaimRepo::getClaimDetailAsPerClaimStageLinkID::DateTime::{}::START", claimStageLinkId);
        String jsonObject = dbCalling(ClaimQueries.CLAIM_FETCH_QUERY, claimStageLinkId);
        logger.debug("ClaimRepo::getClaimDetailAsPerClaimStageLinkID::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String createOrUpdateClaimAndPatientInfo(String claimAndPatientInfo) {
        logger.debug("ClaimRepo::createOrUpdateClaimAndPatientInfo::DateTime::{}::START", claimAndPatientInfo);
        String jsonObject = dbCalling(ClaimQueries.CLAIM_PATIENT_INFO_CREATE_OR_UPDATE_QUERY, claimAndPatientInfo);
        logger.debug("ClaimRepo::createOrUpdateClaimAndPatientInfo::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String createOrUpdateMedicalInfo(String medicalInfo) {
        logger.debug("ClaimRepo::createOrUpdateMedicalInfo::DateTime::{}::START", medicalInfo);
        String jsonObject = dbCalling(ClaimQueries.MEDICAL_INFO_CREATE_OR_UPDATE_QUERY, medicalInfo);
        logger.debug("ClaimRepo::createOrUpdateMedicalInfo::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String createOrUpdateInsuranceInfo(String insuranceInfo) {
        logger.debug("ClaimRepo::createOrUpdateInsuranceInfo::DateTime::{}::START", insuranceInfo);
        String jsonObject = dbCalling(ClaimQueries.INSURANCE_INFO_CREATE_OR_UPDATE_QUERY, insuranceInfo);
        logger.debug("ClaimRepo::createOrUpdateInsuranceInfo::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String saveDocumentOfQuestionnaire(String documentInfo) {
        logger.debug("ClaimRepo::saveDocumentOfQuestionnaire::DateTime::{}::START", documentInfo);
        String jsonObject = dbCalling(ClaimQueries.SAVE_QUESTIONNAIRE_DOCUMENT_QUERY, documentInfo);
        logger.debug("ClaimRepo::saveDocumentOfQuestionnaire::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String submitClaimStatus(String claimInfo) {
        logger.debug("ClaimRepo::submitClaimStatus::DateTime::{}::START", claimInfo);
        String jsonObject = dbCalling(ClaimQueries.SUBMIT_CLAIM_INFO, claimInfo);
        logger.debug("ClaimRepo::submitClaimStatus::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String changeStage(String stageDetails) {
        logger.debug("ClaimRepo::changeStage::DateTime::{}::START", stageDetails);
        String jsonObject = dbCalling(ClaimQueries.CHANGE_CLAIM_STAGE, stageDetails);
        logger.debug("ClaimRepo::changeStage::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String queryClaim(String commentInfo) {
        logger.debug("ClaimRepo::queryClaim::DateTime::{}::START", commentInfo);
        String jsonObject = dbCalling(ClaimQueries.QUERY_ON_CLAIM, commentInfo);
        logger.debug("ClaimRepo::queryClaim::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String approveClaim(String commentInfo) {
        logger.debug("ClaimRepo::approveClaim::DateTime::{}::START", commentInfo);
        String jsonObject = dbCalling(ClaimQueries.APPROVE_CLAIM, commentInfo);
        logger.debug("ClaimRepo::approveClaim::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String receiveTpaResponse(String tpaInfo) {
        logger.debug("ClaimRepo::receiveTpaResponse::DateTime::{}::START", tpaInfo);
        String jsonObject = dbCalling(ClaimQueries.TPA_UPDATE_QUERY, tpaInfo);
        logger.debug("ClaimRepo::receiveTpaResponse::DateTime::{}::END", jsonObject);
        return jsonObject;
    }


    public String getOtherDocument(String otherDocumentInfo) {
        logger.debug("ClaimRepo::getOtherDocument::DateTime::{}::START", otherDocumentInfo);
        String jsonObject = dbCalling(DocumentQueries.GET_OTHER_DOCUMENT, otherDocumentInfo);
        logger.debug("ClaimRepo::getOtherDocument::DateTime::{}::END", jsonObject);
        return jsonObject;
    }

    public String getDocumentTypeId(String getDocInfo) {
        logger.debug("ClaimRepo::getDocumentTypeId::DateTime::{}::START", getDocInfo);
        String jsonObject = dbCalling(DocumentQueries.GET_DOCUMENT_TYPE, getDocInfo);
        logger.debug("ClaimRepo::getDocumentTypeId::DateTime::{}::END", jsonObject);
        return jsonObject;

    }
}
