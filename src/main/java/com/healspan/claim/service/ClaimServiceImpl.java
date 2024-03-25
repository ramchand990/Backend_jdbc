package com.healspan.claim.service;

import com.google.gson.Gson;
import com.healspan.claim.client.RpaClient;
import com.healspan.claim.constant.Constant;
import com.healspan.claim.model.rpa.ResponseClaimStatusDto;
import com.healspan.claim.model.rpa.RpaPushDetails;
import com.healspan.claim.model.rpa.TokenResponseDto;
import com.healspan.claim.repo.ClaimRepo;
import com.healspan.claim.util.ClaimUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimServiceImpl implements ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimServiceImpl.class);

    @Autowired
    private ClaimRepo claimRepo;

    @Autowired
    private ClaimUtil util;

    @Autowired
    private Gson gson;

    @Autowired
    private RpaClient rpaClient;

    @Override
    public String getHospitalUserDashboardData(long userId) {
        logger.info("ClaimOperationServiceImpl::getHospitalUserDashboardData::Start");
        logger.debug("ClaimOperationServiceImpl::getHospitalUserDashboardData::userId::{}", userId);
        String jsonString = "";
        try {
            jsonString = claimRepo.getHospitalUserDashboardDataFromDb(userId);
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::getHospitalUserDashboardData::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::getHospitalUserDashboardData::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::getHospitalUserDashboardData::End");
        return jsonString;
    }

    @Override
    public String getHealspanUserDashboardData(long userId) {
        logger.info("ClaimOperationServiceImpl::getHealSpanUserDashboardData::Start");
        logger.debug("ClaimOperationServiceImpl::getHealSpanUserDashboardData::userId::{}", userId);
        String jsonString = "";
        try {
            jsonString = claimRepo.getHealspanUserDashboardDataFromDb(userId);
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::getHealSpanUserDashboardData::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::getHealSpanUserDashboardData::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::getHealSpanUserDashboardData::End");
        return jsonString;
    }

    @Override
    public String getClaimDetailAsPerClaimStageLinkID(long claimStageLinkId) {
        logger.info("ClaimOperationServiceImpl::getClaimDetailAsPerClaimStageLinkID requestedLoggedInUserId::Start");
        logger.debug("ClaimOperationServiceImpl::getClaimDetailAsPerClaimStageLinkID::claimStageLinkId::{}", claimStageLinkId);
        String jsonString = "";
        try {
            jsonString = claimRepo.getClaimDetailAsPerClaimStageLinkID(claimStageLinkId);
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::getClaimDetailAsPerClaimStageLinkID::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::getClaimDetailAsPerClaimStageLinkID::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::getClaimDetailAsPerClaimStageLinkID responseData::End");
        return jsonString;
    }

    @Override
    public String createOrUpdateClaimAndPatientInfo(String claimAndPatientInfo) {
        logger.info("ClaimOperationServiceImpl::createOrUpdateClaimAndPatientInfo::Start");
        logger.debug("ClaimOperationServiceImpl::createOrUpdateClaimAndPatientInfo::claimAndPatientInfo::{}", claimAndPatientInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.createOrUpdateClaimAndPatientInfo(util.singleQuotes(claimAndPatientInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::createOrUpdateClaimAndPatientInfo::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::createOrUpdateClaimAndPatientInfo::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::createOrUpdateClaimAndPatientInfo responseData::End");
        return jsonString;
    }

    @Override
    public String createOrUpdateMedicalInfo(String medicalInfo) {
        logger.info("ClaimOperationServiceImpl::createOrUpdateMedicalInfo::Start");
        logger.debug("ClaimOperationServiceImpl::CreateOrUpdateMedicalInfo::medicalInfo::{}", medicalInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.createOrUpdateMedicalInfo(util.singleQuotes(medicalInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::createOrUpdateMedicalInfo::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::createOrUpdateMedicalInfo::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::createOrUpdateMedicalInfo responseData::End");
        return jsonString;
    }

    @Override
    public String createOrUpdateInsuranceInfo(String insuranceInfo) {
        logger.info("ClaimOperationServiceImpl::createOrUpdateInsuranceInfo::Start");
        logger.debug("ClaimOperationServiceImpl::createOrUpdateInsuranceInfo::insuranceInfo::{}", insuranceInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.createOrUpdateInsuranceInfo(util.singleQuotes(insuranceInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::createOrUpdateInsuranceInfo::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::createOrUpdateInsuranceInfo::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::createOrUpdateInsuranceInfo responseData::End");
        return jsonString;
    }

    @Override
    public String saveDocumentOfQuestionnaire(String documentInfo) {
        logger.info("ClaimOperationServiceImpl::saveDocumentOfQuestionnaire::Start");
        logger.debug("ClaimOperationServiceImpl::saveDocumentOfQuestionnaire::documentInfo::{}", documentInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.saveDocumentOfQuestionnaire(util.singleQuotes(documentInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::saveDocumentOfQuestionnaire::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::saveDocumentOfQuestionnaire::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::saveDocumentOfQuestionnaire responseData::End");
        return jsonString;
    }

    @Override
    public String submitClaimStatus(String claimInfo) {
        logger.info("ClaimOperationServiceImpl::submitClaimStatus::Start");
        logger.debug("ClaimOperationServiceImpl::submitClaimStatus::claimInfo::{}", claimInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.submitClaimStatus(util.singleQuotes(claimInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::submitClaimStatus::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::submitClaimStatus::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::submitClaimStatus responseData::End");
        return jsonString;
    }

    @Override
    public String changeStage(String stageDetails) {
        logger.info("ClaimOperationServiceImpl::changeStage::Start");
        logger.info("ClaimOperationServiceImpl::changeStage::stageDetails::{}", stageDetails);
        String jsonString = "";
        try {
            jsonString = claimRepo.changeStage(util.singleQuotes(stageDetails));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::changeStage::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::changeStage::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::changeStage responseData::End");
        return jsonString;
    }

    @Override
    public String queryClaim(String commentInfo) {
        logger.info("ClaimOperationServiceImpl::queryClaim::Start");
        logger.debug("ClaimOperationServiceImpl::queryClaim::commentInfo::{}", commentInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.queryClaim(util.singleQuotes(commentInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::queryClaim::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::queryClaim::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::queryClaim::End");
        return jsonString;
    }

    @Override
    public ResponseClaimStatusDto pushRpaClaim(String rpaInfo) {
        logger.info("ClaimOperationServiceImpl::pushRpaClaim::Start");
        logger.debug("ClaimOperationServiceImpl::pushRpaClaim::Request received to push claim data to rpa:{}", rpaInfo);
        String jsonString = "";
        ResponseClaimStatusDto response = new ResponseClaimStatusDto();
        try {
            jsonString = claimRepo.approveClaim(util.singleQuotes(rpaInfo));
            RpaPushDetails rpaPushDetails = gson.fromJson(jsonString, RpaPushDetails.class);
            logger.debug("get token response for rpaClient");
            TokenResponseDto tokenResponseDto = rpaClient.getToken();
            logger.debug("Push claimData to rpa for claimId:{} and getAccessTokenDetails:{}",
                    rpaPushDetails.getClaimId(), tokenResponseDto);
            rpaClient.pushRpaClaim(jsonString, tokenResponseDto.getAccess_token());
            logger.debug("RpaRequiredDetailDto::{}::AccessTokenDetails:{}", jsonString, tokenResponseDto.getAccess_token());
            response.setResponseStatus(String.valueOf(Constant.ResponseStatus.SUCCESS));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::pushRpaClaim::Exception::{}", ExceptionUtils.getStackTrace(e));
            response.setResponseStatus(String.valueOf(Constant.ResponseStatus.FAILED));
        }
        logger.debug("ClaimOperationServiceImpl::pushRpaClaim::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::pushRpaClaim::End");
        return response;
    }

    @Override
    public String receiveTpaResponse(String tpaInfo) {
        logger.info("ClaimOperationServiceImpl::receiveTpaResponse::Start");
        logger.info("ClaimOperationServiceImpl::receiveTpaResponse::stageDetails::{}", tpaInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.receiveTpaResponse(util.singleQuotes(tpaInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::receiveTpaResponse::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::receiveTpaResponse::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::receiveTpaResponse responseData::End");
        return jsonString;
    }

    @Override
    public String getOtherDocument(String otherDocumentInfo) {
        logger.info("ClaimOperationServiceImpl::getOtherDocument::Start");
        logger.debug("ClaimOperationServiceImpl::getOtherDocument::commentInfo::{}", otherDocumentInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.getOtherDocument(util.singleQuotes(otherDocumentInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::getOtherDocument::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::getOtherDocument::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::getOtherDocument::End");
        return jsonString;
    }

    @Override
    public String getDocumentTypeId(String getDocInfo) {
        logger.info("ClaimOperationServiceImpl::getDocumentTypeId::Start");
        logger.debug("ClaimOperationServiceImpl::getDocumentTypeId::commentInfo::{}", getDocInfo);
        String jsonString = "";
        try {
            jsonString = claimRepo.getDocumentTypeId(util.singleQuotes(getDocInfo));
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::getDocumentTypeId::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        logger.debug("ClaimOperationServiceImpl::getDocumentTypeId::Response::{}", jsonString);
        logger.info("ClaimOperationServiceImpl::getDocumentTypeId::End");
        return jsonString;
    }
}
