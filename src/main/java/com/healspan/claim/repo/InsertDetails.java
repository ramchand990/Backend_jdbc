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

@Component
public class InsertDetails {

    @Autowired
    private PropertyConfig propertiesConfig;

    private static final Logger logger = LoggerFactory.getLogger(InsertDetails.class);

    public MasterResponse insertMasterDetails(MasterDetails masterDetails) {
        logger.debug("InsertDetails::insertMasterDetails::MasterDetails::{}::START",masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String insertQuery = MessageFormat.format(AdminQueries.GENERIC_INSERT_QUERY,masterDetails.getTableName());
        logger.debug("Generic Insert query::{}",insertQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(), propertiesConfig.getDbPassword());
             PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, parameters.getName());
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::insertMasterDetails::Exception::{}", ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
            e.printStackTrace();
        }
        logger.debug("InsertDetails::insertMasterDetails::masterResponse::{}::END", masterResponse);
        return masterResponse;
    }

    public MasterResponse insertMasterDetailsForHospitalMst(MasterDetails masterDetails) {
        logger.debug("InsertDetails::insertMasterDetailsForHospitalMst::masterDetails::{}::START", masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String insertQuery = MessageFormat.format(AdminQueries.HOSPITAL_MST_INSERT_QUERY,masterDetails.getTableName());
        logger.debug("Hospital Master Insert query::{}",insertQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(), propertiesConfig.getDbPassword());
             PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, parameters.getHospitalUhid());
            pstmt.setString(2, parameters.getName());
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::insertMasterDetailsForHospitalMst::Exception::{}", ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
            e.printStackTrace();
        }
        logger.debug("InsertDetails::insertMasterDetailsForHospitalMst::masterResponse::{}::END", masterResponse);
        return masterResponse;
    }

    public MasterResponse insertMasterDetailsForUserMst(MasterDetails masterDetails) {
        logger.debug("InsertDetails::insertMasterDetailsForUserMst::MasterDetails: {} --START", masterDetails);
        TableRequestParameters parameters = masterDetails.getRequestParameters();
        MasterResponse masterResponse = new MasterResponse();
        masterResponse.setTableName(masterDetails.getTableName());
        String insertQuery = MessageFormat.format(AdminQueries.USER_MST_INSERT_QUERY,masterDetails.getTableName());
        logger.debug("User Master Insert query::{}",insertQuery);
        try (Connection con = DriverManager.getConnection(propertiesConfig.getDbUrl(),
                propertiesConfig.getDbUsername(), propertiesConfig.getDbPassword());
             PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, parameters.getUserName());
            pstmt.setString(2, parameters.getEmail());
            pstmt.setString(3, parameters.getFirstName());
            pstmt.setBoolean(4, parameters.getIsActive());
            pstmt.setString(5, parameters.getMobileNo());
            pstmt.setString(6, parameters.getLastName());
            pstmt.setString(7, parameters.getPassword());
            pstmt.setLong(8, parameters.getHospitalMstId());
            pstmt.setLong(9, parameters.getUserRoleMstId());
            pstmt.executeUpdate();
            masterResponse.setStatus(Constant.ResponseStatus.SUCCESS.name());
        } catch (SQLException e) {
            logger.error("InsertDetails::insertMasterDetailsForUserMst::Exception::{}",ExceptionUtils.getStackTrace(e));
            masterResponse.setStatus(Constant.ResponseStatus.FAILED.name());
            e.printStackTrace();
        }
        logger.debug("InsertDetails::insertMasterDetailsForUserMst::responseData::{} --END",masterResponse);
        return masterResponse;
    }
}
