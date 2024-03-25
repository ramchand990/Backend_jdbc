package com.healspan.claim.controller;

import com.google.gson.Gson;
import com.healspan.claim.constant.Constant;
import com.healspan.claim.model.login.AuthenticationRequest;
import com.healspan.claim.model.login.LoginResponse;
import com.healspan.claim.repo.AuthenticationRepo;
import com.healspan.claim.service.AuthenticationService;
import com.healspan.claim.util.JwtUtil;
import com.healspan.claim.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.healspan.claim.util.EncryptionUtil.encrypt;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationRepo authenticationRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    /**
     * @param authenticationRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody String authenticationRequest) {
        logger.debug("AuthenticationController::createAuthenticationToken::authenticationRequest::START::{}", authenticationRequest);

        AuthenticationRequest request = new Gson().fromJson(authenticationRequest, AuthenticationRequest.class);
        try {
            String password = encrypt(request.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), password)
            );
        } catch (BadCredentialsException e) {
            logger.info("User is not authentic:{}", request.getUsername());
            return new ResponseEntity(Constant.ResponseStatus.FAILED, HttpStatus.NOT_FOUND);
        }
        logger.info("Generating JWT token for authenticated user:{} ", request.getUsername());
        // Fixing
        LoginResponse response = authenticationRepo.getUserInformation(request.getUsername());
        if (response.getUserName() != null) {
            final UserDetails userDetails = userMapper.loadUserByUsername(request.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            getLoginResponseMap(response, authenticationRequest);
            logger.info("Successfully generated the JWT token for user ::{} :: token----{}", request.getUsername(), jwt);
            response.setJwt(jwt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        logger.debug("AuthenticationController::createAuthenticationToken::authenticationRequest::END::");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private void getLoginResponseMap(LoginResponse response, String authenticationRequest) {
        LoginResponse loginResponse = authenticationService.authenticateUser(authenticationRequest);
        response.setId(loginResponse.getId());
        response.setFirstName(loginResponse.getFirstName());
        response.setLastName(loginResponse.getLastName());
        response.setPassword(null);
        response.setHospitalName(loginResponse.getHospitalName());
        response.setHospitalMstId(loginResponse.getHospitalMstId());
        response.setUserRoleMstId(loginResponse.getUserRoleMstId());
        response.setResponseStatus(loginResponse.getResponseStatus());
    }
}
