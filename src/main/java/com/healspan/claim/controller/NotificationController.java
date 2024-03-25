package com.healspan.claim.controller;

import com.healspan.claim.service.NotificationService;
import com.healspan.claim.util.ClaimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/healspan/claim")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClaimUtil claimUtil;

    @GetMapping(value = "/getusernotification/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserNotification(@PathVariable long userId) {
        if(!claimUtil.emptyJsonStringCheck(notificationService.getUserNotification(userId))){
            return new ResponseEntity<>(notificationService.getUserNotification(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(notificationService.getUserNotification(userId), HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/updateusernotification",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserNotification(@RequestBody String notificationId) {
        if(!claimUtil.emptyJsonStringCheck(notificationService.updateUserNotification(notificationId))){
            return new ResponseEntity<>(notificationService.updateUserNotification(notificationId), HttpStatus.OK);
        }
        return new ResponseEntity<>(notificationService.updateUserNotification(notificationId), HttpStatus.NO_CONTENT);
    }
}
