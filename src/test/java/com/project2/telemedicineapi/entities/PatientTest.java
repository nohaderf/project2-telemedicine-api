package com.project2.telemedicineapi.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    Patient patient = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");

    @Test
    void testToString() {
        assertEquals("Patient(id=1, username=Ayesha, password=password, firstName=Ayesha, lastName=Solanki, dob=2000-05-12, phoneNum=+1 6474023382)", patient.toString());

    }
    @Test
    void constructor(){
        Patient patient1 = new Patient ("Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        assertTrue(patient1 instanceof Patient);
    }
}