package com.healspan.claim.model.login;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LoginResponse {

    private String id;
    private String jwt;
    private String userName;
    private String password;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String hospitalMstId;
    private String hospitalName;
    private String userRoleMstId;
    private String responseStatus;
}
