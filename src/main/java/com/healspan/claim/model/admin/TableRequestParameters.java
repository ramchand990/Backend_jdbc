package com.healspan.claim.model.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TableRequestParameters {
    private long id;
    private String hospitalUhid;
    private String name;
    private String email;
    private String userName;
    private String firstName;
    private Boolean isActive;
    private String mobileNo;
    private String lastName;
    private String password;
    private long hospitalMstId;
    private long userRoleMstId;
}
