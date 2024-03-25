package com.healspan.claim.service;

public interface NotificationService {

    String getUserNotification(long userId);

    String updateUserNotification(String notificationId);
}
