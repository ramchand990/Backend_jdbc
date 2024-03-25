package com.healspan.claim.model.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MasterDetails {
    private String tableName;
    private TableRequestParameters requestParameters;
}
