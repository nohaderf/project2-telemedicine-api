package com.project2.telemedicineapi.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    public void constructor(){
        Appointment appointment = new Appointment("pending","note",null,null);
        assertTrue(appointment instanceof Appointment);
    }
    @Test
    public void testToString(){
        Appointment appointment = new Appointment("pending","note",null,null);
        assertEquals("Appointment(id=null, date=null, time=null, status=pending, note=note, doctor=null, patient=null)",appointment.toString());
    }

}