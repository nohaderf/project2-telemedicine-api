package com.project2.telemedicineapi.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
    Doctor doctor = Doctor.builder().id(1).firstName("khaled").lastName("Amayri").username("kh1995").password("password").phoneNum("5875684533").specialization("doctor").build();
    @Test
    void testEquals() {
        Doctor doctor1 = Doctor.builder().id(1).firstName("khaled").lastName("Amayri").username("kh1995").password("password").phoneNum("5875684533").specialization("doctor").build();
        assertEquals(true,doctor.equals(doctor1));
    }

    @Test
    void getId() {
        assertEquals(1,doctor.getId());
    }

    @Test
    void getUsername() {
        assertEquals("kh1995",doctor.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("password",doctor.getPassword());
    }

    @Test
    void getFirstName() {
        assertEquals("khaled",doctor.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Amayri",doctor.getLastName());
    }

    @Test
    void getSpecialization() {
        assertEquals("doctor",doctor.getSpecialization());
    }

    @Test
    void getPhoneNum() {
        assertEquals("5875684533",doctor.getPhoneNum());
    }

    @Test
    void setId() {
        doctor.setId(2);
        assertEquals(2,doctor.getId());
    }

    @Test
    void setUsername() {
        doctor.setUsername("kh19995");
        assertEquals("kh19995",doctor.getUsername());
    }

    @Test
    void setPassword() {
        doctor.setPassword("pass");
        assertEquals("pass",doctor.getPassword());

    }

    @Test
    void setFirstName() {
        doctor.setFirstName("khaledd");
        assertEquals("khaledd",doctor.getFirstName());
    }

    @Test
    void setLastName() {
        doctor.setLastName("Amayrii");
        assertEquals("Amayrii",doctor.getLastName());
    }

    @Test
    void setSpecialization() {
        doctor.setSpecialization("heart specialist");
        assertEquals("heart specialist",doctor.getSpecialization());
    }

    @Test
    void setPhoneNum() {
        doctor.setPhoneNum("58756845333");
        assertEquals("58756845333",doctor.getPhoneNum());
    }
    @Test
    void testToString() {
        assertEquals("Doctor(id=1, username=kh1995, password=password, firstName=khaled, lastName=Amayri, specialization=doctor, phoneNum=5875684533)", doctor.toString());
    }
    @Test
    void constructor(){
        Doctor d1 =new Doctor( "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");
        assertTrue(d1 instanceof Doctor);
        Doctor d2 = new Doctor();
        assertTrue(d2 instanceof Doctor);
    }

}