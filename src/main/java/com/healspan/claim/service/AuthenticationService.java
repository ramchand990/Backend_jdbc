package com.healspan.claim.service;

import com.healspan.claim.model.login.LoginResponse;

public interface AuthenticationService {

    LoginResponse authenticateUser(String authenticationRequest);

}
