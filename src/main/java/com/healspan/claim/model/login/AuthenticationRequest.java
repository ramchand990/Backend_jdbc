package com.healspan.claim.model.login;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AuthenticationRequest {

    private String username;
    private String password;
    private boolean isActive;
}
