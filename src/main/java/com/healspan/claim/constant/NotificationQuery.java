package com.healspan.claim.constant;

public class NotificationQuery {

    private NotificationQuery() {
    }

    public static final String GET_NOTIFICATION =
            "SELECT * from healspan.get_user_notification({0})";

    public static final String UPDATE_USER_NOTIFICATION =
            "SELECT * from healspan.update_user_notification({0})";
}
