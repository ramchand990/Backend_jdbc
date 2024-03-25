package com.healspan.claim.constant;

public class AuthenticationQuery {
    public static final String USER_AUTHENTICATION =
            "select * from healspan.validate_user({0})";

    public static final String USER_AUTHENTICATION_BY_NAME =
            "select * from healspan.user_mst um where upper(username) = upper({0});";

    private AuthenticationQuery() {
    }
}
