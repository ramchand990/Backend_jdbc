package com.healspan.claim.model.rpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TokenResponseDto {
    private String access_token;
    private Long expires_in;
    private String scope;
    private String token_type;
}
