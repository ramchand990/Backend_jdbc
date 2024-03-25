package com.healspan.claim.service;

import com.google.gson.Gson;
import com.healspan.claim.repo.NotificationRepo;
import com.healspan.claim.util.ClaimUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private Gson gson;

    @Autowired
    private ClaimUtil util;

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public String getUserNotification(long userId) {
        logger.info("NotificationServiceImpl::getUserNotification::Start");
        logger.debug("NotificationServiceImpl::getUserNotification::userId::{}", userId);
        String jsonString = "";
        try {
            jsonString = notificationRepo.getUserNotification(userId);
        } catch (Exception e) {
            logger.error("NotificationServiceImpl::getUserNotification::Exception::{}",
                    ExceptionUtils.getStackTrace(e));
        }
        logger.debug("NotificationServiceImpl::getUserNotification::Response::{}", jsonString);
        logger.info("NotificationServiceImpl::getUserNotification::End");
        return jsonString;
    }

    @Override
    public String updateUserNotification(String notificationId) {
        logger.info("NotificationServiceImpl::updateUserNotification::Start");
        logger.info("NotificationServiceImpl::updateUserNotification::stageDetails::{}",notificationId);
        String jsonString = "";
        try {
            jsonString = notificationRepo.updateUserNotification(util.singleQuotes(notificationId));
        } catch (Exception e) {
            logger.error("NotificationServiceImpl::updateUserNotification::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("NotificationServiceImpl::updateUserNotification::Response::{}", jsonString);
        logger.info("NotificationServiceImpl::updateUserNotification::responseData::End");
        return jsonString;
    }
}
