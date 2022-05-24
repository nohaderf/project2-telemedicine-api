package com.project2.telemedicineapi.helpers;

import com.project2.telemedicineapi.dto.NotificationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NotificationClient {

    private static final String SEND_NOTIFICATION_REQUEST ="http://localhost:8080/messages";
    private RestTemplate restTemplate = new RestTemplate();


    public void callPostEmail(String phoneNumeber, String message){
        NotificationRequest notificationRequest = new NotificationRequest(phoneNumeber,message);
        restTemplate.postForEntity(SEND_NOTIFICATION_REQUEST,notificationRequest, null);

    }
}