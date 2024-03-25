package com.healspan.claim.repo;

import com.healspan.claim.constant.NotificationQuery;
import com.healspan.claim.model.configuration.PropertyConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import static com.healspan.claim.util.EncryptionUtil.decrypt;

@Component
public class NotificationRepo {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRepo.class);

    @Autowired
    private PropertyConfig propertyConfig;

    private String dbCalling(String queryName,long info){
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(queryName,info);
            logger.debug("dbCalling QUERY::{}",query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject =resultSet.getString(1);
            }
        }catch (SQLException e){
            logger.error("ClaimRepo::dbCalling::Exception::{}" , ExceptionUtils.getStackTrace(e));
        }
        return jsonObject;
    }

    private String dbCalling(String queryName,String info){
        String jsonObject = "";
        try (Connection con = DriverManager.getConnection(
                propertyConfig.getDbUrl(), propertyConfig.getDbUsername(), decrypt(propertyConfig.getDbPassword()));
             Statement stmt = con.createStatement()) {
            String query = MessageFormat.format(queryName,info);
            logger.debug("dbCalling QUERY::{}",query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                jsonObject =resultSet.getString(1);
            }
        }catch (SQLException e){
            logger.error("ClaimRepo::dbCalling::Exception::{}" , ExceptionUtils.getStackTrace(e));
        }
        return jsonObject;
    }

    public String getUserNotification(long loggerInUserId) {
        logger.info("NotificationRepo::getUserNotification::START::loggerInUserId{}", loggerInUserId);
        String jsonObject = dbCalling(NotificationQuery.GET_NOTIFICATION,loggerInUserId);
        logger.info("NotificationRepo::getUserNotification::END::{}",LocalDateTime.now());
        return jsonObject;
    }

    public String updateUserNotification(String notificationId) {
        logger.info("NotificationRepo::updateUserNotification::START::notificationId{}", notificationId);
        String jsonObject = dbCalling(NotificationQuery.UPDATE_USER_NOTIFICATION,notificationId);
        logger.info("NotificationRepo::updateUserNotification::END::{}",jsonObject);
        return jsonObject;
    }
}
