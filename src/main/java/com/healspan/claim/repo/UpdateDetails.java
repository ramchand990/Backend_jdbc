package com.healspan.claim.repo;

import com.healspan.claim.constant.AdminQueries;
import com.healspan.claim.constant.Constant;
import com.healspan.claim.model.configuration.PropertyConfig;
import com.healspan.claim.model.admin.MasterDetails;
import com.healspan.claim.model.admin.MasterResponse;
import com.healspan.claim.model.admin.TableRequestParameters;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Component
public class UpdateDetails {
    @Autowired
    private PropertyConfig propertiesConfig;

    private static final Logger logger = LoggerFactory.getLogger(UpdateDetails.class);

    public MasterResponse updateMasterDetails(MasterDetails masterDetails) {
        logger.info("InsertDetails::updateMasterDetails::masterDetails::{}::START", masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String updateQuery = MessageFormat.format(AdminQueries.GENERIC_UPDATE_QUERY,
                masterDetails.getTableName(),parameters.getName(),parameters.getId());
        logger.debug("updateMasterDetails::update Query::{}",updateQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(), decrypt(propertiesConfig.getDbPassword()));
             PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::insertChronic_illness_Details::Exception::{}", ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
        }
        logger.info("InsertDetails::updateMasterDetails::masterResponse::{}::END",masterResponse);
        return masterResponse;
    }

    public MasterResponse updateMasterDetailsForHospitalMst(MasterDetails masterDetails) {
        logger.info("InsertDetails::updateMasterDetailsForHospitalMst::masterDetails::{}::START",masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String updateQuery = MessageFormat.format(AdminQueries.HOSPITAL_MST_UPDATE_QUERY,
                masterDetails.getTableName(),parameters.getHospitalUhid(),parameters.getName(),parameters.getId());
        logger.debug("updateMasterDetailsForHospitalMst::UpdateQuery::{}",updateQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(),decrypt(propertiesConfig.getDbPassword()));
             PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::updateMasterDetailsForHospitalMst::Exception::{}", ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
        }
        logger.info("InsertDetails::updateMasterDetailsForHospitalMst::masterResponse::{}::END",masterResponse);
        return masterResponse;
    }

    public MasterResponse updateMasterDetailsForUserMst(MasterDetails masterDetails) {
        logger.info("InsertDetails::updateMasterDetailsForUserMst::masterDetails::{}::START",masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String updateQuery = MessageFormat.format(AdminQueries.USER_MST_UPDATE_QUERY,masterDetails.getTableName()
                                , parameters.getUserName(), parameters.getFirstName(), parameters.getEmail()
                                , parameters.getIsActive(), parameters.getMobileNo(), parameters.getLastName()
                                , parameters.getPassword(), parameters.getHospitalMstId(), parameters.getUserRoleMstId()
                                , parameters.getId());
        logger.debug("updateMasterDetailsForUserMst::update Query::{}",updateQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(),decrypt(propertiesConfig.getDbPassword()));
             PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::updateMasterDetailsForUserMst::Exception::{}", ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
        }
        logger.info("InsertDetails::updateMasterDetailsForUserMst::masterResponse::{}::END", masterResponse);
        return masterResponse;
    }
}
