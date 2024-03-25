package com.healspan.claim.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.healspan.claim.model.login.AuthenticationRequest;
import com.healspan.claim.model.login.LoginResponse;
import com.healspan.claim.repo.AuthenticationRepo;
import com.healspan.claim.util.ClaimUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.healspan.claim.util.EncryptionUtil.encrypt;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private ClaimUtil util;

    @Autowired
    private AuthenticationRepo authenticationRepo;

    @Override
    public LoginResponse authenticateUser(String authenticationRequest) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        AuthenticationRequest request = gson.fromJson(authenticationRequest, AuthenticationRequest.class);
        request.setPassword(encrypt(request.getPassword()));
        logger.info("Authentication request received for user: {}", request.getUsername());
        LoginResponse response = new LoginResponse();
        String jsonString;
        try {
            jsonString = authenticationRepo.authenticateUser(util.singleQuotes(gson.toJson(request)));
            response = gson.fromJson(jsonString, LoginResponse.class);
        } catch (Exception e) {
            logger.error("ClaimOperationServiceImpl::queryClaim::Exception::{}", ExceptionUtils.getStackTrace(e));
        }
        return response;
    }
}
