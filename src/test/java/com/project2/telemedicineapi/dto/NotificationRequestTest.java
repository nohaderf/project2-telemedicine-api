package com.project2.telemedicineapi.dto;

import org.junit.jupiter.api.Test;

import javax.crypto.MacSpi;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRequestTest {
    NotificationRequest notificationRequest = new NotificationRequest("5875684533","test");

    @Test
    void testEquals() {
        NotificationRequest notificationRequest = new NotificationRequest("5875684533","test");
        assertEquals(notificationRequest,this.notificationRequest);
    }

    @Test
    void getPhoneNumber() {
        assertEquals("5875684533",notificationRequest.getPhoneNumber());
    }

    @Test
    void getMessage() {
        assertEquals("test",notificationRequest.getMessage());
    }

    @Test
    void setPhoneNumber() {
        notificationRequest.setPhoneNumber("555");
        assertEquals("555",notificationRequest.getPhoneNumber());
    }

    @Test
    void setMessage() {
        notificationRequest.setMessage("test2");
        assertEquals("test2",notificationRequest.getMessage());
    }

    @Test
    void testToString() {
        assertEquals("NotificationRequest(phoneNumber=5875684533, message=test)",notificationRequest.toString());
    }
    @Test
    void constructor(){
        NotificationRequest notificationRequest = new NotificationRequest();
        assertTrue(notificationRequest instanceof NotificationRequest);
    }
}