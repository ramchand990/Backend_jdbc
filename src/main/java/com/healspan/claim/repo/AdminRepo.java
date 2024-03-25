package com.healspan.claim.repo;

import com.healspan.claim.constant.AdminQueries;
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
public class AdminRepo {

    private static final Logger logger = LoggerFactory.getLogger(AdminRepo.class);

    @Autowired
    private PropertyConfig propertyConfig;

    private String dbCalling(String queryName, long info) {
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
        return jsonObject;
    }

    public String getTpaWiseMasterDetails(long tpaId) {
        logger.debug("ClaimRepo::getTpaWiseMasterDetails::{}::START", tpaId);
        String jsonObject = dbCalling(AdminQueries.TPA_WISE_MASTER_DETAILS, tpaId);
        logger.debug("ClaimRepo::getTpaWiseMasterDetails::END");
        return jsonObject;
    }

    public String getMasterDetails(int hospitalId) {
        logger.debug("ClaimRepo::getMasterDetails::{}::START", hospitalId);
        String jsonObject = dbCalling(AdminQueries.GET_MASTER_DETAILS, hospitalId);
        logger.debug("ClaimRepo::getMasterDetails::END");
        return jsonObject;
    }
}
