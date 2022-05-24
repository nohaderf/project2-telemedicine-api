package com.project2.telemedicineapi.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRequestTest {

    @Test
    void constructor(){
        AppointmentRequest appointmentRequest = new AppointmentRequest(1,2,"", "");
        assertTrue(appointmentRequest instanceof AppointmentRequest);
    }

}