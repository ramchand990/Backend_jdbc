package com.healspan.claim.repo;

import com.healspan.claim.constant.AuthenticationQuery;
import com.healspan.claim.model.configuration.PropertyConfig;
import com.healspan.claim.model.login.LoginResponse;
import com.healspan.claim.util.ClaimUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;

import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Component
public class AuthenticationRepo {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationRepo.class);

    @Autowired
    private ClaimUtil util;

    @Autowired
    private PropertyConfig propertyConfig;

    public String authenticateUser(String authenticationRequest) {
        logger.info("AuthenticationRepo::authenticateUser::START::{}", authenticationRequest.getBytes());
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(AuthenticationQuery.USER_AUTHENTICATION, authenticationRequest);
            logger.debug("User Authentication QUERY::{}", query.getBytes());
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("AuthenticationRepo::authenticateUser::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("AuthenticationRepo::authenticateUser::END::{}", jsonObject);
        return jsonObject;
    }

    public LoginResponse getUserInformation(String userName) {
        logger.info("AuthenticationRepo::getUserInformation::START::{}", userName);
        LoginResponse response = null;
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(AuthenticationQuery.USER_AUTHENTICATION_BY_NAME, util.singleQuotes(userName));
            logger.debug("User Authentication QUERY::{}", query.getBytes());
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                response = new LoginResponse();
                response.setUserName(resultSet.getString("username"));
                response.setPassword(resultSet.getString("password"));
                response.setActive(resultSet.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            logger.error("AuthenticationRepo::getUserInformation::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("AuthenticationRepo::getUserInformation::END::{}", response.getJwt());
        return response;
    }
}
