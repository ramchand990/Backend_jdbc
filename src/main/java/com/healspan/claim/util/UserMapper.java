package com.healspan.claim.util;

import com.healspan.claim.model.login.LoginResponse;
import com.healspan.claim.repo.AuthenticationRepo;
import com.healspan.claim.security.UserMstDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @Autowired
    private AuthenticationRepo authenticationRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("Find user record in database userName:{} ", userName);
        LoginResponse response = authenticationRepo.getUserInformation(userName);

        UserMstDetails userMstDetails = new UserMstDetails();
        userMstDetails.setUserName(response.getUserName());
        userMstDetails.setPassword(response.getPassword());
        userMstDetails.setActive(response.isActive());
        logger.info("User found in db userDetails{}: ", userMstDetails);
        return userMstDetails;
    }
}
