package com.healspan.claim.service;

import com.healspan.claim.repo.AdminRepo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public String getTpaWiseMasterDetails(long tpaId) {
        logger.info("AdminServiceImpl::getTpaWiseMasterDetails::Start");
        logger.debug("AdminServiceImpl::getTpaWiseMasterDetails::{}", tpaId);
        String jsonString = "";
        try {
            jsonString = adminRepo.getTpaWiseMasterDetails(tpaId);
        } catch (Exception e) {
            logger.error("AdminServiceImpl::getTpaWiseMasterDetails::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("AdminServiceImpl::getTpaWiseMasterDetails::Response::{}", jsonString);
        logger.info("AdminServiceImpl::getTpaWiseMasterDetails::End");
        return jsonString;
    }

    @Override
    public String getMasterDetails(int hospitalId) {
        logger.info("AdminServiceImpl::getMasterDetails::Start");
        logger.debug("AdminServiceImpl::getMasterDetails::{}", hospitalId);
        String jsonString = "";
        try {
            jsonString = adminRepo.getMasterDetails(hospitalId);
        } catch (Exception e) {
            logger.error("AdminServiceImpl::getMasterDetails::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("AdminServiceImpl::getMasterDetails::Response::{}", jsonString);
        logger.info("AdminServiceImpl::getMasterDetails::End");
        return jsonString;
    }
}
