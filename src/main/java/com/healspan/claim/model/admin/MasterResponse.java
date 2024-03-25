package com.healspan.claim.model.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MasterResponse {

    private String tableName;
    private String status;
}
